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
    private String name;
    private int idNo;
    private int telephone;
    private int dob;
    private int doctorNumber;
    private String address;
    @Column(unique = true)
    private String email;

}
