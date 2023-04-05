package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.service;

import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model.PharmacyProducts;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.repository.PharmacyProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PharmacyProductsImpl implements PharmacyProductsService{

    private PharmacyProductsRepository pharmacyProductsRepository;


    @Autowired
    public PharmacyProductsImpl(PharmacyProductsRepository pharmacyProductsRepository) {
        this.pharmacyProductsRepository = pharmacyProductsRepository;
    }

    @Override
    public List<PharmacyProducts> allPharmacyProducts() {
        return pharmacyProductsRepository.findAll();
    }

    @Override
    public List<PharmacyProducts> myProducts(String username) {
        return pharmacyProductsRepository.findByPharmacy(username);
    }

    @Override
    public Optional<PharmacyProducts> searchById(Long id) {
        return pharmacyProductsRepository.findById(id);
    }

    @Override
    public PharmacyProducts newPharmacyProduct(PharmacyProducts pharmacyProducts) {
        return pharmacyProductsRepository.save(pharmacyProducts);
    }
}
