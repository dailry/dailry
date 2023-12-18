package com.daily.daily.member.controller;

import com.daily.daily.member.domain.Member;
import com.daily.daily.member.dto.DuplicateResponseDTO;
import com.daily.daily.member.dto.JoinDTO;
import com.daily.daily.member.dto.MemberInfoDTO;
import com.daily.daily.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    @PostMapping("/join")
    @ResponseStatus(HttpStatus.CREATED)
    public MemberInfoDTO join(@RequestBody @Valid JoinDTO joinDTO) {
        return memberService.join(joinDTO);
    }

    @Secured(value = "ROLE_MEMBER")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public MemberInfoDTO getMemberByAccessToken(@AuthenticationPrincipal Member member) {
        return MemberInfoDTO.from(member);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/check-username")
    public DuplicateResponseDTO checkDuplicatedUsername(String username) {
        System.out.println(username);
        if (memberService.existsByUsername(username)) {
            return new DuplicateResponseDTO(true);
        }

        return new DuplicateResponseDTO(false);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/check-nickname")
    public DuplicateResponseDTO checkDuplicatedNickname(String nickname) {
        if (memberService.existsByNickname(nickname)) {
            return new DuplicateResponseDTO(true);
        }

        return new DuplicateResponseDTO(false);
    }
}
