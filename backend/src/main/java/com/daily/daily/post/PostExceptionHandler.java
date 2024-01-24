package com.daily.daily.post;

import com.daily.daily.common.dto.ExceptionResponseDTO;
import com.daily.daily.post.exception.AlreadyLikeException;
import com.daily.daily.post.exception.LikeDecreaseNotAllowedException;
import com.daily.daily.post.exception.PostNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class PostExceptionHandler {
    @ExceptionHandler({
            PostNotFoundException.class
    })
    public ResponseEntity<ExceptionResponseDTO> handleNotFoundException(RuntimeException e) {
        return new ResponseEntity<>(new ExceptionResponseDTO(e.getMessage(), 404), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
            AlreadyLikeException.class,
            LikeDecreaseNotAllowedException.class
    })
    public ResponseEntity<ExceptionResponseDTO> handleLikeException(RuntimeException e) {
        return new ResponseEntity<>(new ExceptionResponseDTO(e.getMessage(), 409), HttpStatus.CONFLICT);
    }
}
