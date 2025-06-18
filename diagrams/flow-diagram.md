<!--
Copilot, genera los pasos un diagrama de sequencia con Mermaid basado en todas las clases de este proyecto.
-->
```mermaid
graph TD
A[Usuario Frontend/Postman] -->|Envía JWT| B[API Gateway o Controlador]
B -->|Verifica token y rol| C[Spring Security]
C -->|Token válido| D[FileUploadController]
D -->|MultipartFile| E[S3StorageService]
E -->|Crea PutObjectRequest| F[AWS S3 SDK v2]
F -->|Sube archivo| G[S3 Bucket: bigfile-upload-paori21]
G -->|Respuesta OK| H[API]
H --> A
