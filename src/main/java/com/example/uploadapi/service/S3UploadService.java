package com.example.uploadapi.service;

import com.example.uploadapi.commons.dto.AwsSecretsProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class S3UploadService {
    private final S3Client s3Client;
    private final AwsSecretsProperties awsSecrets;

    public String uploadFile(MultipartFile file) {
        String bucketName = awsSecrets.getBucketName();
        String fileName = file.getOriginalFilename();

        try {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(request, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            return "Archivo subido correctamente: " + fileName;

        } catch (IOException e) {
            throw new RuntimeException("Error al subir archivo a S3", e);
        }
    }
}
