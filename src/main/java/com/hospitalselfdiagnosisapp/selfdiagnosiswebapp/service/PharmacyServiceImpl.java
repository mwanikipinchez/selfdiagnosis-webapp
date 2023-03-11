package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.service;

import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.dto.PharmacyDTO;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model.Pharmacy;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model.Role;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.repository.PharmacyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class PharmacyServiceImpl implements PharmacyService{
    private PharmacyRepository pharmacyRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    public PharmacyServiceImpl(PharmacyRepository pharmacyRepository){
        this.pharmacyRepository=pharmacyRepository;
    }

    @Override
    public Pharmacy save(PharmacyDTO pharmacyDTO) {
        Pharmacy pharmacy = new Pharmacy(pharmacyDTO.getName(),pharmacyDTO.getAddress(),
                pharmacyDTO.getEmail(),pharmacyDTO.getTelephone(),
                passwordEncoder.encode(pharmacyDTO.getPassword()),
                LocalDateTime.now(), Arrays.asList(new Role("Pharmacy")));
        return pharmacyRepository.save(pharmacy);
    }

    @Override
    public Pharmacy findByEmail(String email) {
        return pharmacyRepository.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Pharmacy pharmacy = pharmacyRepository.findByEmail(username);
        if (pharmacy == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(pharmacy.getEmail(),
                pharmacy.getPassword(), mapRolesToAuthorities(pharmacy.getRoles()));
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
