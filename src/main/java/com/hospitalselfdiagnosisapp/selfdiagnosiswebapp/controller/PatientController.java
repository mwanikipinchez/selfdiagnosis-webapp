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
public class PatientController {
    private PatientService patientService;


    @Autowired
    public PatientController(PatientService patientService){

        this.patientService = patientService;

    }

    @GetMapping("/")
    public String home(){
        return "index";
    }

    @GetMapping("/login")
    public String login(){

        return "login";
    }


    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        // create model object to store form data
        PatientDTO patient = new PatientDTO();
        model.addAttribute("patient", patient);
        return "PatientSignup.html";
    }

    @PostMapping("/register/save")
    public String registration( @ModelAttribute("patient") @Valid PatientDTO patientDTO,
                               BindingResult result,
                               Model model){
        Patient existingUser = patientService.findPatientByEmail(patientDTO.getEmail());

        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if(result.hasErrors()){
            model.addAttribute("patient", patientDTO);
            return "/register";
        }

        patientService.save(patientDTO);
        return "redirect:/register?success";
    }

    @GetMapping("/patients")
    public String users(Model model){
        List<PatientDTO> patients = patientService.findAllUsers();
        model.addAttribute("patients", patients);
        return "users-list.html";
    }

    @GetMapping("/home")
    public String patientLandingPage(){

        return "patientLandingPage.html";
    }

    @GetMapping("/accessDenied")
    public String accessDenied(){
        return "403";
    }


}




























//@GetMapping("/index")
//    public String home(){
//        return "index";
//    }
//    @GetMapping("/login")
//    public String login(){
//        return "login";
//    }
//
//
//    @GetMapping("/register")
//    public String registrationForm(Model model){
//        PatientDTO patientDTO = new PatientDTO();
//        model.addAttribute("patient", patientDTO);
//        return "PatientSignup";
//    }
//
//@PostMapping("/save")
//public String registration(@Valid @ModelAttribute("patient") PatientDTO patientDTO,
//                           BindingResult result, Model model){
//    Patient existingPatient = patientService.findByEmail(patientDTO.getEmail());
//
//    if(existingPatient != null && existingPatient.getEmail() != null && !existingPatient.getEmail().isEmpty()){
//        result.rejectValue("email", null,
//                "There is already an account registered with the same email");
//    }
//
//
//    if(result.hasErrors()){
//        model.addAttribute("patient", patientDTO);
//        return "PatientSignup";
//    }
//
//    model.addAttribute("patient", patientDTO);
//    patientService.save(patientDTO);
//    return "redirect:/register?success";
//}
//
//
//
//    @GetMapping("/home")
//    public String landingPage(Model model){
//
//        return "patientLandingPage";
//    }
//
//    @GetMapping("/list")
//    public String allPatients(Model model){
//        List<PatientDTO> patients = patientService.findAllUsers();
//        model.addAttribute("patients", patients);
//        return "users-list";
//
//    }