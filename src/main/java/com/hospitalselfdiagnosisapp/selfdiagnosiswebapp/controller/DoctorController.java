package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class DoctorController {

    @GetMapping("/doctor")
    public String doctor(){
        return "This is doctors controller";
    }
}
