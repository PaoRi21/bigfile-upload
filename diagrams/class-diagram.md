<!--
Copilot, genera los pasos un diagrama de clases Mermaid basado en las clases de este proyecto.
-->
```mermaid
classDiagram
    class AwsSecretsProperties {
        +String region
        +String accessKeyId
        +String secretAccessKey
        +String bucketName
    }

    class AwsSecretsLoaderConfig {
        +AwsSecretsProperties awsSecretsProperties()
    }

    class AwsUserSecretsConfig {
        +AwsSecretsProperties awsSecretsProperties()
    }

    class AppUserProperties {
        +String username
        +String password
    }

    AwsSecretsLoaderConfig --> AwsSecretsProperties
    AwsUserSecretsConfig --> AwsSecretsProperties
    AwsUserSecretsConfig --> AppUserProperties