package com.daily.daily.member.service;

import com.daily.daily.member.domain.Member;
import com.daily.daily.member.dto.JoinDTO;
import com.daily.daily.member.dto.MemberInfoDTO;
import com.daily.daily.member.dto.PasswordUpdateDTO;
import com.daily.daily.member.exception.DuplicatedNicknameException;
import com.daily.daily.member.exception.DuplicatedUsernameException;
import com.daily.daily.member.exception.InvalidPasswordResetTokenException;
import com.daily.daily.member.exception.MemberNotFoundException;
import com.daily.daily.member.exception.PasswordUnmatchedException;
import com.daily.daily.member.repository.MemberRepository;
import com.daily.daily.member.repository.PasswordResetTokenRepository;
import com.daily.daily.member.validator.Nickname;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final NicknameGenerator nicknameGenerator;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    public MemberInfoDTO join(JoinDTO joinDTO) {
        String password = joinDTO.getPassword();
        joinDTO.setPassword(passwordEncoder.encode(password));

        if (joinDTO.hasNoneNickname()) {
            joinDTO.setNickname(nicknameGenerator.generateRandomNickname());
        }

        Member member = joinDTO.toMember();
        validateJoinMember(member);
        memberRepository.save(member);

        return MemberInfoDTO.from(member);
    }

    private void validateJoinMember(Member member) {
        if (existsByUsername(member.getUsername())) {
            throw new DuplicatedUsernameException();
        }

        validateDuplicatedNickname(member.getNickname());
    }

    private void validateDuplicatedNickname(String nickname) {
        if (existsByNickname(nickname)) {
            throw new DuplicatedNicknameException();
        }
    }

    public MemberInfoDTO findById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(MemberNotFoundException::new);

        return MemberInfoDTO.from(member);
    }

    public MemberInfoDTO updateNickname(Long id, String nickname) {
        Member findMember = memberRepository.findById(id)
                .orElseThrow(MemberNotFoundException::new);

        validateDuplicatedNickname(nickname);

        findMember.updateNickname(nickname);
        return MemberInfoDTO.from(findMember);
    }

    public void updatePassword(PasswordUpdateDTO passwordUpdateDTO, Long id) {
        Member findMember = memberRepository.findById(id)
                .orElseThrow(MemberNotFoundException::new);

        String presentPassword = passwordUpdateDTO.getPresentPassword();
        String updatePassword = passwordUpdateDTO.getUpdatePassword();

        if (!verifyPassword(presentPassword, findMember.getPassword())) {
            throw new PasswordUnmatchedException();
        }

        findMember.updatePassword(passwordEncoder.encode(updatePassword));
    }

    private boolean verifyPassword(String inputPassword, String memberPassword) {
        return passwordEncoder.matches(inputPassword, memberPassword);
    }

    public void updatePasswordByResetToken(String passwordResetToken, String updatePassword) {
        Long memberId = passwordResetTokenRepository.getMemberIdByToken(passwordResetToken)
                .orElseThrow(InvalidPasswordResetTokenException::new);

        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);

        findMember.updatePassword(passwordEncoder.encode(updatePassword));
    }

    public boolean existsByUsername(String username) {
        return memberRepository.existsByUsername(username);
    }

    public boolean existsByNickname(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

    public void withdrawalMember(Long memberId)
    {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
        memberRepository.delete(findMember);
    }

}
