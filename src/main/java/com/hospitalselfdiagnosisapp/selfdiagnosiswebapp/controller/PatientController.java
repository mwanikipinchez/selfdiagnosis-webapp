package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.controller;


import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model.Patient;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.repository.PatientRepository;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/patient")
    public String patient() {
        return "This is patient controller";
    }


    @GetMapping("/patient/register")
    public String registrationForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "PatientSignup.html";
    }

    @PostMapping("/patient/save")
    public String save(@ModelAttribute Patient patient){
        patientService.newPatient(patient);

        return "redirect:/home";
    }

    @GetMapping("/patient/login")
    public String login(){
        return "login.html";
    }
    @GetMapping("/patient/home")
    public String landingPage(Model model){
        model.addAttribute("patient", new Patient());
        return "patientLandingPage.html";
    }
}