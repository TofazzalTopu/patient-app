package com.info.patient.controller;

import com.info.patient.model.Doctor;
import com.info.patient.model.Patient;
import com.info.patient.service.DoctorService;
import com.info.patient.service.PatientService;
import com.info.patient.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
@RequestMapping(value = "/")
public class LoginController {

    @Autowired
    UserService userService;
    @Autowired
    DoctorService doctorService;

    @Autowired
    PatientService patientService;

    @GetMapping("/")
    String index(Principal principal) {
        if(principal!= null){
            Doctor doctor = doctorService.findDoctorIdByUserName(principal.getName());
            if(doctor == null){
                Patient patient = patientService.findAllByUserName(principal.getName());
                return "redirect:/patients/"+patient.getId();
            }
            return "redirect:/doctors/"+doctor.getId()+"/patients";
        }
        return "redirect:/login";
    }

    @GetMapping(value = {"/login"})
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        boolean isLoggedIn = userService.isLoggedIn();
        if (isLoggedIn) {
            model.addAttribute("isLoggedIn", isLoggedIn);
            return "patient/patient_list";
        }
        model.addAttribute("isLoggedIn", isLoggedIn);
        return "login/login";
    }
}
