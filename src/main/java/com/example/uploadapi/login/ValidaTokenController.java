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

/**
 * Controlador para validar la autenticación mediante un token JWT.
 * Proporciona un endpoint para verificar si el token es válido y el acceso está autorizado.
 */
@RestController
@RequestMapping(BASE_URL_AUTH)
@Tag(name = "Valida autenticación",
        description = "Controlador para validar token")
@SecurityRequirement(name = "bearerAuth")
public class ValidaTokenController {

    /**
     * Utilidad para manejar operaciones relacionadas con JWT, como validación de tokens.
     */
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Endpoint para probar la validez de un token JWT.
     * Requiere autenticación y autorización con roles 'ROLE_ADMIN' o 'ROLE_USER'.
     *
     * @return una respuesta HTTP indicando que el acceso está autorizado si el token es válido.
     */
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