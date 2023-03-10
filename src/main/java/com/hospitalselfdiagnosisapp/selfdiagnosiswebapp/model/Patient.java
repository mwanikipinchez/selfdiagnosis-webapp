package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="patient_records")
@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String gender;
    @Column(nullable = false)
    private LocalDate dob;
    @Column(nullable = false)
    private String address;

    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    private LocalDateTime dateRegistered = LocalDateTime.now();
    private String role = "USER";


    @ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(
            name="patients_roles",
            joinColumns={@JoinColumn(name="PATIENT_ID", referencedColumnName="ID")},
            inverseJoinColumns = {@JoinColumn(name="ROLE_ID", referencedColumnName="ID")}
    )
    private List<Role> roles = new ArrayList<>();



}
