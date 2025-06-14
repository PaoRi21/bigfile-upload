package com.example.uploadapi.config;

import com.example.uploadapi.config.properties.AwsSecretsProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;

@Slf4j
@Configuration
public class AwsSecretsLoaderConfig {
    @Value("${aws.accessKeyId}")
    private String fallbackAccessKey;

    @Value("${aws.secretAccessKey}")
    private String fallbackSecretKey;

    private final String SECRET_NAME = "bigfile/upload/creds";
    private final Region AWS_REGION = Region.US_EAST_1;

    @Bean
    public AwsSecretsProperties awsSecretsProperties() {

        //AwsBasicCredentials baseCreds = AwsBasicCredentials.create(fallbackAccessKey, fallbackSecretKey);

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
