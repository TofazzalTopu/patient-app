package com.info.patient.repository;

import com.info.patient.model.Doctor;
import com.info.patient.model.Patient;
import com.info.patient.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    List<Patient> findAllByUser(User user);
    List<Patient> findAllByDoctor(Doctor doctor);
    List<Patient> findAllByIdAndDoctor(Long patientId, Doctor doctor);

}
