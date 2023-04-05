package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.service;


import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model.InfermedicaSymptoms;
import org.springframework.data.domain.Page;



public interface InfermedicaSymptomsService {
    InfermedicaSymptoms searchByName(String name);
//    InfermedicaSymptoms searchByCommon_name(String name);

   InfermedicaSymptoms save(InfermedicaSymptoms infermedicaSymptoms);

    Page< InfermedicaSymptoms > findPaginated(int pageNo, int pageSize);
}
