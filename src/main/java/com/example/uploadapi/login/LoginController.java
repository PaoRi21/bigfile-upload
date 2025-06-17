package com.example.uploadapi.login;

import com.example.uploadapi.commons.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.example.uploadapi.commons.constants.ApiConstants.BASE_URL_AUTH;

/**
 * Controlador para la autenticación de usuarios.
 * Proporciona un endpoint para iniciar sesión y generar un token JWT.
 */
@RestController
@RequestMapping(BASE_URL_AUTH)
@Tag(name = "Autenticación",
        description = "Controlador para iniciar sesión y generar token JWT")
public class LoginController {

    /**
     * Administrador de autenticación utilizado para validar las credenciales del usuario.
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Utilidad para manejar operaciones relacionadas con JWT, como generación y validación de tokens.
     */
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Endpoint para autenticar al usuario y devolver un token JWT.
     *
     * @param credentials un mapa que contiene las credenciales del usuario (nombre de usuario y contraseña).
     * @return una respuesta HTTP con el token JWT, el nombre de usuario y el rol del usuario autenticado.
     */
    @Operation(summary = "Login", description = "Autentica al usuario y devuelve un token JWT")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        UserDetails userDetails = (UserDetails) auth.getPrincipal();

        String role = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("USER");

        String token = jwtUtil.generateToken(username, role);

        return ResponseEntity.ok(Map.of(
                "token", token,
                "username", username,
                "role", role
        ));
    }
}