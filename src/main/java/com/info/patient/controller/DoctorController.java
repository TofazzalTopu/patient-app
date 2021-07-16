package com.info.patient.controller;


import com.info.patient.enums.Role;
import com.info.patient.model.Doctor;
import com.info.patient.model.Observation;
import com.info.patient.model.Patient;
import com.info.patient.model.User;
import com.info.patient.service.DoctorService;
import com.info.patient.service.ObservationService;
import com.info.patient.service.PatientService;
import com.info.patient.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/doctors/{doctorId}/patients")
public class DoctorController {

    private static final Logger logger = LoggerFactory.getLogger(DoctorController.class);

    @Autowired
    ObservationService observationService;

    @Autowired
    PatientService patientService;

    @Autowired
    UserService userService;

    @Autowired
    DoctorService doctorService;

    @GetMapping
    public String patientList(Model model, @PathVariable Integer doctorId) throws IOException {
        List<Patient> patientList = new ArrayList<>();
        model.addAttribute("doctorId", doctorId);
        try {
            Doctor doctor = doctorService.findById(doctorId);
            patientList = patientService.findAllByDoctor(doctor);
            model.addAttribute("patientList", patientList);
            return "patient/patient_list";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error during fetching data!");
            model.addAttribute("patientList", patientList);
            return "patient/patient_list";
        }
    }

    @PostMapping
    public String create(Model model, @ModelAttribute("patient") Patient patient, @PathVariable Integer doctorId) {
        try {
            Doctor doctor = doctorService.findById(doctorId);
            User user = userService.findByUsername(patient.getUser().getUsername());
            if (user != null) {
                model.addAttribute("error", "Patient already exist with the username " + patient.getUser().getUsername());
                return "patient/create_patient";
            }
            patient.setDoctor(doctor);
            Patient _patient = patientService.save(patient);
            if (_patient == null) {
                model.addAttribute("error", "Patient not saved!");
                return "patient/create_patient";
            }
            model.addAttribute("patient", _patient);
            model.addAttribute("success", "Patient Created successfully.");
            return "patient/view_patient";
        } catch (Exception e) {
            model.addAttribute("error", "Please enter all mendatory fields!");
            return "patient/create_patient";
        }
    }

    @PutMapping
    public String update(Model model, @ModelAttribute("patient") Patient patient, @PathVariable Integer doctorId) {
        try {
            Doctor doctor = doctorService.findById(doctorId);
            patient.setDoctor(doctor);
            Patient _patient = patientService.save(patient);
            if (_patient == null) {
                model.addAttribute("error", "Patient not updated!");
                return "patient/create_patient";
            }

            model.addAttribute("patient", _patient);
            model.addAttribute("success", "Patient updated successfully.");
            return "patient/view_patient";
        } catch (Exception e) {
            model.addAttribute("error", "Please enter all mendatory fields!");
            return "patient/create_patient";
        }
    }

