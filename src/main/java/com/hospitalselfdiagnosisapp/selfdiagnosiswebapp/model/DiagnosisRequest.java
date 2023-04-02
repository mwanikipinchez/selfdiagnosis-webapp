package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DiagnosisRequest {
    private String sex;
    private int age;
    private List<Evidence> evidence;


    // Constructors, getters, and setters


}
