package com.info.patient.service;

import com.info.patient.model.Doctor;
import com.info.patient.model.User;
import com.info.patient.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    UserService userService;

    public Doctor save(Doctor doctor){
        return doctorRepository.save(doctor);
    }

    public Doctor findById(Integer id) {
        Optional<Doctor> doctor = doctorRepository.findById(id);
        if(doctor.isPresent()){
            return doctor.get();
        }
        return null;
    }
    public Doctor findByUser(User user) {
        List<Doctor> doctor = doctorRepository.findByUser(user);
        if(doctor.isEmpty()){
            return null;
        }
        return doctor.get(0);
    }

}
