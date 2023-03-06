package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class DoctorController {

    @GetMapping("/index")
    public String index(){
        return "index.html";
    }
}
