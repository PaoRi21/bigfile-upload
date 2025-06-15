package com.example.uploadapi.login;

import com.example.uploadapi.commons.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.uploadapi.commons.constants.ApiConstants.BASE_URL_AUTH;

@RestController
@RequestMapping(BASE_URL_AUTH)
@Tag(name = "Valida autenticación",
        description = "Controlador para validar token")
@SecurityRequirement(name = "bearerAuth")
public class ValidaTokenController {
    @Autowired
    private JwtUtil jwtUtil;

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
