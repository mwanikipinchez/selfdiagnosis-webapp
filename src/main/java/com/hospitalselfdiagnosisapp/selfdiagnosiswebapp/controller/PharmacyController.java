package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.controller;


//import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.dto.DoctorDTO;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.dto.PharmacyDTO;
//import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model.Doctor;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model.Pharmacy;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.service.PharmacyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/pharmacy")
public class PharmacyController {
    PharmacyService pharmacyService;

    @Autowired
    public PharmacyController(PharmacyService pharmacyService){
        this.pharmacyService=pharmacyService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        PharmacyDTO pharmacyDTO = new PharmacyDTO();
        model.addAttribute("pharmacy", pharmacyDTO);
        return "pharmacySignup";
    }

    @PostMapping("/save")
    public String registration(@Valid @ModelAttribute("pharmacy") PharmacyDTO pharmacyDTO,
                               BindingResult result, Model model){
       Pharmacy existingPharmacy = pharmacyService.findByEmail(pharmacyDTO.getEmail());

        if(existingPharmacy != null && existingPharmacy.getEmail() != null && !existingPharmacy.getEmail().isEmpty()){
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if(result.hasErrors()){
            model.addAttribute("pharmacy", pharmacyDTO);
            return "pharmacySignup";
        }

        model.addAttribute("pharmacy", pharmacyDTO);
        pharmacyService.save(pharmacyDTO);
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home(){
        return "pharmacyLandingPage";
    }
}
