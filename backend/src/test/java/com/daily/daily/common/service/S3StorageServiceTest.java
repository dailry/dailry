package com.daily.daily.common.service;

import com.daily.daily.common.exception.FileContentTypeUnmatchedException;
import io.awspring.cloud.s3.S3Operations;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class S3StorageServiceTest {

    @Mock
    S3Operations s3Operations;
    @InjectMocks
    S3StorageService s3StorageService;
    @Test
    @DisplayName("이미지가 파일이 아닌 파일을 업로드하려고 할 경우 FileContentTypeUnmatchedException 예외가 발생한다.")
    void uploadImage() {
        //given
        MockMultipartFile textFile = new MockMultipartFile(
                "textFile",
                "example.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "".getBytes()
        );

        //when, then
        assertThatThrownBy(() -> s3StorageService.uploadImage(textFile, "post/", ""))
                .isInstanceOf(FileContentTypeUnmatchedException.class);
    }
}