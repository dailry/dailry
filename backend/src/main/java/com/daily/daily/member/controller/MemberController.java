package com.daily.daily.member.controller;

import com.daily.daily.common.dto.CommonResponseDTO;
import com.daily.daily.member.constant.MemberRole;
import com.daily.daily.member.domain.Member;
import com.daily.daily.member.dto.JoinDTO;
import com.daily.daily.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    @PostMapping("/join")
    public ResponseEntity<CommonResponseDTO> join(@RequestBody @Valid JoinDTO joinDTO) {
        try {
            memberService.join(joinDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new CommonResponseDTO("중복된 형식입니다", HttpStatus.BAD_REQUEST.value()));
        }

        return ResponseEntity.status(HttpStatus.CREATED.value())
                .body(new CommonResponseDTO("회원가입 성공", HttpStatus.CREATED.value()));
    }

    @Secured(value = "ROLE_MEMBER")
    @GetMapping("/test")
    public String test(@AuthenticationPrincipal Member member) {

        System.out.println(member.getId());
        System.out.println(member.getUsername());
        System.out.println(member.getPassword());
        System.out.println(member.getNickname());
        System.out.println(member.getRole().name());

        return "test";
    }
}
