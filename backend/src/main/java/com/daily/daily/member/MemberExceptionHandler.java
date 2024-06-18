package com.daily.daily.member;

import com.daily.daily.auth.exception.LoginFailureException;
import com.daily.daily.common.dto.ExceptionResponseDTO;
import com.daily.daily.member.exception.CertificationNumberExpirationException;
import com.daily.daily.member.exception.CertificationNumberUnmatchedException;
import com.daily.daily.member.exception.DuplicatedEmailException;
import com.daily.daily.member.exception.DuplicatedNicknameException;
import com.daily.daily.member.exception.DuplicatedUsernameException;
import com.daily.daily.member.exception.InvalidEmailException;
import com.daily.daily.member.exception.MemberNotFoundException;
import com.daily.daily.member.exception.PasswordUnmatchedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class MemberExceptionHandler {
    @ExceptionHandler({
            DuplicatedUsernameException.class,
            DuplicatedNicknameException.class,
            DuplicatedEmailException.class
    })
    public ResponseEntity<ExceptionResponseDTO> handleDuplicatedException(IllegalArgumentException e) {
        return new ResponseEntity<>(new ExceptionResponseDTO(e.getMessage(), HttpStatus.CONFLICT.value()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler({
            PasswordUnmatchedException.class,
            CertificationNumberUnmatchedException.class,
            CertificationNumberExpirationException.class
    })
    public ResponseEntity<ExceptionResponseDTO> handleUnauthorizedException(IllegalArgumentException e) {
        return new ResponseEntity<>(new ExceptionResponseDTO(e.getMessage(), HttpStatus.UNAUTHORIZED.value()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(LoginFailureException.class)
    public ResponseEntity<ExceptionResponseDTO> handleLoginFailureException(RuntimeException e) {
        return new ResponseEntity<>(new ExceptionResponseDTO(e.getMessage(), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            MemberNotFoundException.class,
            InvalidEmailException.class
    })
    public ResponseEntity<ExceptionResponseDTO> handleNotFoundException(RuntimeException e) {
        return new ResponseEntity<>(new ExceptionResponseDTO(e.getMessage(), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
    }
}
