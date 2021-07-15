package com.info.prescription.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "observations")
public class Observation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(targetEntity = Patient.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    private String height;
    private String weight;
    private String BMI;
    private String BP;
    @Transient
    private Long patientId;

}
