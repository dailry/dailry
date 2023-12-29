package com.daily.daily.member.repository;

import com.daily.daily.member.constant.MemberRole;
import com.daily.daily.member.domain.Member;
import com.daily.daily.member.validator.Nickname;
import com.daily.daily.oauth.constant.SocialType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MemberRepositoryTest {

    static final String TEST_USERNAME = "geonwoo123";
    static final String TEST_NICKNAME = "다일리123";
    static final String TEST_EMAIL = "geonwoo123@naver.com";

    @Autowired MemberRepository memberRepository;
    
    @BeforeEach()
    void saveTestData() {
        Member testMember = Member.builder()
                .username(TEST_USERNAME)
                .nickname(TEST_NICKNAME)
                .email(TEST_EMAIL)
                .role(MemberRole.ROLE_MEMBER)
                .build();

        memberRepository.save(testMember);
    }
    @Test
    void findByUsername() {
        Member findMember = memberRepository.findByUsername(TEST_USERNAME)
                .orElseThrow(NoSuchElementException::new);

        assertThat(findMember.getUsername()).isEqualTo(TEST_USERNAME);
        assertThat(findMember.getNickname()).isEqualTo(TEST_NICKNAME);
        assertThat(findMember.getRole()).isEqualTo(MemberRole.ROLE_MEMBER);
    }

    @Test
    void findByEmail() {
        Member findMember = memberRepository.findByEmail(TEST_EMAIL)
                .orElseThrow(NoSuchElementException::new);

        assertThat(findMember.getUsername()).isEqualTo(TEST_USERNAME);
        assertThat(findMember.getNickname()).isEqualTo(TEST_NICKNAME);
        assertThat(findMember.getRole()).isEqualTo(MemberRole.ROLE_MEMBER);
    }

    @Test
    void existsByUsername() {
        boolean isExist = memberRepository.existsByUsername(TEST_USERNAME);
        boolean isNotExist = memberRepository.existsByUsername("존재하지 않는 username");

        assertThat(isExist).isTrue();
        assertThat(isNotExist).isFalse();
    }

    @Test
    void existsByNickname() {
        boolean isExist = memberRepository.existsByNickname(TEST_NICKNAME);
        boolean isNotExist = memberRepository.existsByNickname("존재하지 않는 nickname");

        assertThat(isExist).isTrue();
        assertThat(isNotExist).isFalse();
    }

    @Test
    void existsByEmail() {
        boolean isExist = memberRepository.existsByEmail(TEST_EMAIL);
        boolean isNotExist = memberRepository.existsByEmail("존재하지 않는 email");

        assertThat(isExist).isTrue();
        assertThat(isNotExist).isFalse();
    }

    @Test
    void findBySocialTypeAndSocialId() {
        //given
        String testEmail = "geonwoo123@gmail.com";
        Member member = Member.builder()
                .socialType(SocialType.GOOGLE)
                .socialId("socialId")
                .email(testEmail)
                .build();

        memberRepository.save(member);

        //when
        Member findMember = memberRepository.findBySocialTypeAndSocialId(SocialType.GOOGLE, "socialId")
                .orElseThrow(NoSuchElementException::new);

        //then
        assertThat(findMember.getEmail()).isEqualTo(testEmail);
    }
}