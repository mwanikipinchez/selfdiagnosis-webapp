package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.service;


import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model.InfermedicaSymptoms;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.repository.InfermedicaSymptomsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class InfermedicaSymptomsServiceImpl implements InfermedicaSymptomsService{
    private InfermedicaSymptomsRepository infermedicaSymptomsRepository;


    @Autowired
    public InfermedicaSymptomsServiceImpl(InfermedicaSymptomsRepository infermedicaSymptomsRepository) {
        this.infermedicaSymptomsRepository = infermedicaSymptomsRepository;
    }

    @Override
    public InfermedicaSymptoms searchByName(String name) {
        return infermedicaSymptomsRepository.findByName(name);
    }

//    @Override
//    public InfermedicaSymptoms searchByCommon_name(String name) {
//        return infermedicaSymptomsRepository.findByCommon_name(name);
//    }

    @Override
    public InfermedicaSymptoms save(InfermedicaSymptoms infermedicaSymptoms) {
        return infermedicaSymptomsRepository.save(infermedicaSymptoms);
    }

    @Override
    public Page<InfermedicaSymptoms> findPaginated(int pageNo, int pageSize) {


        Pageable pageable = (Pageable) PageRequest.of(pageNo - 1, pageSize);
        return infermedicaSymptomsRepository.findAll((org.springframework.data.domain.Pageable) pageable);
    }


}
