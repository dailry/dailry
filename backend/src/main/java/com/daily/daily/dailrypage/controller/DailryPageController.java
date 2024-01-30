package com.daily.daily.dailrypage.controller;

import com.daily.daily.common.dto.SuccessResponseDTO;
import com.daily.daily.dailrypage.dto.DailryPageDTO;
import com.daily.daily.dailrypage.dto.DailryPageFindDTO;
import com.daily.daily.dailrypage.dto.DailryPageUpdateDTO;
import com.daily.daily.dailrypage.service.DailryPageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
//@RequestMapping("/api/dailry/{dailryID}/pages")
//@RequestMapping("/api/pages")
public class DailryPageController {

    private final DailryPageService dailryPageService;

    @PostMapping("/api/pages")
    @ResponseStatus(HttpStatus.CREATED)
    public DailryPageDTO createPage() {
        return dailryPageService.create();
    }

    @PostMapping("/api/dailry/{dailryID}/pages")
    @ResponseStatus(HttpStatus.CREATED)
    public DailryPageDTO createPage2(@AuthenticationPrincipal Long memberId, @PathVariable Long dailryID) {
        return dailryPageService.create2(memberId, dailryID);
    }

    @PatchMapping("/api/pages/{pageId}")
    public DailryPageDTO updatePage(@AuthenticationPrincipal Long memberId, @RequestBody @Valid DailryPageUpdateDTO dailryPageUpdateDTO, @PathVariable Long pageId) {
        return dailryPageService.update(memberId, pageId, dailryPageUpdateDTO);
    }

    @GetMapping("/api/pages/{pageId}")
    public DailryPageDTO findPage(@AuthenticationPrincipal Long memberId, @PathVariable Long pageId) {
        return dailryPageService.find(memberId, pageId);
    }

    @GetMapping("/api/dailry/{dailryId}/pages")
    public List<DailryPageFindDTO> findAllPage(@AuthenticationPrincipal Long memberId, @PathVariable Long dailryId) {
        return dailryPageService.findAll(memberId, dailryId);
    }

    @DeleteMapping("/api/pages/{pageId}")
    public SuccessResponseDTO deletePage(@AuthenticationPrincipal Long memberId, @PathVariable Long pageId) {
        dailryPageService.delete(memberId, pageId);
        return new SuccessResponseDTO(true, HttpStatus.OK.value());
    }
}
