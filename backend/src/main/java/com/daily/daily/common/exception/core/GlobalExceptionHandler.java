package com.daily.daily.common.exception.core;

import com.daily.daily.common.dto.ExceptionResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 스프링에서 제공하는 Validation 에서 예외가 발생했을 때 MethodArgumentNotValidException이 발생된다.
     */

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponseDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        final String defaultMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();

        log.warn(defaultMessage);

        return ResponseEntity.badRequest()
                .body(new ExceptionResponseDTO(defaultMessage, 400));
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionResponseDTO> handleCustomException(CustomException customException) {
        ErrorCode errorCode = customException.getErrorCode();

        return ResponseEntity.status(errorCode.getStatusCode())
                .body(ExceptionResponseDTO.of(errorCode));
    }
}
