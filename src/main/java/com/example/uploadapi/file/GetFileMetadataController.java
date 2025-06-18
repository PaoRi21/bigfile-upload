package com.example.uploadapi.file;

import com.example.uploadapi.commons.util.JwtUtil;
import com.example.uploadapi.unit.S3StorageService;
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

/**
 * Controlador para la consulta de metadata de archivos en AWS S3.
 * Proporciona un endpoint para obtener información detallada de un archivo específico.
 */
@RestController
@RequestMapping(BASE_URL_FILE)
@Tag(name = "Consulta de Archivos",
        description = "Obtiene metadata de archivos en S3")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class GetFileMetadataController {

    /**
     * Servicio para manejar la lógica de interacción con AWS S3.
     */
    private final S3StorageService s3StorageService;

    /**
     * Utilidad para manejar operaciones relacionadas con JWT.
     */
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Endpoint para obtener la metadata de un archivo en AWS S3.
     * Requiere autenticación y autorización con roles 'ADMIN' o 'USER'.
     *
     * @param filename el nombre del archivo del cual se desea obtener la metadata.
     * @return una respuesta HTTP con la metadata del archivo o un mensaje de error si el archivo no se encuentra.
     */
    @GetMapping("/metadata")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> getMetadata(@RequestParam String filename) {
        try {
            Map<String, Object> metadata = s3StorageService.getFileMetadata(filename);
            return ResponseEntity.ok(metadata);
        } catch (NoSuchKeyException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Archivo no encontrado");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener metadata");
        }
    }
}