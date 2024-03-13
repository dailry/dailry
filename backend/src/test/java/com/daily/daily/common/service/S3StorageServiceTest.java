package com.daily.daily.common.service;

import com.daily.daily.common.domain.DirectoryPath;
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

import java.net.URI;

import static com.daily.daily.dailrypage.fixture.DailryPageFixture.다일리_페이지_섬네일_파일;
import static org.assertj.core.api.Assertions.assertThat;
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
        assertThatThrownBy(() -> s3StorageService.uploadImage(textFile, DirectoryPath.of("post"), ""))
                .isInstanceOf(FileContentTypeUnmatchedException.class);
    }

    @Test
    @DisplayName("파일 업로드 후 반환하는 URI의 포맷이 올바른지 확인한다.")
    void uploadImageURIFormatTest() {
        //given, when
        URI uri = s3StorageService.uploadImage(다일리_페이지_섬네일_파일(),
                DirectoryPath.of("thumbnail", "31", "22"),
                "41");

        //then
        assertThat(uri.getPath()).isEqualTo("/thumbnail/31/22/41");
        assertThat(uri.toString()).startsWith("https://");
    }
}