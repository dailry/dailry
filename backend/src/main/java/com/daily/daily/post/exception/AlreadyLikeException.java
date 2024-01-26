package com.daily.daily.post.exception;

public class AlreadyLikeException extends RuntimeException{
    public AlreadyLikeException() {
        super("이미 좋아요를 누른 게시글입니다.");
    }
}
