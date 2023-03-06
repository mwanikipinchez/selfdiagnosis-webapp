package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.controller;


import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model.Patient;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.repository.PatientRepository;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
//@RestController
@RequestMapping("/")
public class PatientController {
    private PatientService patientService;
    private final PatientRepository patientRepository;


    @Autowired
    public PatientController(PatientService patientService,
                             PatientRepository patientRepository){
        this.patientService = patientService;
        this.patientRepository = patientRepository;
    }

    @GetMapping("/")
    public String index() {
        return "index.html";
    }


    @GetMapping("/register")
    public String registrationForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "PatientSignup.html";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Patient patient){
        patientService.newPatient(patient);

        return "redirect:/home";
    }

    @GetMapping("/login")
    public String login(){
        return "login.html";
    }
    @GetMapping("/home")
    public String landingPage(Model model){
        Patient patient = new Patient();
        model.addAttribute("patient", patient);
        return "patientLandingPage.html";
    }

    @GetMapping("/patients")
    public String allPatients(Model model){
        List<Patient> patients = patientService.allPatient();
        model.addAttribute("patients", patients);
        return "users-list.html";

    }
}