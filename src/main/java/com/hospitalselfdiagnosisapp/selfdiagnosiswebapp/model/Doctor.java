package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name="doctor_records")
@Entity
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String gender;
    @Column(nullable = false)
    private int idNo;
    @Column(nullable = false)
    private int telephone;
    @Column(nullable = false)
    private LocalDate dob;
    @Column(nullable = false, unique = true)
    private String doctorNumber;
    @Column(nullable = false)
    private String address;
    @Column(unique = true,nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    private LocalDateTime dateRegistered ;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "doctors_roles",
            joinColumns = @JoinColumn(
                    name = "DOCTOR_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(
                    name = "ROLE_ID", referencedColumnName = "ID"))

    private List<Role> roles = new ArrayList<>();

}
