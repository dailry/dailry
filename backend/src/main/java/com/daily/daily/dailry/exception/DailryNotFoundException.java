package com.daily.daily.dailry.exception;

import java.util.NoSuchElementException;

public class DailryNotFoundException extends NoSuchElementException{
        public DailryNotFoundException() {
            super("해당 다일리를 찾을 수 없습니다.");
        }
}
