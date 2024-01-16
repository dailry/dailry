package com.daily.daily.dailry.controller;

import com.daily.daily.dailry.dto.DailryDTO;
import com.daily.daily.dailry.service.DailryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dailry")
public class DailryController {

    private final DailryService dailryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DailryDTO createDailry() {
        return dailryService.create();
    }
}
