package com.info.patient.service;

import com.info.patient.enums.Role;
import com.info.patient.model.Doctor;
import com.info.patient.model.Patient;
import com.info.patient.model.User;
import com.info.patient.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PatientService {

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    UserService userService;
    @Autowired
    DoctorService doctorService;

    public Patient save(Patient patient){
        patient.setCreateDate(new Date());
        patient.getUser().setRole(Role.PATIENT.toString());
        userService.saveUser(patient.getUser());
        return patientRepository.save(patient);
    }

    public List<Patient> findAll(){
        return patientRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public List<Patient> findAllByUser(User user){
        return patientRepository.findAllByUser(user);
    }
    public Patient findByUser(User user){
        List<Patient> patientList = patientRepository.findAllByUser(user);
        if(patientList.isEmpty()){
            return null;
        }
        return patientList.get(0);
    }

    public List<Patient> findAllByDoctor(Doctor doctor){
        return patientRepository.findAllByDoctor(doctor);
    }
    public List<Patient> findAllByIdAndDoctor(Long patientId, Doctor doctor){
        return patientRepository.findAllByIdAndDoctor(patientId, doctor);
    }
    public Patient findById(Long id){
        return patientRepository.findById(id).get();
    }

    public boolean deletePatient(Patient patient){
        boolean isDelete = true;
        try {
            patientRepository.delete(patient);
        }catch (Exception e){
            isDelete = false;
            e.printStackTrace();
        }
        return isDelete;
    }


}
