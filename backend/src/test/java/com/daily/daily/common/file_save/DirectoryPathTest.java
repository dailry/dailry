package com.daily.daily.common.file_save;

import com.daily.daily.common.domain.DirectoryPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DirectoryPathTest {

    @Test
    @DisplayName("DirectoryPath의 path는 '/'로 시작해야한다. 그리고 끝에는 '/'가 존재하지 않는다")
    void test1() {
        DirectoryPath directoryPath = DirectoryPath.of("dir1", "dir2", "dir3");

        String path = directoryPath.getPath();

        assertThat(path).startsWith("/");
        assertThat(path).doesNotEndWith("/");
    }
}