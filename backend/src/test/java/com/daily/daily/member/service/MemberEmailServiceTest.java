package com.daily.daily.member.service;

import com.daily.daily.member.domain.Member;
import com.daily.daily.member.exception.DuplicatedEmailException;
import com.daily.daily.member.exception.EmailNotFoundException;
import com.daily.daily.member.exception.MemberNotFoundException;
import com.daily.daily.member.repository.CertificationNumberRepository;
import com.daily.daily.member.repository.MemberRepository;
import com.daily.daily.oauth.constant.SocialType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberEmailServiceTest {
    @Mock
    private MailSender mailSender;
    @Mock
    private CertificationNumberRepository certificationRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private MemberEmailService memberEmailService;

    @Test
    @DisplayName("사용자가 입력한 이메일이 중복된 이메일이 아니면, 이메일을 보내고 인증번호를 저장한다.")
    void sendCertificationNumber_success() {
        //given
        String email = "test@example.com";

        //when
        when(memberRepository.existsByEmail(email)).thenReturn(false); // 중복된 이메일이 아닐 때
        memberEmailService.sendCertificationNumber(email);

        //then
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
        verify(certificationRepository, times(1)).saveCertificationNumber(anyString(), anyString());
    }

    @Test
    @DisplayName("사용자가 입력한 이메일이 중복된 이메일이 아니면, 이메일을 보내고 인증번호를 저장한다.")
    void sendCertificationNumber_fail() {
        //given
        String email = "test@example.com";

        //when
        when(memberRepository.existsByEmail(email)).thenReturn(true); // 중복된 이메일일 때

        //then
        assertThatThrownBy(() -> memberEmailService.sendCertificationNumber(email))
                .isInstanceOf(DuplicatedEmailException.class);
    }

    @Test
    void verifyEmailAndRegister() {

    }

    @Test
    @DisplayName("유저가 입력한 이메일이 등록된 이메일이고 소셜 로그인 유저가 아니라면, 아이디 찾기 이메일을 전송한다")
    void sendUsername_success() {
        //given
        String email = "test@example.com";

        //when
        Member mockMember = Member.builder()
                .id(1L)
                .socialType(SocialType.NONE)
                .build();

        when(memberRepository.findByEmail(email)).thenReturn(Optional.of(mockMember)); // 소셜로그인 유저가 아닌 일반 회원을 반환할 때
        memberEmailService.sendUsername(email);

        //then
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    @DisplayName("유저가 입력한 이메일이 소셜 로그인 유저의 이메일이라면 EmailNotFoundException 예외가 발생한다.")
    void sendUsername() {
        //given
        String email = "test@example.com";

        //when
        Member mockMember = Member.builder()
                .id(1L)
                .socialType(SocialType.GOOGLE)
                .build();

        when(memberRepository.findByEmail(email)).thenReturn(Optional.of(mockMember));

        //then
        assertThatThrownBy(() -> memberEmailService.sendUsername(email))
                .isInstanceOf(EmailNotFoundException.class);
    }

    @Test
    @DisplayName("유저가 입력한 이메일과 아이디가 유효하면 비밀번호 초기화 메일을 전송한다.")
    void recoverPassword_success() {
        //given
        String username = "test";
        String email = "test@example.com";

        //when
        Member mockMember = Member.builder()
                .id(1L)
                .email("test@example.com")
                .username("test")
                .build();

        when(memberRepository.findByEmail(email)).thenReturn(Optional.of(mockMember));
        memberEmailService.recoverPassword(username, email);

        //then
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    @DisplayName("유저가 입력한 이메일과 아이디가 유효하지 않으면 MemberNotFoundException이 발생한다.")
    void recoverPassword_fail() {
        //given
        String username = "test";
        String email = "test@example.com";

        //when
        Member mockMember = Member.builder()
                .id(1L)
                .email("test@example.com")
                .username("test123")  // 이메일과 매칭되는 username 정보가 다를 때
                .build();

        when(memberRepository.findByEmail(email)).thenReturn(Optional.of(mockMember));
        //then
        assertThatThrownBy(() -> memberEmailService.recoverPassword(username, email))
                .isInstanceOf(MemberNotFoundException.class);
    }
}