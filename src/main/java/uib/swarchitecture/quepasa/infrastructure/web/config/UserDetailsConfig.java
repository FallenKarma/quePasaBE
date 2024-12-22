package uib.swarchitecture.quepasa.infrastructure.web.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import uib.swarchitecture.quepasa.domain.models.User;
import uib.swarchitecture.quepasa.domain.services.UserService;

@Configuration
@RequiredArgsConstructor
public class UserDetailsConfig {

    private final UserService userService;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            final User user = userService.getUserByUsername(username);
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .build();
        };
    }
}
