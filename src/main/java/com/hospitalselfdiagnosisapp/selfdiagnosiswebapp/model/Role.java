package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="role")
public class Role {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    public Role(String name) {
        super();
        this.name = name;
    }


    @ManyToMany(mappedBy="roles")
    private List<Patient> patients;
    private List<Doctor> doctors;
    private List<Pharmacy> pharmacy;
}
