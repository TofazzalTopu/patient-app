package com.info.patient.repository;

import com.info.patient.model.Observation;
import com.info.patient.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObservationRepository extends JpaRepository<Observation, Long> {

    List<Observation> findAllByPatient(Patient patient);
}
