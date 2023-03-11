package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.dto;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDate;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor


public class PatientDTO {
    private Long id;

    @NotEmpty
    private String name;
    @NotEmpty(message="Email should not be empty")
    @Email
    private String email;

    @NotEmpty
    private String gender;

//    @NotEmpty
    private LocalDate dob;
    @NotEmpty
    private String address;
    @NotEmpty
    private String dateRegistered;

    @NotEmpty(message= "Password should not be empty")
    private String password;
}
