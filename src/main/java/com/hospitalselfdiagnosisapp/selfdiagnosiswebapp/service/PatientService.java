package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.service;

import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.dto.PatientDTO;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientService {
    Patient findPatientByEmail(String email);

    public void save(PatientDTO patientDTO);

    List<PatientDTO> findAllUsers();
}
