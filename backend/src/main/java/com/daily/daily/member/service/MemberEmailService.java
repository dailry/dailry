package com.daily.daily.member.service;

import com.daily.daily.member.repository.CertificationNumberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class MemberEmailService {
    @Value("${spring.mail.username}")
    private String senderEmail;

    private final MailSender mailSender;

    private final CertificationNumberRepository certificationNumberRepository;
    public void sendCertificationNumber(String recipientEmail) {
        int certificationNumber = ThreadLocalRandom.current().nextInt(100000, 1000000); // 6자리 인증 번호 생성

        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setFrom(senderEmail);
        mail.setTo(recipientEmail);
        mail.setSubject("[다일리] 이메일 인증번호 입니다.");
        mail.setText(String.format("이메일 인증번호는 %d 입니다.", certificationNumber));

        mailSender.send(mail);
        certificationNumberRepository.saveCertificationNumber(recipientEmail, certificationNumber);
    }
}
