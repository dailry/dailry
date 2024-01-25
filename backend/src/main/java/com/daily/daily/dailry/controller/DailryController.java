package com.daily.daily.dailry.controller;

import com.daily.daily.common.dto.SuccessResponseDTO;
import com.daily.daily.dailry.dto.DailryDTO;
import com.daily.daily.dailry.dto.DailryUpdateDTO;
import com.daily.daily.dailry.service.DailryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dailry")
public class DailryController {

    private final DailryService dailryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DailryDTO createDailry(@AuthenticationPrincipal Long memberId, @RequestBody @Valid DailryUpdateDTO dailryUpdateDTO) {
        return dailryService.create(memberId, dailryUpdateDTO);
    }

    @PatchMapping("/{dailryId}")
    public DailryDTO updateDailry(@RequestBody DailryUpdateDTO dailryUpdateDTO, @PathVariable Long dailryId) {
        return dailryService.update(dailryId, dailryUpdateDTO);
    }

    @GetMapping("/{dailryId}")
    public DailryDTO findDailry(@PathVariable Long dailryId) {
        return dailryService.find(dailryId);
    }

    @DeleteMapping("/{dailryId}")
    public SuccessResponseDTO deleteDailry(@PathVariable Long dailryId) {
        dailryService.delete(dailryId);
        return new SuccessResponseDTO(true, HttpStatus.OK.value());
    }
}
