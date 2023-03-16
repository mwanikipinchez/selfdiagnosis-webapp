package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.service;

import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.dto.PharmacyDTO;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model.Pharmacy;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model.Role;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.repository.PharmacyRepository;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class PharmacyServiceImpl implements PharmacyService{
    private PharmacyRepository pharmacyRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;


    @Autowired
    public PharmacyServiceImpl(PharmacyRepository pharmacyRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository){
        this.pharmacyRepository=pharmacyRepository;
        this.roleRepository=roleRepository;
        this.passwordEncoder=passwordEncoder;
    }

    @Override
    public Pharmacy save(PharmacyDTO pharmacyDTO) {
        Pharmacy pharmacy = new Pharmacy();
        pharmacy.setName(pharmacyDTO.getName());
        pharmacy.setAddress(pharmacyDTO.getAddress());
        pharmacy.setEmail(pharmacyDTO.getEmail());
        pharmacy.setTelephone(pharmacyDTO.getTelephone());
        pharmacy.setPassword(passwordEncoder.encode(pharmacyDTO.getPassword()));
        pharmacy.setDateRegistered(LocalDateTime.now());
        Role role = roleRepository.findByName("ROLE_PHARMACY");
        if(role == null){
            role = checkRoleExist();
        }
        pharmacy.setRoles(Arrays.asList(role));
        return  pharmacyRepository.save(pharmacy);

    }
    private Role checkRoleExist(){
        Role role = new Role();
        role.setName("ROLE_PHARMACY");
        return roleRepository.save(role);
    }

    @Override
    public Pharmacy findByEmail(String email) {
        return pharmacyRepository.findByEmail(email);
    }

    @Override
    public List<Pharmacy> findAllPharmacy() {
        return pharmacyRepository.findAll();
    }


    private Collection < ? extends GrantedAuthority > mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());

    }

//    CRUD OPERATIONS GOES HERE




}
//
//    //    Get all pharmacy
//    public List<Pharmacy> allPharmacy(){
//        return pharmacyRepository.findAll();
//    }
//
//    //    get one pharmacy
//    public Optional<Pharmacy> onePharmacy(Long id){
//
//        return pharmacyRepository.findById(id);
//    }
//
//    //    Update pharmacy
//    public void updatePharmacy(Long id, Pharmacy pharmacy){
//        Optional<Pharmacy> optionalPharmacy = pharmacyRepository.findById(id);
//        Pharmacy updatedPharmacy;
//        if(optionalPharmacy.isPresent()){
//            updatedPharmacy=optionalPharmacy.get();
//            updatedPharmacy.setId(id);
//            updatedPharmacy.setAddress(pharmacy.getAddress());
//            updatedPharmacy.setName(pharmacy.getName());
//            updatedPharmacy.setTelephone(pharmacy.getTelephone());
//
//            updatedPharmacy.setEmail(pharmacy.getEmail());
//            updatedPharmacy.setPassword(pharmacy.getPassword());
//
//        }
//
//    }
//
//    //    create a pharmacy
//    public void newPharmacy(Pharmacy pharmacy){
//        pharmacyRepository.save(pharmacy);
//    }
//
//    //delete a pharmacy
//    public void deletepharmacy(Long id){
//        pharmacyRepository.deleteById(id);
//    }
//
//
