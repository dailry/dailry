package com.daily.daily.member.service;

import com.daily.daily.member.domain.Member;
import com.daily.daily.member.dto.JoinDTO;
import com.daily.daily.member.dto.MemberInfoDTO;
import com.daily.daily.member.exception.DuplicatedNicknameException;
import com.daily.daily.member.exception.DuplicatedUsernameException;
import com.daily.daily.member.repository.MemberRepository;
import com.fasterxml.jackson.core.PrettyPrinter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    public MemberInfoDTO join(JoinDTO joinDTO) {
        String password = joinDTO.getPassword();
        joinDTO.setPassword(passwordEncoder.encode(password));

        Member member = joinDTO.toMember();
        validateJoinMember(member);
        memberRepository.save(member);

        member.initializeNickname();

        return MemberInfoDTO.from(member);
    }

    public boolean existsByUsername(String username) {
        return memberRepository.existsByUsername(username);
    }

    public boolean existsByNickname(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

    public boolean existsByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    private void validateJoinMember(Member member) {
        if (existsByUsername(member.getUsername())) {
            throw new DuplicatedUsernameException();
        }
        if (member.getNickname() == null) return;
        if (existsByNickname(member.getNickname())) {
            throw new DuplicatedNicknameException();
        }
    }

}
