package com.example.uploadapi.file;

import com.example.uploadapi.commons.util.JwtUtil;
import com.example.uploadapi.service.S3UploadService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;

import java.util.Map;

import static com.example.uploadapi.commons.constants.ApiConstants.BASE_URL_FILE;

@RestController
@RequestMapping(BASE_URL_FILE)
@Tag(name = "Consulta de Archivos",
        description = "Obtiene metadata de archivos en S3")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class GetFileMetadataController {

    private final S3UploadService s3UploadService;
    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/metadata")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> getMetadata(@RequestParam String filename) {
        try {
            Map<String, Object> metadata = s3UploadService.getFileMetadata(filename);
            return ResponseEntity.ok(metadata);
        } catch (NoSuchKeyException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Archivo no encontrado");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener metadata");
        }
    }
}
