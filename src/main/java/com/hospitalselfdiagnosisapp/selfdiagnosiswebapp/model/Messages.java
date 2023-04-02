package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Messages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String message;

    public Messages(String name, String email, String message) {
        this.name = name;
        this.email = email;
        this.message = message;
    }
}
