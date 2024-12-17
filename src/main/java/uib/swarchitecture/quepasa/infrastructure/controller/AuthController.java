package uib.swarchitecture.quepasa.infrastructure.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import uib.swarchitecture.quepasa.domain.exceptions.EmailAlreadyExistsException;
import uib.swarchitecture.quepasa.domain.exceptions.UsernameAlreadyExistsException;
import uib.swarchitecture.quepasa.domain.model.Token;
import uib.swarchitecture.quepasa.domain.model.User;
import uib.swarchitecture.quepasa.domain.service.AuthService;
import uib.swarchitecture.quepasa.infrastructure.controller.utils.ApiResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    AuthService authService;

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
    public ResponseEntity<ApiResponse<Token>> login(@RequestBody User user) {
        authService.login(user);
        return null;
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<Token>> refreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION) final String authentication) {
        return null;
    }
}
