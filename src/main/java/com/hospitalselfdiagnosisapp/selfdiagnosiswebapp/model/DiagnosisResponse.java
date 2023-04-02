package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DiagnosisResponse {
    private String question;
    private String conditionName;
    private Double probability;

}
