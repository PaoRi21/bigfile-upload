package com.example.uploadapi.unit;

import com.example.uploadapi.commons.dto.AwsSecretsProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Servicio para manejar la lógica de interacción con AWS S3.
 * Proporciona métodos para subir archivos y obtener metadata de archivos almacenados en S3.
 */
@Service
@RequiredArgsConstructor
public class S3StorageService {
    /**
     * Cliente de AWS S3 utilizado para interactuar con el servicio de almacenamiento.
     */
    private final S3Client s3Client;

    /**
     * Propiedades de configuración relacionadas con los secretos de AWS, como el nombre del bucket.
     */
    private final AwsSecretsProperties awsSecrets;

    /**
     * Sube un archivo al bucket de AWS S3 especificado en las propiedades de configuración.
     *
     * @param file el archivo que se desea subir, proporcionado como un objeto MultipartFile.
     * @return un mensaje indicando que el archivo se subió correctamente, incluyendo su nombre.
     * @throws RuntimeException si ocurre un error durante la subida del archivo.
     */
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

    /**
     * Obtiene la metadata de un archivo almacenado en el bucket de AWS S3.
     *
     * @param fileName el nombre del archivo del cual se desea obtener la metadata.
     * @return un mapa que contiene información sobre el archivo, como tamaño, tipo de contenido y última modificación.
     */
    public Map<String, Object> getFileMetadata(String fileName) {
        String bucketName = awsSecrets.getBucketName();

        HeadObjectRequest request = HeadObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();

        HeadObjectResponse response = s3Client.headObject(request);

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("fileName", fileName);
        metadata.put("sizeMb", String.format("%.2f MB", (double) response.contentLength() / (1024 * 1024)));
        metadata.put("contentType", response.contentType());
        metadata.put("lastModified", response.lastModified());
        return metadata;
    }
}