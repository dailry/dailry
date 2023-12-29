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
import com.daily.daily.member.repository.PasswordResetTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberEmailService {
    @Value("${spring.mail.username}")
    private String SENDER_EMAIL;

    private final MailSender mailSender;

    private final CertificationNumberRepository certificationRepository;

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    private final MemberRepository memberRepository;

    private void validateDuplicatedEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new DuplicatedEmailException();
        }
    }

    public void sendCertificationNumber(String recipientEmail) {
        validateDuplicatedEmail(recipientEmail);

        int certificationNumber = ThreadLocalRandom.current().nextInt(100000, 1000000); // 6자리 인증 번호 생성

        SimpleMailMessage mail = createSimpleMail(recipientEmail);
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
        Member findMember = memberRepository.findByEmail(recipientEmail)
                .filter(Member::isNotSocialLoginMember)
                .orElseThrow(EmailNotFoundException::new);

        SimpleMailMessage mail = createSimpleMail(recipientEmail);
        mail.setSubject("[다일리] 회원님의 아이디를 보내드립니다.");
        mail.setText(String.format("회원님이 가입한 아이디는 %s 입니다.", findMember.getUsername()));

        mailSender.send(mail);
    }

    public void recoverPassword(String username, String email) {
        Member findMember = memberRepository.findByEmail(email)
                .filter(member -> member.hasSameUsername(username))
                .orElseThrow(MemberNotFoundException::new);

        String randomToken = passwordResetTokenRepository.createRandomToken();
        passwordResetTokenRepository.saveRandomTokenWithMemberId(randomToken, findMember.getId());

        SimpleMailMessage mail = createSimpleMail(email);

        mail.setSubject("[다일리] 비밀번호 재설정을 위한 안내 메일 입니다.");
        String link = "https://da-ily.com/resetPasswordByEmail?passwordResetToken=" + randomToken;
        String mailBody = """
                안녕하세요 다일리입니다.
                비밀번호 재설정을 위한링크를 보내드립니다.
                [비밀번호 재설정 링크] : %s
                해당 링크는 발송후 30분 동안만 유효합니다.
                """.formatted(link);
        mail.setText(mailBody);

        mailSender.send(mail);
    }

    private SimpleMailMessage createSimpleMail(String recipientEmail) {
        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setFrom(SENDER_EMAIL);
        mail.setTo(recipientEmail);
        return mail;
    }
}
