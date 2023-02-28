package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.service;

import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model.Doctor;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model.Pharmacy;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.repository.PharmacyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class PharmacyService {
    private PharmacyRepository pharmacyRepository;

    @Autowired
    public PharmacyService(PharmacyRepository pharmacyRepository){
        this.pharmacyRepository=pharmacyRepository;
    }

//    CRUD OPERATIONS GOES HERE

    //    Get all pharmacy
    public List<Pharmacy> allPharmacy(){
        return pharmacyRepository.findAll();
    }

    //    get one pharmacy
    public Optional<Pharmacy> onePharmacy(Long id){

        return pharmacyRepository.findById(id);
    }

    //    Update pharmacy
    public void updatePharmacy(Long id, Pharmacy pharmacy){
        Optional<Pharmacy> optionalPharmacy = pharmacyRepository.findById(id);
        Pharmacy updatedPharmacy;
        if(optionalPharmacy.isPresent()){
            updatedPharmacy=optionalPharmacy.get();
            updatedPharmacy.setId(id);
            updatedPharmacy.setAddress(pharmacy.getAddress());
            updatedPharmacy.setName(pharmacy.getName());
            updatedPharmacy.setTelephone(pharmacy.getTelephone());

            updatedPharmacy.setEmail(pharmacy.getEmail());
            updatedPharmacy.setPassword(pharmacy.getPassword());

        }

    }

    //    create a pharmacy
    public void newPharmacy(Pharmacy pharmacy){
        pharmacyRepository.save(pharmacy);
    }

    //delete a pharmacy
    public void deletepharmacy(Long id){
        pharmacyRepository.deleteById(id);
    }




}
