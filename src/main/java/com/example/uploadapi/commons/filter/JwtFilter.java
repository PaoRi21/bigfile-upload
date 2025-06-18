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

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (jwtUtil.validateToken(token)) {
                String username = jwtUtil.extractUsername(token);
                String role = jwtUtil.extractUserRole(token);

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                username,
                                null,
                                Collections.singletonList(new SimpleGrantedAuthority(role))
                                //Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role))
                        );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}