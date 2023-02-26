package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.service;

import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DoctorService{
    private DoctorRepository doctorRepository;


    @Autowired
    public DoctorService(DoctorRepository doctorRepository){
        this.doctorRepository=doctorRepository;
    }

//    OTHER CRUD OPERATION METHOD BELOW



}
