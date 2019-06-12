package pl.edu.wat.spz.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.wat.spz.backend.repository.UserRepository;
import pl.edu.wat.spz.domain.entity.User;

public class AuthProvider extends DaoAuthenticationProvider {

    private UserRepository userBaseRepository;

    @Autowired
    public AuthProvider(UserRepository userBaseRepository) {
        this.userBaseRepository = userBaseRepository;
    }

    @Override
    @Transactional
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        final User user = userBaseRepository.findByEmail(auth.getName());
        if (user == null) {
            throw new BadCredentialsException(auth.getName());
        }

        final Authentication result = super.authenticate(auth);
        return new UsernamePasswordAuthenticationToken(user, result.getCredentials(), result.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
