package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.service;

import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.dto.PharmacyDTO;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model.Pharmacy;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface PharmacyService{
    Pharmacy save(PharmacyDTO pharmacyDTO);
    Pharmacy findByEmail(String email);
    List<Pharmacy> findAllPharmacy();

}
//