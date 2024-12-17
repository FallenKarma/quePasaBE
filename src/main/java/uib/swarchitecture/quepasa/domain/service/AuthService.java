package uib.swarchitecture.quepasa.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uib.swarchitecture.quepasa.domain.model.Token;
import uib.swarchitecture.quepasa.domain.model.User;

@Service
public class AuthService {

    private UserService userService;
    private JwtService jwtService;

    @Autowired
    public AuthService(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    public Token register(User user) {
        user = userService.addUser(user);
        return jwtService.generateToken(user);
    }

    public Token login(User user) {
        System.out.println(user.toString());
        return null;
    }


}
