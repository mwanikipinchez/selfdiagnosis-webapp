package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="roles")
public class Role {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;


    public Role(String name){
        this.name=name;
    }

    @ManyToMany(mappedBy="roles")
    private List<Patient> patients;
//<<<<<<< HEAD
    @ManyToMany(mappedBy="roles")
    private List<Doctor> doctors;

    @ManyToMany(mappedBy="roles")
//=======
//    private List<Doctor> doctors;
//>>>>>>> master
    private List<Pharmacy> pharmacy;

}


