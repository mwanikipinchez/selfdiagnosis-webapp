package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.repository;

import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PharmacyRepository extends JpaRepository<Pharmacy,Long> {
    Pharmacy findByEmail(String email);

}
