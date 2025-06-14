package com.example.uploadapi.upload.controller;

import com.example.uploadapi.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.PreRemove;

import static com.example.uploadapi.constants.ApiConstants.BASE_URL_UPLOAD;

@RestController
@RequestMapping(BASE_URL_UPLOAD)
@Tag(name = "Carga de Archivos",
        description = "Operaciones relacionadas con la carga de archivos")
@SecurityRequirement(name = "bearerAuth")
public class FileUploadController {

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/upload")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Operation(summary = "Subir archivo", description = "Permite subir un archivo si estás autenticado")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El archivo está vacío");
        }

        // Simulamos que se subió correctamente
        return ResponseEntity.ok("Archivo " + file.getOriginalFilename() + " subido correctamente.");
    }

    @GetMapping("/test")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Operation(
            summary = "Test de autenticación",
            description = "Prueba si el token JWT es válido"
    )
    public ResponseEntity<String> testUploadAccess() {
        return ResponseEntity.ok("Acceso autorizado: el token es válido");
    }
}
