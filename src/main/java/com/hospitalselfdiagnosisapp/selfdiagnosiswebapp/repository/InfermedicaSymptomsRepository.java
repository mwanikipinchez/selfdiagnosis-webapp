package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.repository;

import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model.InfermedicaSymptoms;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InfermedicaSymptomsRepository extends JpaRepository<InfermedicaSymptoms, Long> {



//    @Query("SELECT n FROM InfermedicaSymptoms n where n.name  ")

//    @Query("SELECT n FROM InfermedicaSymptoms n WHERE n.name LIKE %:searchTerm%")
    InfermedicaSymptoms findByName(@Param("searchTerm") String name);

     Page<InfermedicaSymptoms> findAll(Pageable pageable);
// List<InfermedicaSymptoms> findAllById(Pageable pageable);
}
