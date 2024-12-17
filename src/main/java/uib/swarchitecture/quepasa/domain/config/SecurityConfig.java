package uib.swarchitecture.quepasa.domain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.disable()) // temporal
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(BCryptPasswordEncoder passwordEncoder) {
        // Aquí se usa BCrypt para cifrar la contraseña
        UserDetails user = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("password")) // Cifrado de la contraseña con BCrypt
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    // Crear un bean para BCryptPasswordEncoder
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
