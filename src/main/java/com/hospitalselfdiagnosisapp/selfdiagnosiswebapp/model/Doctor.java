package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="doctor_records")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private int idNo;
    @Column(nullable = false)
    private int telephone;
    @Column(nullable = false)
    private int dob;
    @Column(nullable = false)
    private int doctorNumber;
    @Column(nullable = false)
    private String address;
    @Column(unique = true,nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;

}
