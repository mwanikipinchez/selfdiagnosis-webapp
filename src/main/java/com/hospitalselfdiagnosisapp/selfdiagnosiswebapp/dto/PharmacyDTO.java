package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PharmacyDTO {
    @NotEmpty(message = "Name should not be empty")
    private String name;

    @NotEmpty(message = "Address should not be empty")
    private String address;

    @NotEmpty(message = "Email should not be empty")
    @Email
    private String email;
    @NotEmpty(message = "Phone number should not be empty")
    private int telephone;

    @NotEmpty(message = "Password should not be empty")
    private String password;

}
