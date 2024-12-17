package uib.swarchitecture.quepasa.infrastructure.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import uib.swarchitecture.quepasa.domain.exceptions.EmailAlreadyExistsException;
import uib.swarchitecture.quepasa.domain.exceptions.UsernameAlreadyExistsException;
import uib.swarchitecture.quepasa.domain.model.User;
import uib.swarchitecture.quepasa.domain.service.UserService;
import uib.swarchitecture.quepasa.infrastructure.controller.utils.ApiResponse;

@RestController
@RequestMapping("/api/users")
public class UserController {


    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Endpoint para crear un nuevo usuario
    @PostMapping
    public ResponseEntity<ApiResponse<User>> createUser(@RequestBody User user) {
        try {
            // Llamar al servicio para crear el usuario
            User createdUser = userService.addUser(user);

            // Retornar el usuario creado con un código de estado 201 (Created)
            return new ResponseEntity<>(new ApiResponse<>(createdUser), HttpStatus.CREATED);
        } catch (EmailAlreadyExistsException e) {
            // Manejar cuando el email ya está en uso
            return new ResponseEntity<>(new ApiResponse<>(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (UsernameAlreadyExistsException e) {
            // Manejar cuando el nombre de usuario ya está en uso
            return new ResponseEntity<>(new ApiResponse<>(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Manejar otras excepciones inesperadas
            return new ResponseEntity<>(new ApiResponse<>(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
