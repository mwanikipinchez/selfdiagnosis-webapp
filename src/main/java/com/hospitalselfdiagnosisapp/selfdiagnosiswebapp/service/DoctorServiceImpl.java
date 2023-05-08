<<<<<<< HEAD
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
=======
//package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.service;
//
//import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.dto.DoctorDTO;
//import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model.Doctor;
//import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model.Role;
//import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.repository.DoctorRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//
//@Service
//public class DoctorServiceImpl implements DoctorService {
//    private DoctorRepository doctorRepository;
//
//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;
//
//    @Autowired
//    public DoctorServiceImpl(DoctorRepository doctorRepository){
//        super();
//        this.doctorRepository=doctorRepository;
//    }
//
//
//    @Override
//    public Doctor save(DoctorDTO registrationDTO) {
//        Doctor doctor = new Doctor(registrationDTO.getName(),
//                registrationDTO.getGender(), registrationDTO.getIdNo(),
//                registrationDTO.getTelephone(),registrationDTO.getDob(),
//                registrationDTO.getDoctorNumber(), registrationDTO.getAddress(),
//                registrationDTO.getEmail(),
//                passwordEncoder.encode(registrationDTO.getPassword()),
//                LocalDateTime.now(), Arrays.asList(new Role("DOCTOR")));
//
//        return doctorRepository.save(doctor);
//
//    }
//
//    @Override
//    public Doctor findByEmail(String email) {
//        return doctorRepository.findByEmail(email);
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username)
//            throws UsernameNotFoundException {
//        Doctor doctor = doctorRepository.findByEmail(username);
//        if(doctor == null ){
//            throw  new UsernameNotFoundException("Invalid username or password");
//
//        }
//        return new org.springframework.security.core.userdetails.User(doctor.getEmail(),
//                doctor.getPassword(), mapRolesToAuthorities(doctor.getRoles()));
//    }
//    private Collection < ? extends GrantedAuthority > mapRolesToAuthorities(Collection<Role> roles) {
//        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
//    }
//}
//////    OTHER CRUD OPERATION METHOD BELOW
////
//////    Get all doctors
////    public List<Doctor> allDoctor(){
////        return doctorRepository.findAll();
////    }
////
//////    get one customer
////    public Optional<Doctor> oneDoctor(Long id){
////
////        return doctorRepository.findById(id);
////    }
////
//////    Update customer
////    public void updateDoctor(Long id, Doctor doctor){
////        Optional<Doctor> optionalDoctored = doctorRepository.findById(id);
////        Doctor updatedDoctor;
////        if(optionalDoctored.isPresent()){
////            updatedDoctor=optionalDoctored.get();
////            updatedDoctor.setId(id);
////            updatedDoctor.setDoctorNumber(doctor.getDoctorNumber());
////            updatedDoctor.setName(doctor.getName());
////            updatedDoctor.setDob(doctor.getDob());
////            updatedDoctor.setAddress(doctor.getAddress());
////            updatedDoctor.setEmail(doctor.getEmail());
////            updatedDoctor.setPassword(doctor.getPassword());
////            updatedDoctor.setIdNo(doctor.getIdNo());
////            updatedDoctor.setTelephone(doctor.getTelephone());
////            doctorRepository.save(updatedDoctor);
////        }
////
////    }
////
//////    create a doctor
////    public void newDoctor(Doctor doctor){
////        doctorRepository.save(doctor);
////    }
////
//////delete a doctor
////    public void deleteDoctor(Long id){
////        doctorRepository.deleteById(id);
////    }
>>>>>>> master
