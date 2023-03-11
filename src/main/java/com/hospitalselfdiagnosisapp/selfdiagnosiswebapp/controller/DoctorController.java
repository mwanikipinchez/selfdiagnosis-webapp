package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.controller;


import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.dto.DoctorDTO;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.dto.PatientDTO;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model.Doctor;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model.Patient;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.service.DoctorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/doctor")

public class DoctorController {

    private DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService){
        this.doctorService = doctorService;
    }

//    @ModelAttribute("doctor")
//    public DoctorDTO doctorRegistration(){
//        return new DoctorDTO();
//    }
//
//    Registration form
    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        DoctorDTO doctor = new DoctorDTO();
        model.addAttribute("doctor", doctor);
        return "doctorSignup";

    }

    @PostMapping("/save")
    public String registration(@Valid @ModelAttribute("doctor") DoctorDTO doctorDTO,
                               BindingResult result, Model model){
        Doctor existingDoctor = doctorService.findByEmail(doctorDTO.getEmail());

        if(existingDoctor != null && existingDoctor.getEmail() != null && !existingDoctor.getEmail().isEmpty()){
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if(result.hasErrors()){
            model.addAttribute("doctor", doctorDTO);
            return "PatientSignup";
        }

        model.addAttribute("doctor", doctorDTO);
        doctorService.save(doctorDTO);
        return "redirect:/home";
    }


    @GetMapping("/home")
    public String doctorHome(){
        return "doctorLandingPage";
    }






}
