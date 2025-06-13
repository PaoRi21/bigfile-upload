package com.example.uploadapi.controller;

import com.example.uploadapi.dto.LoginRequest;
import com.example.uploadapi.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.*;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

import static com.example.uploadapi.constants.ApiConstants.BASE_URL_AUTH;

@RestController
@RequestMapping(BASE_URL_AUTH)
@Tag(name = "Autenticación", description = "Controlador para iniciar sesión y generar token JWT")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Operation(
            summary = "Iniciar sesión",
            description = "Recibe credenciales y devuelve un JWT si son válidas",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Token generado exitosamente",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(example = "{\"token\": \"JWT_TOKEN_GENERADO\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Credenciales inválidas",
                            content = @Content(schema = @Schema(example = "\"Credenciales inválidas\""))
                    )
            }
    )
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        final String token = jwtUtil.generateToken(userDetails.getUsername());

        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }
}
