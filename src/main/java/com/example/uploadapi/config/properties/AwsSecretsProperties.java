package com.example.uploadapi.config.properties;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Propiedades de configuración para los secretos de AWS.
 * Esta clase contiene las credenciales y configuraciones necesarias para acceder a los servicios de AWS.
 */
@Data
public class AwsSecretsProperties {
    /**
     * El ID de clave de acceso de AWS utilizado para la autenticación.
     */
    @JsonProperty("aws.accessKeyId")
    private String awsAccessKeyId;

    /**
     * La clave de acceso secreta de AWS utilizada para la autenticación.
     */
    @JsonProperty("aws.secretAccessKey")
    private String awsSecretAccessKey;

    /**
     * El nombre del bucket de AWS S3.
     */
    @JsonProperty("bucket.name")
    private String bucketName;

    /**
     * La región de AWS donde se encuentran los recursos.
     */
    private String region;
}