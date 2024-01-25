package com.daily.daily.postcomment;

import com.daily.daily.common.dto.ExceptionResponseDTO;
import com.daily.daily.postcomment.exception.PostCommentNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class PostCommentExceptionHandler {

    @ExceptionHandler({
            PostCommentNotFoundException.class,
    })
    public ResponseEntity<ExceptionResponseDTO> handleNotFoundException(RuntimeException e) {
        return new ResponseEntity<>(new ExceptionResponseDTO(e.getMessage(), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
    }
}
