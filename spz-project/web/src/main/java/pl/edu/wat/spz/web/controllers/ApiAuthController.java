package pl.edu.wat.spz.web.controllers;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.spz.backend.service.AuthService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class ApiAuthController {

    @Autowired
    AuthService authService;

    @Autowired
    AuthenticationManager authenticationManagerBean;

    @RequestMapping(value = "/api/auth", method = RequestMethod.POST)
    public Object authorize(@RequestParam("username") String username, @RequestParam("password") String password,
                            HttpServletRequest request, HttpServletResponse response) throws IOException {
        Authentication authenticate = authenticationManagerBean.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return authService.authorize(request, response, authenticate);
    }

}
