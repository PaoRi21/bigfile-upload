package com.example.uploadapi.commons.dto;

import lombok.Data;

/**
 * Clase que representa la solicitud de inicio de sesión.
 * Contiene los datos necesarios para autenticar a un usuario en el sistema.
 */
@Data
public class LoginRequest {
    /**
     * Nombre de usuario utilizado para la autenticación.
     */
    private String username;

    /**
     * Contraseña utilizada para la autenticación.
     */
    private String password;
}