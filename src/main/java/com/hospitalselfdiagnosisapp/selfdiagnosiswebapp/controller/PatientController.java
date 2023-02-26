package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PatientController {

    @GetMapping("/patient")
    public String patient(){
        return "This is patient controller";
    }
}
