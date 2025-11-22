package br.com.fiap.gs_rise.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontendController {

    @GetMapping({"/", "/dashboard", "/ui"})
    public String dashboard() {
        return "dashboard";
    }
}
