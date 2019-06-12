package pl.edu.wat.spz.web.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import pl.edu.wat.spz.backend.repository.UserRepository;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static pl.edu.wat.spz.backend.service.AuthService.AUTHORIZATION_HEADER;
import static pl.edu.wat.spz.backend.service.AuthService.SECRET;
import static pl.edu.wat.spz.backend.service.AuthService.TOKEN_PREFIX;


public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private UserRepository userRepository;

    public JWTAuthorizationFilter(AuthenticationManager authManager, UserRepository userRepository) {
        super(authManager);
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(AUTHORIZATION_HEADER);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        if (authentication != null)
            SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION_HEADER);
        if (token != null) {
            String user = JWT.require(Algorithm.HMAC512(SECRET.getBytes())) //user - mail
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, "")) //odkodowanie
                    .getSubject(); //pobiera podmiot czyli mail

            if (user != null) {
                return new UsernamePasswordAuthenticationToken(
                        userRepository.findByEmail(user),
                        new ArrayList<>(), new ArrayList<>());
            }
            return null;
        }
        return null;
    }
}
