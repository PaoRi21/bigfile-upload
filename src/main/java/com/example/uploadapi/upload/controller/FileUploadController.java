package com.example.uploadapi.upload.controller;

import com.example.uploadapi.service.S3UploadService;
import com.example.uploadapi.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;


import java.io.IOException;

import static com.example.uploadapi.constants.ApiConstants.BASE_URL_UPLOAD;

@RestController
@RequestMapping(BASE_URL_UPLOAD)
@Tag(name = "Carga de Archivos",
        description = "Operaciones relacionadas con la carga de archivos")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class FileUploadController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private S3Client s3Client;

    private final S3UploadService s3UploadService;

    @PostMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @Operation(
            summary = "Subir archivo a S3",
            description = "Permite subir un archivo si estás autenticado")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        System.out.println("🔄 Subiendo archivo: " + file.getOriginalFilename());
        String result = s3UploadService.uploadFile(file);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/test")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @Operation(
            summary = "Test de autenticación",
            description = "Prueba si el token JWT es válido"
    )
    public ResponseEntity<String> testUploadAccess() {
        return ResponseEntity.ok("Acceso autorizado: el token es válido");
    }
}
