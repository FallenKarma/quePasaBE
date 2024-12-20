package uib.swarchitecture.quepasa.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import uib.swarchitecture.quepasa.domain.models.Token;
import uib.swarchitecture.quepasa.domain.models.User;

@Service
public class AuthServiceImpl implements AuthService{

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthServiceImpl(UserService userService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public Token register(User user) {
        user = userService.addUser(user);
        return jwtService.generateToken(user);
    }

    public Token authenticate(User user) {
        // Verificar credenciales
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword()
                )
        );

        // Devolver token del usuario
        user = userService.getUserByUsername(user.getUsername());
        return jwtService.generateToken(user);
    }

    public Token refreshToken(String authentication) {
        // Comprobar que es un header valido
        if (authentication == null || !authentication.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid auth header");
        }

        // Obtener el id del usuario
        String refreshToken = authentication.substring(7);
        long id = jwtService.extractUserId(refreshToken);
        if (id == 0) {
            throw new IllegalArgumentException("Invalid refresh token");
        }

        // Obtener el usuario
        User user = userService.getUserById(id);

        // Verificar que el token es correcto
        if (!jwtService.isTokenValid(refreshToken, user)) {
            throw new AuthenticationException("Spoiled token") {
            };
        }

        // Devolver token del usuario
        String accessToken = jwtService.generateAccessToken(user);
        return Token.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
