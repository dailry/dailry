package com.daily.daily.member.service;

import com.daily.daily.member.domain.Member;
import com.daily.daily.member.dto.EmailVerifyDTO;
import com.daily.daily.member.exception.CertificationNumberExpirationException;
import com.daily.daily.member.exception.CertificationNumberUnmatchedException;
import com.daily.daily.member.exception.DuplicatedEmailException;
import com.daily.daily.member.exception.EmailNotFoundException;
import com.daily.daily.member.exception.MemberNotFoundException;
import com.daily.daily.member.repository.CertificationNumberRepository;
import com.daily.daily.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ThreadLocalRandom;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberEmailService {
    @Value("${spring.mail.username}")
    private String SENDER_EMAIL;

    private final MailSender mailSender;

    private final CertificationNumberRepository certificationRepository;

    private final MemberRepository memberRepository;

    private void validateDuplicatedEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new DuplicatedEmailException();
        }
    }
    public void sendCertificationNumber(String recipientEmail) {
        validateDuplicatedEmail(recipientEmail);

        int certificationNumber = ThreadLocalRandom.current().nextInt(100000, 1000000); // 6자리 인증 번호 생성

        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setFrom(SENDER_EMAIL);
        mail.setTo(recipientEmail);
        mail.setSubject("[다일리] 이메일 인증번호 입니다.");
        mail.setText(String.format("이메일 인증번호는 %d 입니다.", certificationNumber));

        mailSender.send(mail);
        certificationRepository.saveCertificationNumber(recipientEmail, String.valueOf(certificationNumber));
    }

    public void verifyEmailAndRegister(Long memberId, EmailVerifyDTO emailVerifyDTO) {
        String email = emailVerifyDTO.getEmail();

        validateDuplicatedEmail(email);

        String submittedCertificationNumber = emailVerifyDTO.getCertificationNumber();
        String findCertificationNumber = certificationRepository.getCertificationNumber(email)
                .orElseThrow(CertificationNumberExpirationException::new);

        if (!submittedCertificationNumber.equals(findCertificationNumber)) {
            throw new CertificationNumberUnmatchedException();
        }

        updateMemberEmail(memberId, email);
    }
    private void updateMemberEmail(Long memberId, String email) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);

        member.updateEmail(email);
    }

    public void sendUsername(String recipientEmail) {
        Member member = memberRepository.findByEmail(recipientEmail)
                .orElseThrow(EmailNotFoundException::new);

        if (member.isSocialLoginUser()) {
            throw new EmailNotFoundException("소셜 로그인 사용자는 아이디가 존재하지 않습니다.");
        }

        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setFrom(SENDER_EMAIL);
        mail.setTo(recipientEmail);
        mail.setSubject("[다일리] 회원님의 아이디를 보내드립니다.");
        mail.setText(String.format("회원님이 가입한 아이디는 %s 입니다.", member.getUsername()));

        mailSender.send(mail);
    }
}
