package com.example.uploadapi.config;

import com.example.uploadapi.commons.dto.AwsSecretsProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestAwsConfig {
    @Bean
    public AwsSecretsProperties awsSecretsProperties() {
        AwsSecretsProperties props = new AwsSecretsProperties();
        props.setAccessKey("test-access");
        props.setAwsSecretAccessKey("test-secret");
        props.setBucketName("test-bucket");
        props.setRegion("us-east-1");
        return props;
    }
}