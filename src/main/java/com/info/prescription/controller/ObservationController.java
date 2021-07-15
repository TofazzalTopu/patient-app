package com.info.prescription.controller;


import com.info.prescription.model.Observation;
import com.info.prescription.service.ObservationService;
import com.info.prescription.service.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/observations")
public class ObservationController {

    private static final Logger logger = LoggerFactory.getLogger(ObservationController.class);

    public final ObservationService observationService;
    public final PatientService patientService;

    public ObservationController(ObservationService observationService, PatientService patientService) {
        this.observationService = observationService;
        this.patientService = patientService;
    }

    @GetMapping("/create/{patientId}")
    public String createForm(Model model, @PathVariable Long patientId) {

        Observation observation = new Observation();
        model.addAttribute("observation", observation);
        model.addAttribute("patientId", patientId);
        logger.info("Create observation Form Loaded.");
        return "observation/create";
    }

    @PostMapping("/create")
    public String save(Model model, @ModelAttribute("observation") Observation observation
            , BindingResult result) throws IOException {
        try {
            observation.setPatient(patientService.findById(observation.getPatientId()));
            observation = observationService.save(observation);

            List<Observation> observationList = observationService.findAllByPatient(observation.getPatient());

            logger.info("Fetching observationList.");
            model.addAttribute("observation", observation);
            model.addAttribute("observationList", observationList);
            model.addAttribute("success", "Observation Created successfully.");
            return "observation/view";
        } catch (Exception e) {
            model.addAttribute("error", "Please enter all mandatory fields!");
            model.addAttribute("observation", observation);
            return "observation/create";
        }
    }

    @GetMapping("/list/{patientId}")
    public String list(Model model, @PathVariable Long patientId) throws IOException {

        List<Observation> observationList = new ArrayList<>();
        model.addAttribute("patientId", patientId);
        try {
            observationList = observationService.findAll();
            if (observationList.isEmpty()) {
                model.addAttribute("error", "No data found for this selected date range!");
            } else {
                model.addAttribute("observationList", observationList);
            }
            return "observation/list";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error during fetching data!");
            return "observation/list";
        }
    }

    @GetMapping(value = "/viewDetails/{id}")
    public String viewDetails(Model model, @PathVariable Long id) {
        Observation observation = observationService.findById(id);
        try {
            model.addAttribute("observation", observation);
            return "observation/view";
        } catch (Exception e) {
            model.addAttribute("observation", observation);
            return "observation/list";
        }
    }

    @GetMapping(value = "/edit/{id}")
    public String edit(Model model, @PathVariable Long id) {
        Observation observation = observationService.findById(id);
        List<Observation> observationList = observationService.findAllByPatient(observation.getPatient());
        try {
            model.addAttribute("observationList", observationList);
            model.addAttribute("observation", observation);
            return "observation/edit";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("observationList", observationList);
            model.addAttribute("error", "ERROR - Observation not found!");
            return "observation/edit";
        }
    }

    // @Comment: This method will create observation and will render observation list form after create observation.
    @PostMapping("/update/{id}")
    public String update(Model model, @PathVariable Long id, @ModelAttribute("observation") Observation observation) throws IOException {
        try {
            observation.setPatient(patientService.findById(observation.getPatientId()));
            Observation _observation = observationService.update(observation);
            if (_observation == null) {
                logger.info("Observation not updated!");
                model.addAttribute("error", "Observation not updated!");
                return "observation/edit";
            }

            logger.info("Observation updated successfully.");
            List<Observation> observationList = observationService.findAllByPatient(observation.getPatient());

            logger.info("Fetching observationList.");
            model.addAttribute("observationList", observationList);
            model.addAttribute("success", "Observation updated successfully.");
            return "observation/view";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "ERROR - Observation not saved!");
            return "observation/edit";
        }
    }

    @GetMapping(value = "/delete/{id}")
    public String delete(Model model, @PathVariable Long id) {
        Observation observation = observationService.findById(id);
        List<Observation> observationList = observationService.findAllByPatient(observation.getPatient());
        try {
            boolean isDelete = observationService.delete(observation);

            if (isDelete) {
                model.addAttribute("observationList", observationList);
                model.addAttribute("success", "Observation deleted successfully!");
                return "observation/list";
            } else {
                model.addAttribute("observationList", observationList);
                model.addAttribute("error", "ERROR - Observation not deleted!");
                return "observation/list";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("observationList", observationList);
            model.addAttribute("error", "ERROR - Observation not deleted!");
            return "observation/list";
        }
    }
}
