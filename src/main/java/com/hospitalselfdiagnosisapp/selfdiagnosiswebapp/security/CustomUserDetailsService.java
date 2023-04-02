package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.security;

import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model.Doctor;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model.Patient;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model.Pharmacy;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model.Role;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.repository.DoctorRepository;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.repository.PatientRepository;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.repository.PharmacyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.Collection;
import java.util.stream.Collectors;


@Service

public class CustomUserDetailsService implements UserDetailsService {

    private PatientRepository patientRepository;
    private DoctorRepository doctorRepository;
    private PharmacyRepository pharmacyRepository;



    public CustomUserDetailsService(PatientRepository patientRepository, DoctorRepository doctorRepository, PharmacyRepository pharmacyRepository) {
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.pharmacyRepository = pharmacyRepository;
    }



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Patient patient = patientRepository.findByEmail(email);
        Doctor doctor = doctorRepository.findByEmail(email);
        Pharmacy pharmacy = pharmacyRepository.findByEmail(email);


        if (patient != null) {
            return new org.springframework.security.core.userdetails.User(patient.getEmail(),
                    patient.getPassword(),
                    mapRolesToAuthorities(patient.getRoles()));
        } else if (doctor != null) {
            return new org.springframework.security.core.userdetails.User(doctor.getEmail(),
                    doctor.getPassword(),
                    mapRolesToAuthorities(doctor.getRoles()));

        } else if (pharmacy != null) {
            return new org.springframework.security.core.userdetails.User(pharmacy.getEmail(),
                    pharmacy.getPassword(),
                    mapRolesToAuthorities(pharmacy.getRoles()));

        } else{
            throw new UsernameNotFoundException("Invalid username or password.");
        }

    }
    private Collection < ? extends GrantedAuthority> mapRolesToAuthorities(Collection <Role> roles) {
        Collection< ? extends GrantedAuthority> mapRoles = roles.stream()
                 .map(role -> new SimpleGrantedAuthority(role.getName()))
                 .collect(Collectors.toList());
        return mapRoles;
    }

}
