package uib.swarchitecture.quepasa.infrastructure.web.adapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uib.swarchitecture.quepasa.domain.ports.AuthPort;
import uib.swarchitecture.quepasa.infrastructure.web.services.JwtService;

@Component
public class AuthAdapter implements AuthPort {

    private final JwtService jwtService;

    @Autowired
    public AuthAdapter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public long getIdFromAuthentication(final String authentication) {
        // Eliminar Bearer
        String token = authentication.substring(7);
        return jwtService.extractUserId(token);
    }
}
