package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.service;

import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model.PharmacyProducts;

import java.util.List;
import java.util.Optional;

public interface PharmacyProductsService {
    List<PharmacyProducts> allPharmacyProducts();
    List<PharmacyProducts> myProducts(String username);
    Optional<PharmacyProducts> searchById(Long id);
    PharmacyProducts newPharmacyProduct(PharmacyProducts pharmacyProducts);


}
