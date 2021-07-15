package com.info.prescription.repository;

import com.info.prescription.model.Patient;
import com.info.prescription.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    List<Patient> findAllByUser(User user);
//    List<Patient> findAllByPatientId(String patientId);

}
