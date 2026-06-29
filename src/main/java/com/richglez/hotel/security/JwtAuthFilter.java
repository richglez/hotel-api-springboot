package com.richglez.hotel.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        // ← If there is no token, simply let it pass — Spring Security
        //    decidirá si el endpoint requiere auth o no
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // pasa estos atributos al siguiente filtro
            return; // se acaba el filtro aqui
        }

        try {
            final String token = authHeader.substring(7);
            final String email = jwtService.extractUsername(token); // ← puede lanzar ExpiredJwtException, MalformedJwtException...

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                if (jwtService.isTokenValid(token, userDetails)) {
                    var authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

        } catch (Exception e) {
            // Token inválido o expirado → limpia el contexto
            // AuthenticationEntryPoint devolverá el 401 limpiamente
            SecurityContextHolder.clearContext();
            System.out.println("❌ JwtAuthFilter error: " + e.getClass().getSimpleName() + " → " + e.getMessage());
        }

        filterChain.doFilter(request, response); // ← siempre continúa la cadena
    }
}


// Seguridad Para interceptar requests y validar tokens.