package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="appointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String doctorEmail;
    @Column(nullable = false)
    private String patientEmail;
    @Column(nullable = false)
    private LocalDateTime appointmentDate;
    @Column(nullable = false)
    private LocalDateTime createdOn;


}
