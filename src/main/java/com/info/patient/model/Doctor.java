package com.info.patient.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "doctors")
public class Doctor {

    @Id
    @Column(name = "doctor_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "u_user_id", nullable = false)
    private User user;

    @NonNull
    private String designation;
    private String degree;
    private int experience;

    @Transient
    @ManyToMany(mappedBy = "doctors")
    List<Patient> patients;

}
