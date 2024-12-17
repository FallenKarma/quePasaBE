package uib.swarchitecture.quepasa.infrastructure.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uib.swarchitecture.quepasa.domain.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {


    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
}
