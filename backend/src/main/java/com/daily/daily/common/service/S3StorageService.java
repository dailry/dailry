package com.daily.daily.common.service;

import com.daily.daily.common.domain.DirectoryPath;
import com.daily.daily.common.exception.FileContentTypeUnmatchedException;
import com.daily.daily.common.exception.FileUploadFailureException;
import io.awspring.cloud.s3.S3Operations;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3StorageService {

    @Value("${spring.cloud.aws.s3.bucket}")
    private String BUCKET_NAME;
    @Value("${app.properties.dataStorageDomain}")
    private String dataStorageDomain;

    private final S3Operations s3Operations;

    public URI uploadImage(MultipartFile imageFile, DirectoryPath directoryPath, String fileName) {
        log.info("파일 업로드 시작");

        URI fileURI = URI.create("https://" + dataStorageDomain + directoryPath.getPath()+ "/" + fileName);
        validateImageContentType(imageFile);

        try {
            String key = fileURI.getPath().substring(1); // s3의 key 는 '/'를 제외하고 시작
            s3Operations.upload(BUCKET_NAME, key, imageFile.getInputStream());
            return fileURI;
        } catch (IOException e) {
            throw new FileUploadFailureException(e);
        }
    }
    private void validateImageContentType(MultipartFile imageFile) {
        String contentType = imageFile.getContentType();

        if (contentType == null || !contentType.startsWith("image/")) {
            throw new FileContentTypeUnmatchedException();
        }
    }
}
