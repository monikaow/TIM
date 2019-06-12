package pl.edu.wat.spz.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.spz.backend.dto.MedicalVisitDto;
import pl.edu.wat.spz.backend.dto.UserDto;
import pl.edu.wat.spz.backend.exceptions.UserAlreadyExistException;
import pl.edu.wat.spz.backend.service.MedicalVisitService;
import pl.edu.wat.spz.backend.service.UserService;
import pl.edu.wat.spz.domain.entity.User;

import javax.servlet.http.HttpServletRequest;

@RestController
public class RegistrationController {

    private UserService userService;

    private MedicalVisitService visitService;

    @Autowired
    public RegistrationController(UserService userService, MedicalVisitService visitService) {
        this.userService = userService;
        this.visitService = visitService;
    }

    @RequestMapping(value = "/auth/patient/register", method = RequestMethod.POST)
    public Object patientRegister(@RequestBody UserDto user) {
        final User registered = userService.registerNewPatient(user);
        userService.setAuth(registered);
        return registered.getId();
    }

    @RequestMapping(value = "/api/patient/register/visit", method = RequestMethod.POST)
    public Object visitRegister(@RequestBody MedicalVisitDto visit) {
        return visitService.registerMedicalVisit(visit);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<String> handleStorageFileNotFound(UserAlreadyExistException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
