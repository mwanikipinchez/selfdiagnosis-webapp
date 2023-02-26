package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PharmacyController {

    @GetMapping("/pharmacy")
    public String pharmacy(){
        return "This is pharmacy controller";
    }
}
