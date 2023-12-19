package com.daily.daily.member.controller;

import com.daily.daily.common.dto.CommonResponseDTO;
import com.daily.daily.member.domain.Member;
import com.daily.daily.member.dto.DuplicateResultDTO;
import com.daily.daily.member.dto.EmailDTO;
import com.daily.daily.member.dto.JoinDTO;
import com.daily.daily.member.dto.MemberInfoDTO;
import com.daily.daily.member.dto.NicknameDTO;
import com.daily.daily.member.dto.PasswordUpdateDTO;
import com.daily.daily.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
    public DuplicateResultDTO checkDuplicatedUsername(String username) {
        System.out.println(username);
        if (memberService.existsByUsername(username)) {
            return new DuplicateResultDTO(true);
        }

        return new DuplicateResultDTO(false);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/check-nickname")
    public DuplicateResultDTO checkDuplicatedNickname(String nickname) {
        if (memberService.existsByNickname(nickname)) {
            return new DuplicateResultDTO(true);
        }

        return new DuplicateResultDTO(false);
    }

    @Secured(value = "ROLE_MEMBER")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/check-email")
    public DuplicateResultDTO checkDuplicatedEmail(@Valid EmailDTO emailDTO) {
        if (memberService.existsByEmail(emailDTO.getEmail())) {
            return new DuplicateResultDTO(true);
        }

        return new DuplicateResultDTO(false);
    }

    @Secured(value = "ROLE_MEMBER")
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/nickname")
    public MemberInfoDTO updateNickname(
            @RequestBody @Valid NicknameDTO nicknameDTO,
            @AuthenticationPrincipal Member member
    ) {
        return memberService.updateNickname(member, nicknameDTO.getNickname());
    }
    @Secured(value = "ROLE_MEMBER")
    @PatchMapping("/password")
    @ResponseStatus(HttpStatus.OK)
    public CommonResponseDTO updatePassword(@RequestBody @Valid PasswordUpdateDTO passwordUpdateDTO, @AuthenticationPrincipal Member member) {
        memberService.updatePassword(passwordUpdateDTO, member);
        return new CommonResponseDTO(true, HttpStatus.OK.value());

    }
}
