package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.service;

import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model.Patient;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {
    private PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository){
        this.patientRepository=patientRepository;
    }


//    OTHER CRUD OPERATIONS METHOD GOES HERE
//    get patients
    public List allPatient(){
        return patientRepository.findAll();
    }

//    get one patient
    public Optional<Patient> patient(Long id){

        return patientRepository.findById(id);

    }


//    Create a new patient
    public void newPatient(Patient patient){
        patientRepository.save(patient);
    }

//    update existing patient
    public void updatePatient(Long id, Patient patient){

        Optional<Patient> existingPatient = patientRepository.findById(id);

        if(existingPatient.isPresent()){
            Patient updatePatient=existingPatient.get();
            updatePatient.setId(id);
            updatePatient.setAddress(patient.getAddress());
            updatePatient.setName(patient.getName());
            updatePatient.setDob(patient.getDob());
            updatePatient.setEmail(patient.getEmail());
            updatePatient.setGender(patient.getGender());
            updatePatient.setPassword(patient.getPassword());

        }
    }

//    deleting a patient from the db
    public void deletePatient(Long id)
    {
        patientRepository.deleteById(id);
    }


}
