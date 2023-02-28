package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.service;

import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model.Doctor;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class DoctorService{
    private DoctorRepository doctorRepository;


    @Autowired
    public DoctorService(DoctorRepository doctorRepository){
        this.doctorRepository=doctorRepository;
    }

//    OTHER CRUD OPERATION METHOD BELOW

//    Get all doctors
    public List<Doctor> allDoctor(){
        return doctorRepository.findAll();
    }

//    get one customer
    public Optional<Doctor> oneDoctor(Long id){

        return doctorRepository.findById(id);
    }

//    Update customer
    public void updateDoctor(Long id, Doctor doctor){
        Optional<Doctor> optionalDoctored = doctorRepository.findById(id);
        Doctor updatedDoctor;
        if(optionalDoctored.isPresent()){
            updatedDoctor=optionalDoctored.get();
            updatedDoctor.setId(id);
            updatedDoctor.setDoctorNumber(doctor.getDoctorNumber());
            updatedDoctor.setName(doctor.getName());
            updatedDoctor.setDob(doctor.getDob());
            updatedDoctor.setAddress(doctor.getAddress());
            updatedDoctor.setEmail(doctor.getEmail());
            updatedDoctor.setPassword(doctor.getPassword());
            updatedDoctor.setIdNo(doctor.getIdNo());
            updatedDoctor.setTelephone(doctor.getTelephone());
            doctorRepository.save(updatedDoctor);
        }

    }

//    create a doctor
    public void newDoctor(Doctor doctor){
        doctorRepository.save(doctor);
    }

//delete a doctor
    public void deleteDoctor(Long id){
        doctorRepository.deleteById(id);
    }

}
