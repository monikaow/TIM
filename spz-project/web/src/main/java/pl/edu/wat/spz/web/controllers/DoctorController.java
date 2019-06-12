package pl.edu.wat.spz.web.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DoctorController {

    @RequestMapping(value = "/doctor/manage-visits", method = RequestMethod.GET)
    public String manageWorkTimePage() {
        return "/views/doctor/manage-visits";
    }

    @RequestMapping(value = "/doctor/details", method = RequestMethod.GET)
    public String detailsPage(ModelMap map) {
        Object doctor = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        map.addAttribute("doctor", doctor);

        return "/views/doctor/details";
    }


}
