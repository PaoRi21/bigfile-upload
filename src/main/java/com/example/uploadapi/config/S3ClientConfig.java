package com.example.uploadapi.config;

import com.example.uploadapi.config.properties.AwsSecretsProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3ClientConfig {
    private final AwsSecretsProperties awsSecrets;

    public S3ClientConfig(AwsSecretsProperties awsSecrets) {
        this.awsSecrets = awsSecrets;
    }

    @Bean
    public S3Client s3Client() {
        AwsBasicCredentials credentials = AwsBasicCredentials.create(
                awsSecrets.getAwsAccessKeyId(),
                awsSecrets.getAwsSecretAccessKey()
        );

        return S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .region(Region.of(awsSecrets.getRegion()))
                .build();
    }
}
