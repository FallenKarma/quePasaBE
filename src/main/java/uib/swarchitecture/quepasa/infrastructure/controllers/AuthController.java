package uib.swarchitecture.quepasa.infrastructure.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import uib.swarchitecture.quepasa.domain.exceptions.EmailAlreadyExistsException;
import uib.swarchitecture.quepasa.domain.exceptions.UsernameAlreadyExistsException;
import uib.swarchitecture.quepasa.domain.models.Token;
import uib.swarchitecture.quepasa.domain.models.User;
import uib.swarchitecture.quepasa.domain.services.AuthService;
import uib.swarchitecture.quepasa.infrastructure.controllers.utils.ApiResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Token>> register(@RequestBody User user) {
        try {
            // Llamar al servicio para registrar un usuario
            Token createdToken = authService.register(user);

            // Retornar el token del creado con un código 201 (Created)
            return new ResponseEntity<>(new ApiResponse<>(createdToken), HttpStatus.CREATED);
        } catch (EmailAlreadyExistsException | UsernameAlreadyExistsException e) {
            // Manejar cuando el email o username ya está en uso
            return new ResponseEntity<>(new ApiResponse<>(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch(Exception e) {
            // Manejar otras excepciones inesperadas
            return new ResponseEntity<>(new ApiResponse<>(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Token>> authenticate(@RequestBody User user) {
        try {
            // Llamar al servicio de autenticación
            Token createdToken = authService.authenticate(user);

            // Retornar el token con código 200 (OK)
            return new ResponseEntity<>(new ApiResponse<>(createdToken), HttpStatus.OK);
        } catch (BadCredentialsException e) {
            // Manejar cuando las credenciales no son correctas
            return new ResponseEntity<>(new ApiResponse<>(e.getMessage()), HttpStatus.UNAUTHORIZED);
        } catch(Exception e) {
            // Manejar otras excepciones
            return new ResponseEntity<>(new ApiResponse<>(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<Token>> refreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION) final String authentication) {
        try {
            // Llamar al servicio de refresco de token
            Token createdToken = authService.refreshToken(authentication);

            // Retornar el token renovado con código 200 (OK)
            return new ResponseEntity<>(new ApiResponse<>(createdToken), HttpStatus.OK);
        } catch (AuthenticationException e) {
            // Manejar cuando el usuario no existe o ha expirado el token
            return new ResponseEntity<>(new ApiResponse<>(e.getMessage()), HttpStatus.UNAUTHORIZED);
        } catch (IllegalArgumentException e) {
            // Manejar cuando el token no es válido
            return new ResponseEntity<>(new ApiResponse<>(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch(Exception e) {
            // Manejar otras excepciones
            return new ResponseEntity<>(new ApiResponse<>(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
