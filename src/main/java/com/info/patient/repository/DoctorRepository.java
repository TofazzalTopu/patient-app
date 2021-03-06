package com.info.patient.repository;

import com.info.patient.model.Doctor;
import com.info.patient.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

    List<Doctor> findByUser_Id(Long id);
    List<Doctor> findByUser(User user);
}
