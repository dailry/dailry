package com.daily.daily.postcomment.exception;

public class PostCommentNotFoundException extends RuntimeException{
    public PostCommentNotFoundException() {
        super("존재하지 않는 댓글입니다.");
    }
}
