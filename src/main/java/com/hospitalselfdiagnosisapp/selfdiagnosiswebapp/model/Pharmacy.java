package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Collection;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="pharmacy_records")
public class Pharmacy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String address;

    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private int telephone;
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private LocalDateTime dateRegistered;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "pharmacy_roles",
            joinColumns = @JoinColumn(
                    name = "PHARMACY_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(
                    name = "ROLE_ID", referencedColumnName = "ID"))

    private Collection< Role > roles;


    public Pharmacy(String name, String address, String email, int telephone,
                    String password, LocalDateTime dateRegistered, Collection<Role> roles){
        this.name=name;
        this.address=address;
        this.email=email;
        this.telephone = telephone;
        this.password = password;
        this.dateRegistered=dateRegistered;
        this.roles=roles;

    }

}
