package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.security;

import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model.Patient;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model.Role;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.repository.PatientRepository;
import jakarta.transaction.Transactional;
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
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    private PatientRepository patientRepository;




    public CustomUserDetailsService(PatientRepository patientRepository)
    {
        this.patientRepository=patientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Patient patient = patientRepository.findByEmail(email);


        if (patient != null) {
            return new org.springframework.security.core.userdetails.User(patient.getEmail(),
                    patient.getPassword(),
                    mapRolesToAuthorities(patient.getRoles()));
        }else{
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
