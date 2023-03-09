package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.service;

import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.dto.PatientDTO;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientService {
    Patient findByEmail(String email);
    public void updatePatient(Long id, Patient patient);
    public void newPatient(PatientDTO patientDTO);
    public Optional<Patient> patient(Long id);
    List<PatientDTO> findAllUsers();
}
