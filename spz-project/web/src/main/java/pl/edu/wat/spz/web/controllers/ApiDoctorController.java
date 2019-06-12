package pl.edu.wat.spz.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.spz.backend.dto.RecipeHistoryDto;
import pl.edu.wat.spz.backend.service.*;
import pl.edu.wat.spz.commons.utils.Constans;
import pl.edu.wat.spz.domain.entity.*;

import java.util.Date;
import java.util.List;

@RestController
public class ApiDoctorController {

    private DoctorTimetableService timetableService;

    private DoctorService doctorService;

    private DoctorSpecializationService doctorSpecializationService;

    private MedicalVisitService visitService;

    private RecipeHistoryService recipeHistoryService;

    private UserService userService;

    @Autowired
    public ApiDoctorController(DoctorTimetableService timetableService, DoctorService doctorService, DoctorSpecializationService doctorSpecializationService, MedicalVisitService visitService, RecipeHistoryService recipeHistoryService, UserService userService) {
        this.timetableService = timetableService;
        this.doctorService = doctorService;
        this.doctorSpecializationService = doctorSpecializationService;
        this.visitService = visitService;
        this.recipeHistoryService = recipeHistoryService;
        this.userService = userService;
    }

    @RequestMapping(value = "/api/doctor/timetable/fetch", method = RequestMethod.GET)
    public List<List<DoctorTimetable>> findByDoctorAndDate(@RequestParam("doctorId") Long doctorId, @RequestParam("dates") @DateTimeFormat(pattern = Constans.DATE_FORMAT) List<Date> dates) {
        return timetableService.groupByDoctorAndDates(doctorId, dates);
    }

    @RequestMapping(value = "/api/doctor/visits/fetch", method = RequestMethod.GET)
    public List<MedicalVisit> findByDoctorVisits(@RequestParam("doctorId") Long doctorId, @RequestParam("date") @DateTimeFormat(pattern = Constans.DATE_FORMAT) Date date) {
        return visitService.findAllByDoctorId(doctorId, date);
    }

    @RequestMapping(value = "/api/doctor/timetable/first", method = RequestMethod.GET)
    public DoctorTimetable findFirstEnableVisit(@RequestParam("doctorId") Long doctorId) {
        return timetableService.findFirstEnableVisit(doctorId);
    }

    @RequestMapping(value = "/api/doctor/specialization", method = RequestMethod.GET)
    public List<DoctorSpecialization> findAllBySpecializationId(@RequestParam("specializationId") Long specializationId) {
        return doctorSpecializationService.findAllBySpecializationId(specializationId);
    }

    @RequestMapping(value = "/api/doctor/register/recipe-history", method = RequestMethod.POST)
    public RecipeHistory registerRecipeHistory(@RequestBody RecipeHistoryDto recipeHistoryDto) {
        return recipeHistoryService.registerRecipeHistory(recipeHistoryDto);
    }


    @RequestMapping(value = "/api/doctor/details", method = RequestMethod.POST)
    public Doctor updatePatient(@RequestBody Doctor doctor) {
        Doctor update = doctorService.update(doctor);
        userService.setAuth(update);
        return update;
    }

}
