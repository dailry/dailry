package com.daily.daily.common;

import com.daily.daily.auth.exception.LoginFailureException;
import com.daily.daily.common.dto.ExceptionResponseDTO;
import com.daily.daily.common.exception.FileUploadFailureException;
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
                .body(new ExceptionResponseDTO(defaultMessage, 400));
    }

    @ExceptionHandler(FileUploadFailureException.class)
    public ResponseEntity<ExceptionResponseDTO> handleFileUploadFailureException(FileUploadFailureException e) {
        log.error("파일 업로드 실패 {}", e.getOriginalException().getMessage());

        return ResponseEntity.badRequest()
                .body(new ExceptionResponseDTO(e.getMessage(), 400));
    }
}
