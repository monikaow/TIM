package pl.edu.wat.spz.backend.service;

import com.auth0.jwt.JWT;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import pl.edu.wat.spz.backend.dto.UserDetails;
import pl.edu.wat.spz.domain.entity.Patient;
import pl.edu.wat.spz.domain.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@Service
public class AuthService {

    public static final String SECRET = "jc#$#343rnfskdjuhf/*/+jsd4####grh4&^(935fsd434g";
    public static final long EXPIRATION_TIME = 864_000_000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String EXPOSE_HEADERS = "Access-Control-Expose-Headers";
    public static final String AUTHORIZATION_HEADER = "Authorization";

    public Object authorize(HttpServletRequest request,
                            HttpServletResponse response,
                            Authentication auth) throws IOException {
        Object principal = auth.getPrincipal();
        if (principal instanceof Patient) {
            Patient user = (Patient) auth.getPrincipal();
            String token = JWT.create()
                    .withSubject(user.getEmail())
                    .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .sign(HMAC512(SECRET.getBytes()));
            response.addHeader(EXPOSE_HEADERS, AUTHORIZATION_HEADER);
            response.addHeader(AUTHORIZATION_HEADER, TOKEN_PREFIX + token);

            return new UserDetails(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail());
        }

        return null;
    }
}
