package pl.edu.wat.spz.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.spz.backend.service.*;
import pl.edu.wat.spz.domain.entity.MedicalVisit;
import pl.edu.wat.spz.domain.entity.Patient;
import pl.edu.wat.spz.domain.entity.RecipeHistory;
import pl.edu.wat.spz.domain.entity.Specialization;

import java.util.List;

@RestController
public class ApiPatientController {

    private SpecializationService specializationService;

    private MedicalVisitService medicalVisitService;

    private RecipeHistoryService recipeHistoryService;

    private PatientService patientService;

    private UserService userService;

    @Autowired
    public ApiPatientController(SpecializationService specializationService, MedicalVisitService medicalVisitService, RecipeHistoryService recipeHistoryService, PatientService patientService, UserService userService) {
        this.specializationService = specializationService;
        this.medicalVisitService = medicalVisitService;
        this.recipeHistoryService = recipeHistoryService;
        this.patientService = patientService;
        this.userService = userService;
    }

    @RequestMapping(value = "/api/patient/visits/fetch", method = RequestMethod.GET)
    public Page<MedicalVisit> fetchMedicalVisits(@RequestParam("patientId") Long patientId,
                                                 Pageable pageable) {
        return medicalVisitService.findByPatientPaginated(patientId, pageable);
    }
    
    @RequestMapping(value = "/api/patient/specializations/fetch", method = RequestMethod.GET)
    public List<Specialization> fetchSpecializations() {
        return specializationService.findAll();
    }

    @RequestMapping(value = "/api/patient/recipe-history/fetch", method = RequestMethod.GET)
    public Page<RecipeHistory> fetchRecipeHistories(@RequestParam("patientId") Long patientId,
                                                    Pageable pageable) {
        return recipeHistoryService.findAllByPatientId(patientId, pageable);
    }

    @RequestMapping(value = "/api/patient/visit/cancel", method = RequestMethod.POST)
    public MedicalVisit cancelVisit(@RequestParam("medicalVisitId") Long medicalVisitId) {
        return medicalVisitService.cancelVisit(medicalVisitId);
    }
    
    @RequestMapping(value = "/api/patient/visit/update", method = RequestMethod.POST)
    public MedicalVisit updateVisit(@RequestParam("medicalVisitId") Long medicalVisitId, @RequestParam("timetableId") Long timetableId) {
        return medicalVisitService.updateVisit(medicalVisitId, timetableId);
    }
    
    @RequestMapping(value = "/api/patient/details", method = RequestMethod.POST)
    public Patient updatePatient(@RequestBody Patient patient) {
        Patient update = patientService.update(patient);
        userService.setAuth(update);
        return update;
    }
}
