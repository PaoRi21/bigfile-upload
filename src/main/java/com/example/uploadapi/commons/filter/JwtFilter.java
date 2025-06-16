package com.example.uploadapi.commons.filter;

import com.example.uploadapi.commons.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

/**
 * Filtro para la validación de tokens JWT en cada solicitud HTTP.
 * Extiende {@link OncePerRequestFilter} para garantizar que se ejecute una vez por solicitud.
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

    /**
     * Utilidad para manejar operaciones relacionadas con JWT, como validación y extracción de datos.
     */
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Método que intercepta las solicitudes HTTP y valida el token JWT presente en el encabezado de autorización.
     *
     * @param request     la solicitud HTTP entrante.
     * @param response    la respuesta HTTP saliente.
     * @param filterChain la cadena de filtros que permite continuar con el procesamiento de la solicitud.
     * @throws ServletException si ocurre un error relacionado con el servlet.
     * @throws IOException      si ocurre un error de entrada/salida.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // Obtener el encabezado de autorización de la solicitud.
        String authHeader = request.getHeader("Authorization");

        // Validar que el encabezado contenga un token JWT válido.
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Extraer el token eliminando el prefijo "Bearer ".
            if (jwtUtil.validateToken(token)) { // Validar el token utilizando JwtUtil.
                String username = jwtUtil.extractUsername(token); // Extraer el nombre de usuario del token.
                String role = jwtUtil.extractUserRole(token); // Extraer el rol del usuario del token.

                // Crear un objeto de autenticación con el nombre de usuario y el rol.
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                username,
                                null,
                                Collections.singletonList(new SimpleGrantedAuthority(role))
                        );
                // Configurar detalles adicionales de la autenticación.
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Establecer el contexto de seguridad con la autenticación.
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        // Continuar con la cadena de filtros.
        filterChain.doFilter(request, response);
    }
}