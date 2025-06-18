package com.example.uploadapi.commons.constants;

import lombok.NoArgsConstructor;

/**
 * Clase que define constantes utilizadas en las rutas de la API.
 * Proporciona las URLs base para los endpoints de autenticación y manejo de archivos.
 */
@NoArgsConstructor
public class ApiConstants {
    /**
     * URL base para los endpoints relacionados con la autenticación.
     */
    public static final String BASE_URL_AUTH = "/api/v1/auth";

    /**
     * URL base para los endpoints relacionados con el manejo de archivos.
     */
    public static final String BASE_URL_FILE = "/api/v1/file";
}