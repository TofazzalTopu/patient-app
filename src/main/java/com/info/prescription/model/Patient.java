package com.info.prescription.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
/*
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;*/
import java.util.Date;

@Data
@Entity
@Table(name = "patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "blood_group")
    private String bloodGroup;

    //    @NotBlank(message = "Patient Date is mandatory")
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
//    @DateTimeFormat(pattern = DateTimeFormat.ISO.DATE)
    @Column(name = "admission_date")
    private Date admissionDate;

//    @Column(name = "gender")
//    private String gender;

    @Column(name = "create_date")
    private Date createDate;

    //    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "update_date")
    private Date updateDate;
    private Long updatedBy;

    @Transient
    private int count;
}
