package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.service;

import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.dto.PharmacyDTO;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model.Pharmacy;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface PharmacyService extends UserDetailsService {
    Pharmacy save(PharmacyDTO pharmacyDTO);
    Pharmacy findByEmail(String email);

}
