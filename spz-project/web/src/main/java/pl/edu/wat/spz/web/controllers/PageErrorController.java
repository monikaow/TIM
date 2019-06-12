package pl.edu.wat.spz.web.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice(annotations = Controller.class)
public class PageErrorController {

    @ExceptionHandler(Exception.class)
    public String error(HttpServletRequest request) {
        return "redirect:/";
    }
}
