package com.daily.daily.member.controller;

import com.daily.daily.member.dto.JoinDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberControllerTest {

    @Test
    @DisplayName("한글 사이즈를 3, 영어숫자 사이즈를 2라고 보았을 때 전체 사이즈가 30사이즈를 넘어가면 안된다.")

    void join() {
        JoinDTO joinDTO = new JoinDTO();
        joinDTO.setNickname("ㅁㅁㅁㅁ@@");


    }
}