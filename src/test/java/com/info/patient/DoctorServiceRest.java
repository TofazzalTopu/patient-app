package com.info.patient;

import com.info.patient.enums.Role;
import com.info.patient.model.Doctor;
import com.info.patient.model.User;
import com.info.patient.repository.DoctorRepository;
import com.info.patient.service.DoctorService;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@NoArgsConstructor
public class DoctorServiceRest {

    @InjectMocks
    DoctorService doctorService;

    @Mock
    DoctorRepository doctorRepository;

    @Test
    public void save() {
        Doctor doctor = new Doctor();
        doctor.setId(1);
        doctor.setDesignation("D");
        doctor.setDegree("D");
        doctor.setExperience(1);

        Mockito.when(doctorRepository.save(doctor)).thenReturn(doctor);
        Assert.assertEquals(doctor, doctorService.save(doctor));
    }

    @Test
    public void findById(){
        Doctor doctor = new Doctor();
        doctor.setId(1);
        doctor.setDesignation("D");
        doctor.setDegree("D");
        doctor.setExperience(1);
        Mockito.when(doctorRepository.findById(1)).thenReturn(Optional.of(doctor));
    }

    @Test
    public void findAll() {
        Doctor doctor = new Doctor();
        doctor.setDesignation("D");
        doctor.setDegree("D");
        doctor.setExperience(1);

        List<Doctor> doctorList = Arrays.asList(doctor);
        Mockito.when(doctorRepository.findAll()).thenReturn(doctorList);
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

        List<Doctor> doctorList = new ArrayList<>();
        doctorList.add(doctor);
        Mockito.when(doctorRepository.findByUser(user)).thenReturn(doctorList);
//        Mockito.verify(doctorRepository, Mockito.atLeastOnce()).findByUser(user);
    }
}
