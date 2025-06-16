package com.example.uploadapi.commons.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/**
 * Clase de utilidad para manejar operaciones relacionadas con JWT (JSON Web Tokens).
 * Proporciona métodos para generar, validar y extraer información de los tokens JWT.
 */
@Component
public class JwtUtil {

    /**
     * Clave secreta utilizada para firmar los tokens JWT.
     * Debe ser suficientemente larga para garantizar la seguridad.
     */
    private static final String SECRET_KEY = "mi_clave_super_secreta_para_jwt_que_debe_ser_larga";

    /**
     * Tiempo de expiración de los tokens JWT en milisegundos (1 hora).
     */
    private static final long EXPIRATION_TIME = 1000 * 60 * 60;

    /**
     * Llave generada a partir de la clave secreta para firmar los tokens JWT.
     */
    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    /**
     * Clave secreta configurada a través de las propiedades de la aplicación.
     */
    @Value("${jwt.secret}")
    private String secret;

    /**
     * Genera un token JWT con el nombre de usuario y el rol especificados.
     *
     * @param username el nombre de usuario que se incluirá en el token.
     * @param role     el rol del usuario que se incluirá como un reclamo en el token.
     * @return el token JWT generado como una cadena compacta.
     */
    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Valida un token JWT verificando su firma y estructura.
     *
     * @param token el token JWT que se desea validar.
     * @return true si el token es válido, false en caso contrario.
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    /**
     * Extrae el nombre de usuario del token JWT.
     *
     * @param token el token JWT del cual se desea extraer el nombre de usuario.
     * @return el nombre de usuario contenido en el token.
     */
    public String extractUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    /**
     * Extrae el rol del usuario del token JWT.
     *
     * @param token el token JWT del cual se desea extraer el rol del usuario.
     * @return el rol del usuario contenido en el token.
     */
    public String extractUserRole(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("role", String.class);
    }
}