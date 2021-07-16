package com.info.patient.controller;

import com.info.patient.enums.Role;
import com.info.patient.model.Observation;
import com.info.patient.model.Patient;
import com.info.patient.service.DoctorService;
import com.info.patient.service.ObservationService;
import com.info.patient.service.PatientService;
import com.info.patient.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "/patients/{patientId}")
public class PatientController {
    private static final Logger logger = LoggerFactory.getLogger(PatientController.class);

    @Autowired
    PatientService patientService;

    @Autowired
    UserService userService;

    @Autowired
    ObservationService observationService;

    @Autowired
    DoctorService doctorService;

    @GetMapping
    public String viewDetails(Model model, @PathVariable Long patientId) {
        Patient patient = patientService.findById(patientId);
        model.addAttribute("patientId", patientId);
        try {
            model.addAttribute("patient", patient);
            return "patient/view_patient";
        } catch (Exception e) {
            model.addAttribute("patient", patient);
            return "patient/patient_list";
        }
    }

    @GetMapping("/observations")
    public String observationList(Model model, @PathVariable Long patientId) throws IOException {
        Integer doctorId = 0;
        List<Observation> observationList = new ArrayList<>();
        model.addAttribute("patientId", patientId);
        try {
            Patient patient = patientService.findById(patientId);
            observationList = observationService.findAllByPatient(patient);
            doctorId = patient.getDoctor().getId();
            model.addAttribute("doctorId", doctorId);
            model.addAttribute("observationList", observationList);
            return "observation/list";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error during fetching data!");
            model.addAttribute("observationList", observationList);
            return "observation/list";
        }
    }

    @GetMapping(value = "/observations/{observationId}")
    public String viewDetails(Model model, @PathVariable Long patientId, @PathVariable Long observationId) {
        Observation observation = observationService.findById(observationId);
        model.addAttribute("patientId", patientId);
        try {
            model.addAttribute("observation", observation);
            return "observation/view";
        } catch (Exception e) {
            model.addAttribute("observation", observation);
            return "observation/list";
        }
    }

}
