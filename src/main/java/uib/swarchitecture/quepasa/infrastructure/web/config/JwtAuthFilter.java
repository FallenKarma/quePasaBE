package uib.swarchitecture.quepasa.infrastructure.web.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uib.swarchitecture.quepasa.domain.models.User;
import uib.swarchitecture.quepasa.domain.services.UserService;
import uib.swarchitecture.quepasa.infrastructure.web.services.JwtService;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException { // Cadena de responsabilidades
        // Está en los dominios que no autenticamos
        if (request.getServletPath().contains("/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        // No tiene un header de authorization
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // El token no referencia ningún usuario
        String token = authHeader.substring(7);
        long id = jwtService.extractUserId(token);
        if (id == 0) {
            filterChain.doFilter(request, response);
            return;
        }

        // Hay un usuario autentificado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            filterChain.doFilter(request, response);
            return;
        }


        User user;
        try {
            user = userService.getUserById(id);
        } catch (Exception e) {
            // No se encuentra el usuario
            filterChain.doFilter(request, response);
            return;
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());

        // El token no es válido
        if (!jwtService.isTokenValid(token, user)) {
            return;
        }

        // Autenticar al usuario
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
