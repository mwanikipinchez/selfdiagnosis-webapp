package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.controller;


//import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.dto.DoctorDTO;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.dto.PharmacyDTO;
//import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model.Doctor;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model.Pharmacy;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.repository.PharmacyRepository;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.service.PharmacyService;
import jakarta.validation.Valid;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller

public class PharmacyController {
    PharmacyService pharmacyService;
    private final PharmacyRepository pharmacyRepository;

    @Autowired
    public PharmacyController(PharmacyService pharmacyService,
                              PharmacyRepository pharmacyRepository){
        this.pharmacyService=pharmacyService;
        this.pharmacyRepository = pharmacyRepository;
    }

//    @GetMapping("/pharmacy/register")
//    public String showRegistrationForm(Model model){
//        PharmacyDTO pharmacyDTO = new PharmacyDTO();
//        model.addAttribute("pharmacy", pharmacyDTO);
//        return "pharmacySignup";
//    }

//    @PostMapping("/pharmacy/save")
//    public String registration(@Valid @ModelAttribute("pharmacy") PharmacyDTO pharmacyDTO,
//                               BindingResult result, Model model){
//       Pharmacy existingPharmacy = pharmacyService.findByEmail(pharmacyDTO.getEmail());
//
//        if(existingPharmacy != null && existingPharmacy.getEmail() != null && !existingPharmacy.getEmail().isEmpty()){
//            result.rejectValue("email", null,
//                    "There is already an account registered with the same email");
//        }
//
//        if(result.hasErrors()){
//            model.addAttribute("pharmacy", pharmacyDTO);
//            return "pharmacySignup";
//        }
//
//        model.addAttribute("pharmacy", pharmacyDTO);
//        pharmacyService.save(pharmacyDTO);
//        return "redirect:/pharmacy";
//    }

    @GetMapping("/pharmacy/login")
    public String login(){
        return "login";
    }

    @GetMapping("/pharmacy/home")
    public String home(){
        return "pharmacyLandingPage";
    }

//    @GetMapping("/pharmacy")
//    public String pharmacy(Model model){
//        List<Pharmacy> pharmacy = pharmacyService.findAllPharmacy();
//        model.addAttribute("pharmacys", pharmacy);
//        return "pharmacylist";
//    }
}
