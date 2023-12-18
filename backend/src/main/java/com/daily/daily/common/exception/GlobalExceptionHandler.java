package com.daily.daily.common.exception;

import com.daily.daily.common.dto.CommonResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponseDTO> handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        final String defaultMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();

        log.warn(defaultMessage);

        return ResponseEntity.badRequest()
                .body(new CommonResponseDTO(defaultMessage, HttpStatus.BAD_REQUEST.value()));
    }
}
