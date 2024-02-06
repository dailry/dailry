package com.daily.daily.dailrypage.controller;

import com.daily.daily.common.dto.SuccessResponseDTO;
import com.daily.daily.dailrypage.dto.DailryPageCreateResponseDTO;
import com.daily.daily.dailrypage.dto.DailryPageDTO;
import com.daily.daily.dailrypage.dto.DailryPagePreviewDTO;
import com.daily.daily.dailrypage.dto.DailryPageThumbnailDTO;
import com.daily.daily.dailrypage.dto.DailryPageUpdateDTO;
import com.daily.daily.dailrypage.service.DailryPageService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
//@RequestMapping("/api/dailry/{dailryID}/pages")
//@RequestMapping("/api/pages")
public class DailryPageController {

    private final DailryPageService dailryPageService;

    @PostMapping("/api/dailry/{dailryID}/pages")
    @Secured(value = "ROLE_MEMBER")
    @ResponseStatus(HttpStatus.CREATED)
    public DailryPageDTO createPage2(@AuthenticationPrincipal Long memberId, @PathVariable Long dailryID) {
        return dailryPageService.create2(memberId, dailryID);
    }

    @PostMapping("/api/dailry/pages/{pageId}/edit")
    @Secured(value = "ROLE_MEMBER")
    public DailryPageDTO updatePage(
            @AuthenticationPrincipal Long memberId,
            @PathVariable Long pageId,
            @RequestPart @Valid DailryPageUpdateDTO dailryPageRequest,
            @RequestPart @NotNull MultipartFile thumbnail) {
        return dailryPageService.update(memberId, pageId, dailryPageRequest, thumbnail);
    }

    @GetMapping("/api/dailry/pages/{pageId}")
    @Secured(value = "ROLE_MEMBER")
    public DailryPageDTO findPage(@AuthenticationPrincipal Long memberId, @PathVariable Long pageId) {
        return dailryPageService.find(memberId, pageId);
    }

    @GetMapping("/api/dailry/{dailryId}/pages")
    @Secured(value = "ROLE_MEMBER")
    public DailryPagePreviewDTO findAllPage(@AuthenticationPrincipal Long memberId, @PathVariable Long dailryId) {
        return dailryPageService.findAll(memberId, dailryId);
    }

    @DeleteMapping("/api/dailry/pages/{pageId}")
    @Secured(value = "ROLE_MEMBER")
    public SuccessResponseDTO deletePage(@AuthenticationPrincipal Long memberId, @PathVariable Long pageId) {
        dailryPageService.delete(memberId, pageId);
        return new SuccessResponseDTO(true, HttpStatus.OK.value());
    }
}
