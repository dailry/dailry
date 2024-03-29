package com.daily.daily.dailry.controller;

import com.daily.daily.common.dto.SuccessResponseDTO;
import com.daily.daily.dailry.dto.DailryDTO;
import com.daily.daily.dailry.dto.DailryFindDTO;
import com.daily.daily.dailry.dto.DailryUpdateDTO;
import com.daily.daily.dailry.service.DailryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dailry")
public class DailryController {

    private final DailryService dailryService;

    @Secured(value = "ROLE_MEMBER")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DailryDTO createDailry(@AuthenticationPrincipal Long memberId, @RequestBody @Valid DailryUpdateDTO dailryUpdateDTO) {
        return dailryService.create(memberId, dailryUpdateDTO);
    }

    @Secured(value = "ROLE_MEMBER")
    @PatchMapping("/{dailryId}")
    public DailryDTO updateDailry(@AuthenticationPrincipal Long memberId, @RequestBody DailryUpdateDTO dailryUpdateDTO, @PathVariable Long dailryId) {
        return dailryService.update(memberId, dailryId, dailryUpdateDTO);
    }

    @Secured(value = "ROLE_MEMBER")
    @GetMapping("/{dailryId}")
    public DailryDTO findDailry(@AuthenticationPrincipal Long memberId, @PathVariable Long dailryId) {
        return dailryService.find(memberId, dailryId);
    }

    @Secured(value = "ROLE_MEMBER")
    @GetMapping
    public List<DailryFindDTO> findAllDailry(@AuthenticationPrincipal Long memberId) {
        return dailryService.findAll(memberId);
    }

    @Secured(value = "ROLE_MEMBER")
    @DeleteMapping("/{dailryId}")
    public SuccessResponseDTO deleteDailry(@AuthenticationPrincipal Long memberId, @PathVariable Long dailryId) {
        dailryService.delete(memberId, dailryId);
        return new SuccessResponseDTO(true, HttpStatus.OK.value());
    }
}
