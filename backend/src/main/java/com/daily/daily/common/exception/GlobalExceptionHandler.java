package com.daily.daily.common.exception;

import com.daily.daily.common.dto.ExceptionResponseDTO;
import com.daily.daily.member.exception.DuplicatedNicknameException;
import com.daily.daily.member.exception.DuplicatedUsernameException;
import com.daily.daily.member.exception.PasswordUnmatchedException;
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
    public ResponseEntity<ExceptionResponseDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        final String defaultMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();

        log.warn(defaultMessage);

        return ResponseEntity.badRequest()
                .body(new ExceptionResponseDTO(defaultMessage, HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler({DuplicatedUsernameException.class,
            DuplicatedNicknameException.class})
    public ResponseEntity<ExceptionResponseDTO> handleDuplicatedException(IllegalArgumentException e) {
        return new ResponseEntity<>(new ExceptionResponseDTO(e.getMessage(), HttpStatus.CONFLICT.value()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler({
            PasswordUnmatchedException.class
    })
    public ResponseEntity<ExceptionResponseDTO> handlePasswordUnmatchedException(IllegalArgumentException e) {
        return new ResponseEntity<>(new ExceptionResponseDTO(e.getMessage(), HttpStatus.UNAUTHORIZED.value()), HttpStatus.UNAUTHORIZED);
    }
}
