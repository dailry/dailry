package com.daily.daily.dailrypage.exception;

import java.util.NoSuchElementException;

public class DailryPageThumbnailNotFoundException extends NoSuchElementException {
    public DailryPageThumbnailNotFoundException() {
        super("해당 다일리 페이지의 섬네일을 찾을 수 없습니다.");
    }

}
