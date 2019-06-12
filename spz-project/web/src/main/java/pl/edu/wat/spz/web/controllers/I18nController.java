package pl.edu.wat.spz.web.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class I18nController {

    @GetMapping("/lang")
    public Object changeLang() {
        return null;
    }
}
