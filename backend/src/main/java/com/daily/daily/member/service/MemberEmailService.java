package com.daily.daily.member.service;

import com.daily.daily.member.domain.Member;
import com.daily.daily.member.dto.EmailVerifyDTO;
import com.daily.daily.member.exception.CertificationNumberExpirationException;
import com.daily.daily.member.exception.CertificationNumberUnmatchedException;
import com.daily.daily.member.exception.MemberNotFoundException;
import com.daily.daily.member.repository.CertificationNumberRepository;
import com.daily.daily.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class MemberEmailService {
    @Value("${spring.mail.username}")
    private String senderEmail;

    private final MailSender mailSender;

    private final CertificationNumberRepository certificationRepository;

    private final MemberRepository memberRepository;
    public void sendCertificationNumber(String recipientEmail) {
        int certificationNumber = ThreadLocalRandom.current().nextInt(100000, 1000000); // 6자리 인증 번호 생성

        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setFrom(senderEmail);
        mail.setTo(recipientEmail);
        mail.setSubject("[다일리] 이메일 인증번호 입니다.");
        mail.setText(String.format("이메일 인증번호는 %d 입니다.", certificationNumber));

        mailSender.send(mail);
        certificationRepository.saveCertificationNumber(recipientEmail, String.valueOf(certificationNumber));
    }

    public void verifyEmailAndRegister(Long memberId, EmailVerifyDTO emailVerifyDTO) {
        String email = emailVerifyDTO.getEmail();
        String memberCertificationNumber = emailVerifyDTO.getCertificationNumber();
        String findCertificationNumber = certificationRepository.getCertificationNumber(email)
                .orElseThrow(CertificationNumberExpirationException::new);

        if (!memberCertificationNumber.equals(findCertificationNumber)) {
            throw new CertificationNumberUnmatchedException();
        }

        updateMemberEmail(memberId, email);
    }

    private void updateMemberEmail(Long memberId, String email) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);

        member.updateEmail(email);
    }
}
