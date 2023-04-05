package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Condition {
    private String id;
    private String name;

    @JsonProperty("common_name")
    private String commonName;
    private Double probability;
}
