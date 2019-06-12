package pl.edu.wat.spz.web.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice(annotations = RestController.class)
public class ApiErrorController {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handleAjaxException(HttpServletRequest request) {
        return request.getAttribute("javax.servlet.error.status_code");
    }

}
