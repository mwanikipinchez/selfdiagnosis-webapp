package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.service;

import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.dto.PatientDTO;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model.Patient;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model.Role;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.repository.PatientRepository;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Primary
public class PatientServiceImpl implements PatientService{
    private PatientRepository patientRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder passwordEncoder;


    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository,
                              RoleRepository roleRepository,
                              BCryptPasswordEncoder passwordEncoder){
        this.patientRepository=patientRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder=passwordEncoder;
    }





    @Override
    public void save(PatientDTO patientDTO) {
        Patient patient = new Patient();
        patient.setName(patientDTO.getName());
        patient.setGender(patientDTO.getGender());
        patient.setDob(patientDTO.getDob());
        patient.setAddress(patientDTO.getAddress());
        patient.setEmail(patientDTO.getEmail());
        patient.setPassword(passwordEncoder.encode(patientDTO.getPassword()));
        patient.setDateRegistered(LocalDateTime.now());
        Role role = roleRepository.findByName("ROLE_PATIENT");
        if(role == null){
            role = checkRoleExist();
        }
        patient.setRoles(Arrays.asList(role));
        patientRepository.save(patient);

    }
    private Role checkRoleExist(){
        Role role = new Role();
        role.setName("ROLE_PATIENT");
        return roleRepository.save(role);
    }

    @Override
    public Patient findPatientByEmail(String email) {
        return patientRepository.findByEmail(email);
    }

    @Override
    public List<PatientDTO> findAllUsers() {
        List<Patient> patients = patientRepository.findAll();
        return patients.stream()
                .map((patient) -> mapToPatientDTO(patient))
                .collect(Collectors.toList());
    }


    private PatientDTO mapToPatientDTO(Patient patient){
        PatientDTO patientDTO = new PatientDTO();

        patientDTO.setName(patient.getName());
        patientDTO.setGender(patient.getGender());
        patientDTO.setDob(patient.getDob());
        patientDTO.setAddress(patient.getAddress());
        patientDTO.setEmail(patient.getEmail());
        return patientDTO;
    }



}














//    public void updatePatient(Long id, Patient patient){
//
//        Optional<Patient> existingPatient = patientRepository.findById(id);
//
//        if(existingPatient.isPresent()){
//            Patient updatePatient=existingPatient.get();
//            updatePatient.setId(id);
//            updatePatient.setAddress(patient.getAddress());
//            updatePatient.setName(patient.getName());
//            updatePatient.setDob(patient.getDob());
//            updatePatient.setEmail(patient.getEmail());
//            updatePatient.setGender(patient.getGender());
//            updatePatient.setPassword(patient.getPassword());
//
//        }
//    }