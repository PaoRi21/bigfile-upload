package com.example.uploadapi.commons.config;

import com.example.uploadapi.commons.dto.AwsSecretsProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;

/**
 * Clase de configuración para cargar secretos de AWS utilizando AWS Secrets Manager.
 * Proporciona un bean para obtener las propiedades de los secretos de AWS.
 */
@Slf4j
@Component
@Configuration
@Profile("!test")
public class AwsSecretsLoaderConfig {

    /**
     * Nombre del secreto en AWS Secrets Manager.
     */
    private final String SECRET_NAME = "bigfile/upload/creds";

    /**
     * Región de AWS donde se encuentra el secreto.
     */
    private final Region AWS_REGION = Region.US_EAST_1;

    /**
     * Clave de acceso de AWS utilizada como respaldo en caso de que no se pueda obtener el secreto.
     */
    @Value("${aws.accessKeyId}")
    private String fallbackAccessKey;

    /**
     * Clave secreta de AWS utilizada como respaldo en caso de que no se pueda obtener el secreto.
     */
    @Value("${aws.secretAccessKey}")
    private String fallbackSecretKey;

    /**
     * Bean que carga las propiedades del secreto de AWS desde AWS Secrets Manager.
     *
     * @return una instancia de {@link AwsSecretsProperties} con las propiedades del secreto.
     * @throws RuntimeException si ocurre un error al obtener el secreto.
     */
    @Bean
    public AwsSecretsProperties awsSecretsProperties() {

        SecretsManagerClient client = SecretsManagerClient.builder()
                .region(AWS_REGION)
                //.credentialsProvider(StaticCredentialsProvider.create(baseCreds))
                .build();

        GetSecretValueRequest request = GetSecretValueRequest.builder()
                .secretId(SECRET_NAME)
                .build();

        try {
            String secretJson = client.getSecretValue(request).secretString();
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(secretJson, AwsSecretsProperties.class);
        } catch (Exception e) {
            log.error("No se pudo cargar el secreto", e);
            throw new RuntimeException("Fallo al obtener el secreto de AWS", e);
        }
    }
}