    @GetMapping(value = "/{patientId}")
    public String edit(Model model, @PathVariable Integer doctorId, @PathVariable Long patientId) {
        try {
            Doctor doctor = doctorService.findById(doctorId);
            Patient patient = patientService.findById(patientId);
            model.addAttribute("patient", patient);
            return "patient/edit_patient";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "ERROR - Patient not found!");
            return "patient/edit_patient";
        }
    }

    @PostMapping(value = "/{patientId}")
    public String update(Model model, @ModelAttribute("patient") Patient patient, @PathVariable Integer doctorId, @PathVariable Long patientId) {
        try {
            Doctor doctor = doctorService.findById(doctorId);
            patient.setDoctor(doctor);
            patient.setUpdateDate(new Date());
            Patient _patient = patientService.save(patient);
            if (_patient == null) {
                logger.info("Patient not updated!");
                model.addAttribute("error", "Patient not updated!");
                return "patient/edit_patient";
            }

            logger.info("Patient updated successfully.");

            List<Patient> patientList = patientService.findAll();

            logger.info("Fetching patientList.");
            model.addAttribute("userList", userService.findAllByRole(Role.PATIENT.toString()));
            model.addAttribute("patientList", patientList);
            model.addAttribute("success", "Patient updated successfully.");
            return "patient/view_patient";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("userList", userService.findAllByRole(Role.PATIENT.toString()));
            model.addAttribute("error", "ERROR - Patient not saved!");
            return "patient/edit_patient";
        }
    }

    @GetMapping(value = "/{patientId}/delete")
    public String deletePatient(Model model, @PathVariable Long doctorId, @PathVariable Long patientId) {
        List<Patient> patientList = new ArrayList<>();
        model.addAttribute("doctorId", doctorId);
        try {
            Patient patient = patientService.findById(patientId);
            boolean isDelete = patientService.deletePatient(patient);

            if (isDelete) {
                patientList = patientService.findAll();
                model.addAttribute("patientList", patientList);
                model.addAttribute("success", "Patient deleted successfully!");
                return "patient/patient_list";
            } else {
                model.addAttribute("patientList", patientList);
                model.addAttribute("error", "ERROR - Patient not deleted!");
                return "patient/patient_list";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("patientList", patientList);
            model.addAttribute("error", "ERROR - Patient not deleted!");
            return "patient/patient_list";
        }
    }

    @GetMapping(value = {"/form"})
    public String createPatientForm(Model model, @PathVariable Integer doctorId) {
        Patient patient = new Patient();
        model.addAttribute("patient", patient);
        model.addAttribute("doctorId", doctorId);
        logger.info("Create patient Form Loaded.");
        return "patient/create_patient";
    }

    @PostMapping("/{patientId}/observations")
    public String save(Model model, @ModelAttribute("observation") Observation observation
            , BindingResult result) throws IOException {
        try {
            observation.setPatient(patientService.findById(observation.getPatientId()));
            observation = observationService.save(observation);

            List<Observation> observationList = observationService.findAllByPatient(observation.getPatient());

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

    @GetMapping("/{patientId}/observations")
    public String createForm(Model model, @PathVariable Long doctorId, @PathVariable Long patientId) {
        Observation observation = new Observation();
        model.addAttribute("observation", observation);
        model.addAttribute("patientId", patientId);
        return "observation/create";
    }

    @PostMapping("/{patientId}/observations/{observationId}")
    public String update(Model model, @ModelAttribute("observation") Observation observation, @PathVariable Long observationId) throws IOException {
        model.addAttribute("observation", observation);
        try {
            if (observationId == null) {
                logger.info("Observation not updated. ID not found!");
                model.addAttribute("error", "Observation not updated. ID not found!");
                return "observation/edit";
            }
            observation.setPatient(patientService.findById(observation.getPatientId()));
            Observation _observation = observationService.update(observation);
            if (_observation == null) {
                logger.info("Observation not updated!");
                model.addAttribute("error", "Observation not updated!");
                return "observation/edit";
            }

            logger.info("Observation updated successfully.");
            model.addAttribute("success", "Observation updated successfully.");
            return "observation/view";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "ERROR - Observation not saved!");
            return "observation/edit";
        }
    }

    @GetMapping("/{patientId}/observations/{observationId}")
    public String edit(Model model, @ModelAttribute("observation") Observation observation, @PathVariable Long observationId) throws IOException {
        try {
            if (observationId == null) {
                logger.info("Observation not found!");
                model.addAttribute("error", "Observation not found!");
                return "observation/view";
            }
            observation = observationService.findById(observationId);
            if (observation == null) {
                logger.info("Observation not found!");
                model.addAttribute("error", "Observation not found!");
                return "observation/view";
            }
            model.addAttribute("observation", observation);
            return "observation/edit";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "ERROR - Observation not found!");
            return "observation/view";
        }
    }

    @GetMapping(value = "/{patientId}/observations/{observationId}/delete")
    public String deleteObservation(Model model, @PathVariable Long observationId, @PathVariable Long doctorId) {
        model.addAttribute("doctorId", doctorId);
        List<Observation> observationList = new ArrayList<>();
        Observation observation = observationService.findById(observationId);
        if (observation != null) {
            observationList = observationService.findAllByPatient(observation.getPatient());
        }
        try {
            boolean isDelete = observationService.delete(observation);
            observationList = observationService.findAllByPatient(observation.getPatient());

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
