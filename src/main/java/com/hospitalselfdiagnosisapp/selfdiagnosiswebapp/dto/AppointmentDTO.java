package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDTO {
    private String title;
    private String doctorEmail;
    private String patientEmail;
    private LocalDateTime appointmentDate;

}
