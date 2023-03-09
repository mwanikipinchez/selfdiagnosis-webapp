package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.service;

import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.dto.PatientDTO;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model.Patient;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model.Role;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.repository.PatientRepository;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements PatientService{
    private PatientRepository patientRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository,
                              RoleRepository roleRepository,
                              PasswordEncoder passwordEncoder){
        this.patientRepository=patientRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder=passwordEncoder;
    }


//    OTHER CRUD OPERATIONS METHOD GOES HERE
//    get patients
//    public List allPatient(){
//        return patientRepository.findAll();
//    }
public List<PatientDTO> findAllUsers() {
    List<Patient> patients = patientRepository.findAll();
    return patients.stream()
            .map((patient) -> mapToPatientDTO(patient))
            .collect(Collectors.toList());
}
    private PatientDTO mapToPatientDTO(Patient patient){
        PatientDTO patientDTO = new PatientDTO();

        patientDTO.setName(patient.getName());
        patientDTO.setEmail(patient.getEmail());
        return patientDTO;
    }

//    get one patient
    public Optional<Patient> patient(Long id){

        return patientRepository.findById(id);

    }




//    Create a new patient
//    public void newPatient(Patient patient){
//        patientRepository.save(patient);
//    }

    @Override
    public Patient findByEmail(String email) {
        return patientRepository.findByEmail(email);
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

    @Override
    public void newPatient(PatientDTO patientDTO) {
        Patient patient = new Patient();
        patient.setName(patientDTO.getName());
        patient.setGender(patientDTO.getGender());
        patient.setAddress(patientDTO.getAddress());
        patient.setEmail(patientDTO.getEmail());
        patient.setDob(patientDTO.getDob());
        patient.setPassword(passwordEncoder.encode(patientDTO.getPassword()));
        Role role = roleRepository.findByName("ADMIN");
        if(role == null){
            role = checkRoleExist();
        }
        patient.setRoles(Arrays.asList(role));
        patientRepository.save(patient);
    }



    //    deleting a patient from the db
    public void deletePatient(Long id)
    {
        patientRepository.deleteById(id);
    }

    private Role checkRoleExist(){
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }

}
