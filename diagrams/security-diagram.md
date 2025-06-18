<!--
Copilot, genera los pasos un diagrama de seguridad con Mermaid basado en todas las clases de este proyecto.
-->
```mermaid
flowchart TD
    subgraph Seguridad
        A[Autenticación JWT] --> B[Protección por roles]
        B --> C[Endpoints protegidos con @PreAuthorize]
        C --> D[Acceso a AWS Secrets Manager]
        D --> E[Credenciales seguras]
        E --> F[Propiedades de configuración: AwsSecretsProperties]
        F --> G[Clases relacionadas: AwsSecretsLoaderConfig, AwsUserSecretsConfig]
        G --> H[Usuarios y roles: AppUserProperties]
    end_