package com.example.uploadapi.commons.config;

import com.example.uploadapi.commons.dto.AwsSecretsProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

/**
 * Clase de configuración para el cliente de AWS S3.
 * Proporciona un bean para inicializar y configurar el cliente de S3 utilizando las credenciales de AWS.
 */
@Configuration
public class S3ClientConfig {

    /**
     * Propiedades de los secretos de AWS que contienen las credenciales y configuración necesarias.
     */
    private final AwsSecretsProperties awsSecrets;

    /**
     * Constructor que inicializa las propiedades de los secretos de AWS.
     *
     * @param awsSecrets instancia de {@link AwsSecretsProperties} con las credenciales y configuración de AWS.
     */
    public S3ClientConfig(AwsSecretsProperties awsSecrets) {
        this.awsSecrets = awsSecrets;
    }

    /**
     * Bean que configura e inicializa el cliente de AWS S3.
     * Utiliza las credenciales y región especificadas en las propiedades de los secretos de AWS.
     *
     * @return una instancia de {@link S3Client} configurada.
     */
    @Bean
    public S3Client s3Client() {
        // Crear las credenciales básicas de AWS utilizando el ID de acceso y la clave secreta.
        AwsBasicCredentials credentials = AwsBasicCredentials.create(
                awsSecrets.getAwsAccessKeyId(),
                awsSecrets.getAwsSecretAccessKey()
        );

        // Construir y devolver el cliente de S3 configurado con las credenciales y la región.
        return S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .region(Region.of(awsSecrets.getRegion()))
                .build();
    }
}