package com.example.uploadapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación Upload API.
 * Esta clase inicia la aplicación Spring Boot.
 */
@SpringBootApplication
public class UploadApiApplication {

    /**
     * Método principal que arranca la aplicación.
     *
     * @param args argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        SpringApplication.run(UploadApiApplication.class, args);
    }
}