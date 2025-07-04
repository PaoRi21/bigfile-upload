# 📦 bigfile-upload

Proyecto Spring Boot que permite la carga de archivos grandes a Amazon S3 de forma segura, usando autenticación JWT y configuración vía Secrets Manager.

---

## 🚀 Funcionalidades

- ✅ Autenticación con JWT
- ✅ Protección por roles
- ✅ Carga de archivos (`MultipartFile`) directamente a AWS S3
- ✅ Lectura de credenciales seguras desde AWS Secrets Manager
- ✅ Documentación Swagger OpenAPI
- ✅ Consulta de metadata del archivo desde S3
- ✅ Preparado para despliegue en EC2 con IAM Role

---

## 🔐 Seguridad

- Los endpoints están protegidos con `@PreAuthorize`
- Solo usuarios autenticados con roles válidos pueden subir o consultar archivos
- Las credenciales AWS **no están hardcodeadas**, se obtienen desde Secrets Manager

---

## 📂 Endpoints principales

Swagger: https://pao21.online/swagger-ui/index.html

| Método | URL                                   | Descripción |
|--------|---------------------------------------|-------------|
| POST | `/api/v1/file/upload`                | Sube un archivo a S3 |
| GET  | `/api/v1/file/metadata?filename=...` | Consulta el tamaño, tipo y fecha de un archivo en S3 |
| POST | `/api/v1/auth/login`                  | Login que retorna un token JWT |

---

## 🧪 Cómo probar

1. Autenticarse con:
```bash
POST /api/v1/auth/login
Body:
{
  "username": "username",
  "password": "password",
  "role": "role"
}
```

2. Usar el token JWT para autorizar subida de archivos en Swagger o Postman.

---

## ⚙️ Tecnologías

- Java 17
- Spring Boot 3.2.5
- Spring Security
- AWS S3 + Secrets Manager (SDK v2)
- Swagger (SpringDoc)
- Maven

---

## 📦 Diagrama de clases

![Editor _ Mermaid Chart-2025-06-18-164002.png](images%2FEditor%20_%20Mermaid%20Chart-2025-06-18-164002.png)

---
## 📦 Diagrama de sequencia

![Editor _ Mermaid Chart-2025-06-18-164331.png](images%2FEditor%20_%20Mermaid%20Chart-2025-06-18-164331.png)

---
## 📦 Diagrama de seguridad

![Editor _ Mermaid Chart-2025-06-18-163926.png](images%2FEditor%20_%20Mermaid%20Chart-2025-06-18-163926.png)

---
## ☁️ Despliegue

El proyecto puede ser desplegado en AWS EC2, usando un IAM Role con permisos para:

- `s3:PutObject`, `s3:GetObject`
- `secretsmanager:GetSecretValue`

---

## 👩‍💻 Autora

Pao Rivera - [@paori21](https://github.com/paori21)  
*Java backend developer