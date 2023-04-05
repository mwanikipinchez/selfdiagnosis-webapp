package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.repository;

import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model.PharmacyProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PharmacyProductsRepository extends JpaRepository<PharmacyProducts, Long> {
    List<PharmacyProducts> findByPharmacy(String username);

}
