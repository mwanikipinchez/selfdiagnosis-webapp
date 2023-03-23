package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="patient_records")
@Entity
public class Patient {
//    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "This field is mandatory")
    @Column(nullable = false)
    private String name;
    @NotBlank(message = "This field is mandatory")
    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private LocalDate dob;
    @NotBlank(message = "This field is mandatory")
    @Column(nullable = false)
    private String address;
    @NotBlank(message = "This field is mandatory")

    @Column(unique = true, nullable = false)
    private String email;


    @Column(nullable = false)
    private LocalDateTime dateRegistered = LocalDateTime.now();
    @NotBlank(message = "This field is mandatory")
    @Column(nullable = false)
    private String password;


    @ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(
            name="patients_roles",
            joinColumns={@JoinColumn(name="PATIENT_ID", referencedColumnName="ID")},
            inverseJoinColumns = {@JoinColumn(name="ROLE_ID", referencedColumnName="ID")}
    )


    private List<Role> roles = new ArrayList<>();

    }





