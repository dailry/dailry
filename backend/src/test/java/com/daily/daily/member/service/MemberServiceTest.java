package com.daily.daily.member.service;

import com.daily.daily.member.domain.Member;
import com.daily.daily.member.dto.JoinDTO;
import com.daily.daily.member.dto.MemberInfoDTO;
import com.daily.daily.member.dto.PasswordUpdateDTO;
import com.daily.daily.member.exception.DuplicatedNicknameException;
import com.daily.daily.member.exception.DuplicatedUsernameException;
import com.daily.daily.member.exception.PasswordUnmatchedException;
import com.daily.daily.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
    @Mock
    MemberRepository memberRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @InjectMocks
    MemberService memberService;

    @Test
    @DisplayName("joinDTO의 정보에 맞게 회원가입이 된 멤버의 정보를 반환해야 한다")
    void join1() {
        //given
        JoinDTO joinDTO = new JoinDTO("username1", "password123", "다일리4141221");

        //when
        MemberInfoDTO memberInfoDTO = memberService.join(joinDTO);

        //then
        assertThat(joinDTO.getUsername()).isEqualTo(memberInfoDTO.getUsername());
        assertThat(joinDTO.getNickname()).isEqualTo(memberInfoDTO.getNickname());
    }


    @Test
    @DisplayName("username이 중복되었을 때에는 DuplicatedUsernameException이 발생해야 한다.")
    void join2() {
        //given
        JoinDTO joinDTO = new JoinDTO("username1", "password123", "다일리441231");
        when(memberRepository.existsByUsername("username1")).thenReturn(true);

        //when, then
        assertThatThrownBy(() -> memberService.join(joinDTO)).isInstanceOf(DuplicatedUsernameException.class);
    }

    @Test
    @DisplayName("nickname이 중복되었을 때에는 DuplicatedNicknameException이 발생해야 한다.")
    void join3() {
        //given
        JoinDTO joinDTO = new JoinDTO("username1", "password123", "다일리441231");
        when(memberRepository.existsByNickname("다일리441231")).thenReturn(true);

        //when, then
        assertThatThrownBy(() -> memberService.join(joinDTO))
                .isInstanceOf(DuplicatedNicknameException.class);
    }

    @Test
    @DisplayName("닉네임을 변경할 때, 변경하고 싶은 닉네임이 이미 존재하면 DuplicatedNicknameException이 발생한다.")
    void updateNickname() {
        //given
        Member member = Member.builder()
                .id(1L)
                .username("username1")
                .nickname("임시닉네임")
                .build();

        when(memberRepository.existsByNickname("바꾸고 싶은 닉네임")).thenReturn(true);
        when(memberRepository.findById(member.getId())).thenReturn(Optional.of(member));

        //when, then
        assertThatThrownBy(() -> memberService.updateNickname(member, "바꾸고 싶은 닉네임"))
                .isInstanceOf(DuplicatedNicknameException.class);
    }

    @Test
    @DisplayName("비밀번호를 변경할 때, 현재 입력된 비밀번호가 올바르지 않으면 PasswordUnmatchedException이 발생한다.")
    void updatePassword1() {
         //given
        Member member = Member.builder()
                .id(1L)
                .username("username1")
                .nickname("nickname")
                .password(new BCryptPasswordEncoder().encode("myPassword123"))
                .build();

        PasswordUpdateDTO wrongPasswordDTO = new PasswordUpdateDTO("wrongPassword", "updatePassword");
        PasswordUpdateDTO correctPasswordDTO = new PasswordUpdateDTO("myPassword123", "updatePassword");
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));

        MemberService testMemberService = new MemberService(new BCryptPasswordEncoder(), memberRepository); // 실제 BCrypt인코더 주입, memberRepository는 Mock객체

        //when, then
        assertThatThrownBy(() -> testMemberService.updatePassword(wrongPasswordDTO, member))
                .isInstanceOf(PasswordUnmatchedException.class); // wrong case

        testMemberService.updatePassword(correctPasswordDTO, member); // correct case. no exception
    }
}