package com.info.prescription.service;

import com.info.prescription.model.Patient;
import com.info.prescription.model.User;
import com.info.prescription.repository.PatientRepository;
import com.info.prescription.utility.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PatientService {

    @Autowired
    PatientRepository patientRepository;


    public Patient save(Patient patient){
        patient.setCreateDate(new Date());
        return patientRepository.save(patient);
    }

    public List<Patient> findAll(){
        return patientRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    /*public List<Patient> findAllByPatientId(String patientId){
        return patientRepository.findAllByPatientId(patientId);
    }*/
    public List<Patient> findAllByUser(User user){
        return patientRepository.findAllByUser(user);
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
