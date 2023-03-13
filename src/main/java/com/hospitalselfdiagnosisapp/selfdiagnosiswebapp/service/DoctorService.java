package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.service;

import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.dto.DoctorDTO;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model.Doctor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;

@Primary
public interface DoctorService extends UserDetailsService {
    Doctor save(DoctorDTO registrationDTO);
    Doctor findByEmail(String email);
}
