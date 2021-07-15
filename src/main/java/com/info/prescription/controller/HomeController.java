package com.info.prescription.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Controller
@RequestMapping(value = "/")
public class HomeController {

    @GetMapping("/")
    String index(Principal principal) {
        return principal != null ? "redirect:/patient/list" : "redirect:/login";
    }

}
