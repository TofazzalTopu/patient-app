package com.info.patient;

import com.info.patient.enums.Role;
import com.info.patient.model.Doctor;
import com.info.patient.model.Patient;
import com.info.patient.model.User;
import com.info.patient.repository.PatientRepository;
import com.info.patient.service.PatientService;
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
public class PatientServiceTest {

    @InjectMocks
    PatientService patientService;

    @Mock
    PatientRepository patientRepository;

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
        Mockito.when(patientRepository.save(patient)).thenReturn(patient);
        Assert.assertEquals(patient, patientService.save(patient));
    }

    @Test
    public void findById(){
        Patient patient = new Patient();
        patient.setBloodGroup("A+");
        patient.setId(1L);
        Mockito.when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
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
        List<Patient> patientList = Arrays.asList(patient);
        Mockito.when(patientRepository.findAll()).thenReturn(patientList);
    }

    @Test
    public void findAllByUser()  {
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
        patient.setBloodGroup("A+");
        patient.setCreateDate(new Date());
        patient.setUser(user);
        patient.setDoctor(doctor);
        List<Patient> patientList = new ArrayList<>();
        patientList.add(patient);
        Mockito.when(patientRepository.findAllByUser(user)).thenReturn(patientList);
//        Mockito.verify(patientRepository, Mockito.atLeastOnce()).findAllByUser(user);
    }

    @Test
    public void findAllByDoctor()  {
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
        List<Patient> patientList = new ArrayList<>();
        patientList.add(patient);
        Mockito.when(patientRepository.findAllByDoctor(doctor)).thenReturn(patientList);
//        Mockito.verify(patientRepository, Mockito.atLeastOnce()).findAllByDoctor(doctor);
    }

    @Test
    public void delete(){
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setBloodGroup("A+");
        patient.setCreateDate(new Date());
        patientService.deletePatient(patient);
        Mockito.verify(patientRepository, Mockito.times(1)).delete(patient);
    }
}
