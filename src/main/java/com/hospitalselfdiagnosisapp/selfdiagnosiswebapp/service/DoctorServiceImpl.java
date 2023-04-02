package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.service;

import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.dto.DoctorDTO;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model.Doctor;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model.Role;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.repository.DoctorRepository;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class DoctorServiceImpl implements DoctorService {
    private DoctorRepository doctorRepository;


    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;


    public DoctorServiceImpl(DoctorRepository doctorRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder){
        super();
        this.doctorRepository=doctorRepository;
        this.roleRepository=roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public Doctor saveDoctor(DoctorDTO doctor) {
        Doctor newdoc = new Doctor();
        newdoc.setName(doctor.getName());
        newdoc.setGender(doctor.getGender());
        newdoc.setIdNo(doctor.getIdNo());
        newdoc.setTelephone(doctor.getTelephone());
        newdoc.setDob(doctor.getDob());
        newdoc.setDoctorNumber(doctor.getDoctorNumber());
        newdoc.setAddress(doctor.getAddress());
        newdoc.setEmail(doctor.getEmail());
        newdoc.setPassword(passwordEncoder.encode(doctor.getPassword()));
       newdoc.setDateRegistered(LocalDateTime.now());
        Role role = roleRepository.findByName("ROLE_DOCTOR");
        if(role == null){
            role = checkRoleExist();
        }
        newdoc.setRoles(Arrays.asList(role));
    return  doctorRepository.save(newdoc);

    }
    private Role checkRoleExist(){
        Role role = new Role();
        role.setName("ROLE_DOCTOR");
        return roleRepository.save(role);
    }


    @Override
    public Doctor findDoctorByEmail(String email) {
        return doctorRepository.findByEmail(email);
    }

    @Override
    public List<Doctor> findAllDoctors() {
        return doctorRepository.findAll();
    }
}
