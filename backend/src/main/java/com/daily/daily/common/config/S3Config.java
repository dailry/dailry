package com.daily.daily.common.config;

import io.awspring.cloud.autoconfigure.core.CredentialsProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
@RequiredArgsConstructor
public class S3Config {
    private final CredentialsProperties credentialsProperties;

    @Bean
    public S3Client s3Client () {
        String accessKey = credentialsProperties.getAccessKey();
        String secretKey = credentialsProperties.getSecretKey();

        System.setProperty("aws.region", "ap-northeast-2");
        return S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
                .build();
    }
}
