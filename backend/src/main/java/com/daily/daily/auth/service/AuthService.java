package com.daily.daily.auth.service;

import com.daily.daily.auth.dto.JwtClaimDTO;
import com.daily.daily.auth.dto.LoginDTO;
import com.daily.daily.auth.exception.LoginFailureException;
import com.daily.daily.member.domain.Member;
import com.daily.daily.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;
    public JwtClaimDTO login(LoginDTO loginDto) {
        String username = loginDto.getUsername();
        String password = loginDto.getPassword();

        Member findMember = memberRepository.findByUsername(username)
                .orElseThrow(LoginFailureException::new);

        if (!passwordEncoder.matches(password, findMember.getPassword())) {
            throw new LoginFailureException();
        }

        return new JwtClaimDTO(findMember.getId(), findMember.getRole());
    }
}
