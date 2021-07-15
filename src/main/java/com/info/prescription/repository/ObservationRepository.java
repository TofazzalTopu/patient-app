package com.info.prescription.repository;

import com.info.prescription.model.Observation;
import com.info.prescription.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObservationRepository extends JpaRepository<Observation, Long> {

    List<Observation> findAllByPatient(Patient patient);
}
