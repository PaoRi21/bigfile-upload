package com.example.uploadapi.unit;

import com.example.uploadapi.commons.dto.AwsSecretsProperties;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class S3UploadServiceTest {

    @Mock
    private S3Client s3Client;

    @Mock
    private AwsSecretsProperties awsSecrets;

    @Mock
    private MultipartFile multipartFile;

    private S3StorageService s3StorageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        s3StorageService = new S3StorageService(s3Client, awsSecrets);
    }

    @Test
    void testUploadFile() throws IOException {
        // Configuración de mocks
        String bucketName = "test-bucket";
        String fileName = "test-file.txt";
        byte[] fileContent = "test content".getBytes();

        when(awsSecrets.getBucketName()).thenReturn(bucketName);
        when(multipartFile.getOriginalFilename()).thenReturn(fileName);
        when(multipartFile.getContentType()).thenReturn("text/plain");
        when(multipartFile.getInputStream()).thenReturn(new ByteArrayInputStream(fileContent));
        when(multipartFile.getSize()).thenReturn((long) fileContent.length);

        // Ejecución del método
        String result = s3StorageService.uploadFile(multipartFile);

        // Verificación
        assertEquals("Archivo subido correctamente: test-file.txt", result);
        verify(s3Client).putObject(any(PutObjectRequest.class), (RequestBody) any());
    }

    @Test
    void testGetFileMetadata() {
        // Configuración de mocks
        String bucketName = "test-bucket";
        String fileName = "test-file.txt";

        HeadObjectResponse headObjectResponse = mock(HeadObjectResponse.class);
        when(awsSecrets.getBucketName()).thenReturn(bucketName);
        when(s3Client.headObject(any(HeadObjectRequest.class))).thenReturn(headObjectResponse);
        when(headObjectResponse.contentLength()).thenReturn(1024L);
        when(headObjectResponse.contentType()).thenReturn("text/plain");
        when(headObjectResponse.lastModified()).thenReturn(null);

        // Ejecución del método
        Map<String, Object> metadata = s3StorageService.getFileMetadata(fileName);

        // Verificación
        assertNotNull(metadata);
        assertEquals("test-file.txt", metadata.get("fileName"));
        assertEquals("0.00 MB", metadata.get("sizeMb"));
        assertEquals("text/plain", metadata.get("contentType"));
        assertNull(metadata.get("lastModified"));
    }


}