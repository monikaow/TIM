package pl.edu.wat.spz.web.controllers;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.edu.wat.spz.backend.service.AuthService;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    AuthenticationManager authenticationManagerBean;

    private void changeSessionAttributeToRequestAttribute(HttpServletRequest request, String... names) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            for (String name : names) {
                Object sessionAttribute = session.getAttribute(name);
                if (sessionAttribute != null) {
                    session.removeAttribute(name);
                    request.setAttribute(name, sessionAttribute);
                }
            }
        }
    }

    @RequestMapping(value = "/auth/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request) {
        changeSessionAttributeToRequestAttribute(request, "LOGOUT_MESSAGE", "AUTH_EXCEPTION");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }

        return "views/auth/login";
    }

}
