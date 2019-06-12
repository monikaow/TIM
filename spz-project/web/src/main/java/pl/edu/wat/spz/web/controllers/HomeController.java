package pl.edu.wat.spz.web.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.edu.wat.spz.domain.entity.Doctor;
import pl.edu.wat.spz.domain.entity.Patient;

@Controller
public class HomeController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String homePage(ModelMap model) throws Exception {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (principal instanceof Patient) {
            return "/views/patient/home";

        }
        else if (principal instanceof Doctor) {
            return "/views/doctor/home";
        }

        return "/views/public/home";
    }
}
