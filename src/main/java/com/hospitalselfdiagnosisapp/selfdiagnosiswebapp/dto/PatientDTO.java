package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.dto;

import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model.Role;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor


public class PatientDTO {

    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String gender;
   @NotNull
    private LocalDate dob;
    @NotEmpty
    private String address;
    @NotEmpty(message="Email should not be empty")
    @Email
    private String email;

    @NotEmpty(message= "Password should not be empty")
//    @Size(min=8, max=20)
    private String password;

}
