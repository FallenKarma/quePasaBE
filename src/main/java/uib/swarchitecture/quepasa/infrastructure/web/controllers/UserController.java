package uib.swarchitecture.quepasa.infrastructure.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uib.swarchitecture.quepasa.domain.models.User;
import uib.swarchitecture.quepasa.domain.services.UserService;
import uib.swarchitecture.quepasa.infrastructure.web.controllers.utils.ApiResponse;
import uib.swarchitecture.quepasa.infrastructure.web.models.UserDTO;

@RestController
@RequestMapping("/api/users")
public class UserController {


    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/exist")
    public ResponseEntity<ApiResponse<UserDTO>> checkUserExist(@RequestParam String username) {
        try {
            // Llamar al servicio para obtener un usuario
            User user = userService.getUserByUsername(username);
            UserDTO userDTO = new UserDTO(user.getId(), user.getUsername());

            // Retornar el usuario si existe
            return new ResponseEntity<>(new ApiResponse<>(userDTO), HttpStatus.OK);
        } catch (UsernameNotFoundException e) {
            // Manejar cuando el usuario no existe
            return new ResponseEntity<>(new ApiResponse<>(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Manejar otras excepciones inesperadas
            return new ResponseEntity<>(new ApiResponse<>(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
