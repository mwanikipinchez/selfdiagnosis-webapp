//<<<<<<< HEAD
package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.service;

import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.dto.DoctorDTO;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model.Doctor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

@Primary
public interface DoctorService {
    Doctor saveDoctor(DoctorDTO doctor);
    Doctor findDoctorByEmail(String email);

    List<Doctor> findAllDoctors();
}
//=======
//package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.service;
//
//import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.dto.DoctorDTO;
//import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model.Doctor;
//import org.springframework.security.core.userdetails.UserDetailsService;
//
//public interface DoctorService extends UserDetailsService {
//    Doctor save(DoctorDTO registrationDTO);
//    Doctor findByEmail(String email);
//}
//>>>>>>> master
