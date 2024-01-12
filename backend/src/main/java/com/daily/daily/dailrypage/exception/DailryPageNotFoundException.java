package com.daily.daily.dailrypage.exception;

import java.util.NoSuchElementException;

public class DailryPageNotFoundException extends NoSuchElementException {
    public DailryPageNotFoundException() {
        super("해당 다일리 페이지를 찾을 수 없습니다.");
    }

}
