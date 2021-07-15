package com.info.prescription.controller;

import com.info.prescription.enums.UserType;
import com.info.prescription.model.Patient;
import com.info.prescription.service.PatientService;
import com.info.prescription.service.UserService;
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
@RequestMapping(value = "/patient")
public class PatientController {
    private static final Logger logger = LoggerFactory.getLogger(PatientController.class);

    @Autowired
    PatientService patientService;

    @Autowired
    UserService userService;

    // @Comment: This method will render form to create patient.
    @GetMapping(value = {"/createForm"})
    public String createpatientForm(Model model) {
        Patient patient = new Patient();
        model.addAttribute("patient", patient);
        model.addAttribute("userList", userService.findAllByUserType(UserType.PATIENT.toString()));
        logger.info("Create patient Form Loaded.");
        return "patient/create_patient";
    }

    @ModelAttribute("dateFormat")
    public String dateFormat() {
        return "yyyy-MM-dd";
    }

    @InitBinder
    private void dateBinder(WebDataBinder binder) {
        //The date format to parse or output your dates
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormat());
        //Create a new CustomDateEditor
        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        //Register it as custom editor for the Date type
        binder.registerCustomEditor(Date.class, editor);
    }

    // @Comment: This method will create patient and will render patient list form after create patient.
    @PostMapping("/create")
    public String create(Model model, @ModelAttribute("patient") Patient patient
            , BindingResult result) throws IOException {
        try {
            /*List<Patient> patientListByPatientId = patientService.findAllByPatientId(patient.getPatientId());
            if (!patientListByPatientId.isEmpty()) {
                logger.info("Patient ID already exist!");
                model.addAttribute("error", "Patient ID already exist!");
                return "patient/create_patient";
            }*/

            List<Patient> patients = patientService.findAllByUser(patient.getUser());
            if (!patients.isEmpty()) {
                logger.info("Patient already exist!");
                model.addAttribute("error", "Patient already exist!");
                return "patient/create_patient";
            }

            Patient _patient = patientService.save(patient);
            if (_patient == null) {
                logger.info("Patient not saved!");
                model.addAttribute("error", "Patient not saved!");
                return "patient/create_patient";
            }

            logger.info("Patient Created successfully.");

            List<Patient> patientList = patientService.findAll();

            logger.info("Fetching patientList.");
            model.addAttribute("patient", _patient);
            model.addAttribute("patientList", patientList);
            model.addAttribute("success", "Patient Created successfully.");
            return "patient/view_patient";
        } catch (Exception e) {
            model.addAttribute("error", "Please enter all mendatory fields!");
            return "patient/create_patient";
        }
    }

    // @Comment: This method will render patient list based on date range.
    @GetMapping("/list")
    public String patientList(Model model, @RequestParam(required = false, defaultValue = "") String range) throws IOException {

        List<Patient> patientList = new ArrayList<>();
        try {
            patientList = patientService.findAll();

            if (patientList.isEmpty()) {
                model.addAttribute("error", "No data found for this selected date range!");
            } else {
                model.addAttribute("patientList", patientList);
            }
            return "patient/patient_list";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error during fetching data!");
            return "patient/patient_list";
        }
    }

    @GetMapping(value = "/viewDetails/{id}")
    public String viewDetails(Model model, @PathVariable Long id) {
        Patient patient = patientService.findById(id);
        try {
            model.addAttribute("userList", userService.findAllByUserType(UserType.PATIENT.toString()));
            model.addAttribute("patient", patient);
            return "patient/view_patient";
        } catch (Exception e) {
            model.addAttribute("userList", userService.findAllByUserType(UserType.PATIENT.toString()));
            model.addAttribute("patient", patient);
            return "patient/patient_list";
        }
    }

    @GetMapping(value = "/edit/{id}")
    public String edit(Model model, @PathVariable Long id) {
        try {
            Patient patient = patientService.findById(id);

            model.addAttribute("userList", userService.findAllByUserType(UserType.PATIENT.toString()));
            model.addAttribute("patient", patient);
            return "patient/edit_patient";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("userList", userService.findAllByUserType(UserType.PATIENT.toString()));
            model.addAttribute("error", "ERROR - Patient not found!");
            return "patient/edit_patient";
        }
    }

    // @Comment: This method will create patient and will render patient list form after create patient.
    @PostMapping("/update/{id}")
    public String updatePatient(Model model, @PathVariable Long id, @ModelAttribute("patient") Patient patient,
                                BindingResult result) throws IOException {
        try {
          /*  if (result.hasErrors()) {
                logger.info("Patient not update!");
                model.addAttribute("error", "Please enter all mendatory fields!");
                return "patient/edit_patient";
            }*/

            /*SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
            String prDate = formatter.format(patient.getAdmissionDate());
            String createDate = formatter.format(new Date());
            patient.setCreateDate(formatter.parse(createDate));
            patient.setAdmissionDate(formatter.parse(prDate));

            if (patient.getUpdateDate() != null) {
                String nVisitDate = formatter.format(patient.getUpdateDate());
                patient.setUpdateDate(formatter.parse(nVisitDate));
            }*/
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
            model.addAttribute("userList", userService.findAllByUserType(UserType.PATIENT.toString()));
            model.addAttribute("patientList", patientList);
            model.addAttribute("success", "Patient updated successfully.");
            return "patient/view_patient";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("userList", userService.findAllByUserType(UserType.PATIENT.toString()));
            model.addAttribute("error", "ERROR - Patient not saved!");
            return "patient/edit_patient";
        }
    }

    @GetMapping(value = "/delete/{id}")
    public String delete(Model model, @PathVariable Long id) {
        List<Patient> patientList = new ArrayList<>();
        try {
            Patient patient = patientService.findById(id);
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

}
