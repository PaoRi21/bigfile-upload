package com.example.uploadapi.file;

import com.example.uploadapi.commons.util.JwtUtil;
import com.example.uploadapi.service.S3UploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;

import java.io.IOException;

import static com.example.uploadapi.commons.constants.ApiConstants.BASE_URL_FILE;

@RestController
@RequestMapping(BASE_URL_FILE)
@Tag(name = "Carga de Archivos",
        description = "Sube archivos a S3")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class FileUploadController {

    private final S3UploadService s3UploadService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private S3Client s3Client;

    @PostMapping("/upload")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @Operation(
            summary = "Subir archivo a S3",
            description = "Permite subir un archivo si estás autenticado")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        System.out.println("🔄 Subiendo archivo: " + file.getOriginalFilename());
        String result = s3UploadService.uploadFile(file);
        return ResponseEntity.ok(result);
    }
}
