package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.controller;


import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.dto.PatientDTO;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model.Patient;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.repository.PatientRepository;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.service.PatientService;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.service.PatientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

import java.util.List;

@Controller
//@RestController
@RequestMapping("/")
public class PatientController {
    private PatientService patientService;
    private PatientDTO patientDTO;




    @Autowired
    public PatientController(PatientService patientService){
        this.patientService = patientService;

    }

    @GetMapping("/")
    public String index() {
        return "index";
    }


//    @GetMapping("/register")
//    public String registrationForm(Model model) {
//        model.addAttribute("patient", new Patient());
//        return "PatientSignup";
//    }

    @GetMapping("/register")
    public String registrationForm(Model model){
        PatientDTO patient = new PatientDTO();
        model.addAttribute("patient", patient);
        return "PatientSignup";
    }

//    @PostMapping("/save")
//    public String save(@ModelAttribute Patient patient){
//        patientServiceImpl.newPatient(patient);
//
//        return "redirect:/home";
//    }
// handler method to handle user registration form submit request
@PostMapping("/save")
public String registration(@Valid @ModelAttribute("user") PatientDTO patientDTO, BindingResult result, Model model){
    Patient existingPatient = patientService.findByEmail(patientDTO.getEmail());

    if(existingPatient != null && existingPatient.getEmail() != null && !existingPatient.getEmail().isEmpty()){
        result.rejectValue("email", null,
                "There is already an account registered with the same email");
    }

    if(result.hasErrors()){
        model.addAttribute("patient", patientDTO);
        return "/register";
    }

    patientService.newPatient(patientDTO);
    return "redirect:/register?success";
}


    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @GetMapping("/home")
    public String landingPage(Model model){
        Patient patient = new Patient();
        model.addAttribute("patient", patient);
        return "patientLandingPage.html";
    }

    @GetMapping("/patients")
    public String allPatients(Model model){
        List<PatientDTO> patients = patientService.findAllUsers();
        model.addAttribute("patients", patients);
        return "users-list";

    }
}