package com.info.patient.service;

import com.info.patient.model.Observation;
import com.info.patient.model.Patient;
import com.info.patient.repository.ObservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ObservationService {

    public final ObservationRepository observationRepository;

    public ObservationService(ObservationRepository observationRepository) {
        this.observationRepository = observationRepository;
    }

    public Observation save(Observation observation) {
        return observationRepository.save(observation);
    }

    public Observation update(Observation observation) {
        return observationRepository.save(observation);
    }

    public List<Observation> findAllByPatient(Patient patient) {
        return observationRepository.findAllByPatient(patient);
    }

    public Observation findById(Long id) {
        Optional<Observation> observation = observationRepository.findById(id);
        if (observation.isPresent()) {
            return observation.get();
        }
        return null;
    }

    public List<Observation> findAll() {
        return observationRepository.findAll();
    }

    public boolean delete(Observation observation) {
        boolean isDelete = true;
        try {
            observationRepository.delete(observation);
        } catch (Exception e) {
            isDelete = false;
            e.printStackTrace();
        }
        return isDelete;
    }
}
