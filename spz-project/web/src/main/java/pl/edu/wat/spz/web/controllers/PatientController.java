package pl.edu.wat.spz.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.spz.backend.service.*;
import pl.edu.wat.spz.domain.entity.MedicalVisit;
import pl.edu.wat.spz.domain.entity.Patient;
import pl.edu.wat.spz.domain.entity.RecipeHistory;
import pl.edu.wat.spz.domain.entity.Specialization;

import java.util.List;

@Controller
public class PatientController {

    private SpecializationService specializationService;

    @Autowired
    public PatientController(SpecializationService specializationService) {
        this.specializationService = specializationService;
    }

    @RequestMapping(value = "/auth/patient/register", method = RequestMethod.GET)
    public String patientRegisterPage() {

        return "views/patient/register";
    }

    @RequestMapping("/patient/details")
    public String patientEditDetails(ModelMap map) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        map.addAttribute("patient", principal);
        return "views/patient/details";
    }

    @RequestMapping("/patient/manage-visits")
    public String manageVisitsPage() {
        return "views/patient/manage-visits";
    }

    @RequestMapping("/patient/recipe-history")
    public String recipeHistoryPage() {
        return "views/patient/recipe-history";
    }

    @RequestMapping("/patient/reservation-visit")
    public String patientReservationVisit(ModelMap map) {
        map.addAttribute("specializations", specializationService.findAll());
        return "views/patient/reservation-visit";
    }

}
