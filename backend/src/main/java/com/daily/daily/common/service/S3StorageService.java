package com.daily.daily.common.service;

import com.daily.daily.common.exception.FileContentTypeUnmatchedException;
import com.daily.daily.common.exception.FileUploadFailureException;
import io.awspring.cloud.s3.S3Operations;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3StorageService {

    @Value("${spring.cloud.aws.s3.bucket}")
    private String BUCKET_NAME;

    private final S3Operations s3Operations;

    /**
     * 파일을 업로드하고 파일 경로를 반환합니다.
     * @param imageFile 업로드 하고 싶은 파일. 이미지 형식이 아닐경우 예외 발생.
     * @param directoryPath 저장하고 싶은 S3저장소의 디렉토리 경로.
     * @param filePrefix 저장하고 싶은 파일이름의 prefix
     * @return 저장한 파일의 전체 경로를 반환합니다.
     * 파일경로는 다음과 같은 규칙에 의해 정해집니다.
     * {directoryPath}/filePrefix_UUID_OriginalFileName
     */

    public String uploadImage(MultipartFile imageFile, String directoryPath, String filePrefix) {
        log.info("파일 업로드 시작");

        validateImageContentType(imageFile);
        String path = directoryPath + "/" +
                filePrefix + "_" +
                UUID.randomUUID() +
                imageFile.getOriginalFilename();

        try {
            s3Operations.upload(BUCKET_NAME, path, imageFile.getInputStream());
            return path;
        } catch (IOException e) {
            throw new FileUploadFailureException(e);
        }
    }

    private void validateImageContentType(MultipartFile imageFile) {
        String contentType = imageFile.getContentType();

        if (contentType == null || !contentType.startsWith("image/")) {
            throw new FileContentTypeUnmatchedException("이미지 파일만 업로드 가능합니다.");
        }
    }
}