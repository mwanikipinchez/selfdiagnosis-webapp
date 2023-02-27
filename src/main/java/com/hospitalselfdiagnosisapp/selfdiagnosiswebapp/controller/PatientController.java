package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.controller;


import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model.Patient;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/patient")
public class PatientController {

//    @GetMapping("/patient")
//    public String patient() {
//        return "This is patient controller";
//    }


    @GetMapping("/register")
    public String registrationForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "signup";
    }

    @PostMapping("/register")
    public String save(Patient patient){

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
}