package com.info.patient;

import com.info.patient.enums.Role;
import com.info.patient.model.Doctor;
import com.info.patient.model.Observation;
import com.info.patient.model.Patient;
import com.info.patient.model.User;
import com.info.patient.repository.ObservationRepository;
import com.info.patient.service.ObservationService;
import lombok.NoArgsConstructor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@NoArgsConstructor
public class ObservationServiceTest {

    @InjectMocks
    ObservationService observationService;

    @Mock
    ObservationRepository observationRepository;

    @Test
    public void save() {
        User user = new User();
        user.setId(1L);
        user.setUsername("rana");
        user.setFirstName("Mr.");
        user.setLastName("Jatin");
        user.setRole(Role.PATIENT.toString());
        user.setPassword("123");
        user.setDob(new Date());
        user.setEmail("rana@gmail.com");

        Doctor doctor = new Doctor();
        doctor.setDesignation("D");
        doctor.setDegree("D");
        doctor.setExperience(1);

        Patient patient = new Patient();
        patient.setBloodGroup("A+");
        patient.setCreateDate(new Date());
        patient.setUser(user);
        patient.setDoctor(doctor);

        Observation observation = new Observation();
        observation.setPatient(patient);
        observation.setBMI("B");
        observation.setBP("B");
        observation.setHeight("H");
        observation.setWeight("w");

        Mockito.when(observationRepository.save(observation)).thenReturn(observation);
        Assert.assertEquals(observation, observationService.save(observation));
    }

    @Test
    public void findById(){
        User user = new User();
        user.setId(1L);
        user.setUsername("rana");
        user.setFirstName("Mr.");
        user.setLastName("Jatin");
        user.setRole(Role.PATIENT.toString());
        user.setPassword("123");
        user.setDob(new Date());
        user.setEmail("rana@gmail.com");

        Doctor doctor = new Doctor();
        doctor.setDesignation("D");
        doctor.setDegree("D");
        doctor.setExperience(1);

        Patient patient = new Patient();
        patient.setBloodGroup("A+");
        patient.setCreateDate(new Date());
        patient.setUser(user);
        patient.setDoctor(doctor);

        Observation observation = new Observation();
        observation.setPatient(patient);
        observation.setBMI("B");
        observation.setBP("B");
        observation.setHeight("H");
        observation.setWeight("w");
        Mockito.when(observationRepository.findById(1L)).thenReturn(Optional.of(observation));
    }

    @Test
    public void findAll() {
        User user = new User();
        user.setId(1L);
        user.setUsername("rana@gmail.com");
        user.setFirstName("Mr.");
        user.setLastName("Jatin");
        user.setRole(Role.PATIENT.toString());
        user.setPassword("123");

        Doctor doctor = new Doctor();
        doctor.setDesignation("D");
        doctor.setDegree("D");
        doctor.setExperience(1);

        Patient patient = new Patient();
        patient.setBloodGroup("A+");
        patient.setCreateDate(new Date());
        patient.setUser(user);
        patient.setDoctor(doctor);

        Observation observation = new Observation();
        observation.setPatient(patient);
        observation.setBMI("B");
        observation.setBP("B");
        observation.setHeight("H");
        observation.setWeight("w");

        List<Observation> observationList = Arrays.asList(observation);
        Mockito.when(observationRepository.findAll()).thenReturn(observationList);
    }

    @Test
    public void findAllByPatient()  {
        User user = new User();
        user.setId(1L);
        user.setUsername("rana@gmail.com");
        user.setFirstName("Mr.");
        user.setLastName("Jatin");
        user.setRole(Role.PATIENT.toString());
        user.setPassword("123");

        Doctor doctor = new Doctor();
        doctor.setDesignation("D");
        doctor.setDegree("D");
        doctor.setExperience(1);
        doctor.setId(1);

        Patient patient = new Patient();
        patient.setId(1L);
        patient.setBloodGroup("A+");
        patient.setCreateDate(new Date());
        patient.setUser(user);
        patient.setDoctor(doctor);

        Observation observation = new Observation();
        observation.setPatient(patient);
        observation.setBMI("B");
        observation.setBP("B");
        observation.setHeight("H");
        observation.setWeight("w");

        List<Observation> observationList = new ArrayList<>();
        observationList.add(observation);

        Mockito.when(observationRepository.findAllByPatient(patient)).thenReturn(observationList);
//        Mockito.verify(observationRepository, Mockito.atLeastOnce()).findAllByPatient(patient);
    }

    @Test
    public void delete(){
        Observation observation = new Observation();
        observation.setBMI("B");
        observation.setBP("B");
        observation.setHeight("H");
        observation.setWeight("w");

        observationService.delete(observation);
        Mockito.verify(observationRepository, Mockito.times(1)).delete(observation);
    }
}
