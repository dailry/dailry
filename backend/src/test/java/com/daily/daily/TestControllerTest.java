package com.daily.daily;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestControllerTest {

    @Test
    void test1() {
        Assertions.assertThat("테스트").isEqualTo("테스트");
    }
}