package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.controller;


import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.dto.AppointmentDTO;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.dto.DoctorDTO;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.dto.PatientDTO;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.dto.PharmacyDTO;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model.*;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.repository.MessageRepository;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Controller
public class PatientController {


    private RestTemplate restTemplate;
    private PatientService patientService;
    private DoctorService doctorService;
    PharmacyService pharmacyService;
    TodoService todoService;
    AppointmentService appointmentService;
    MessageService messageService;
    @Value("${infermedica.app-id}")
     private String appId;
   @Value("${infermedica.app-key}")
    private String appKey;
    @Autowired
    private MessageRepository messageRepository;


    @Autowired
    public PatientController(RestTemplate restTemplate,MessageService messageService,PatientService patientService, DoctorService doctorService, PharmacyService pharmacyService, TodoService todoService, AppointmentService appointmentService) {
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.pharmacyService = pharmacyService;
        this.todoService = todoService;
        this.appointmentService = appointmentService;
        this.messageService = messageService;
        this.restTemplate = restTemplate;
    }




    @GetMapping("/")
    public String home(Model model){
        SymptomSearch symptom = new SymptomSearch();
        model.addAttribute("symptom",symptom);
        Messages messages = new Messages();
        model.addAttribute("messages", messages);
        return "index";
    }

    @GetMapping("/login")
    public String login(){

        return "login";
    }

     @GetMapping("/pharmacyindex")
     public String pharmacyIndex(){
        return "pharmacyindex";
     }
     @GetMapping("/doctorindex")
     public String doctorIndex(){
        return "doctor";
     }
    @GetMapping("/services")
    public String service(){
        return "services";
    }

    @GetMapping("/projects")
    public String project(){
        return "projects";
    }

    @GetMapping("/pricing")
    public String pricing(){
        return "pricing";
    }

    @GetMapping("/contacts")
    public String contacts(Model model){
        Messages messages = new Messages();
        model.addAttribute("messages", messages);
        return "contacts";
    }

    @GetMapping("/doctor")
    public String doctor(){
        return "doctors";
    }

    @GetMapping("/pharmacuticals")
    public String pharmacy(){
        return "pharmacy";
    }



    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        // create model object to store form data
        PatientDTO patient = new PatientDTO();
        model.addAttribute("patient", patient);
        return "signup";
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

//        Twillio newRegistration = new Twillio();
//        newRegistration.sendMessage();
//        TestSendingWithSenderID message = new TestSendingWithSenderID();
//        message.adminSms();
//        String response = " Dear " + patientDTO.getName() + "\n" + "Thank you for registering with our online services";
//        message.clientSms(patientDTO.getPhone(),response);
        return "redirect:/register?success";
    }



    @GetMapping("/dashboard")
    public String patientLandingPage(Model model, Authentication authentication){
        String username = authentication.getName();
        List<Todo> todos = todoService.userTodos(username);
        model.addAttribute("todos", todos);

        return "dashboard";
    }

//    diagnosis view
    @GetMapping("/diagnosisform")
    public String diagnosisForm(){
        return "diagnosis";
    }

//    reset password view
    @GetMapping("/resetPassword")
    public String resetPassword(){
        return "forgot-password";
    }

//    Calling appointment view
    @GetMapping("/appointment")
    public String appointment(Model model, Authentication authentication){
       String username = authentication.getName();
       List<Appointment> allAppointment = appointmentService.findByPatientEmail(username);
       model.addAttribute("appointments", allAppointment);
        return "appointment";
    }

//    Profile view
    @GetMapping("/profile")
    public String updateProfile()
    {

        return "profile";
    }

//    pharmacy near you
    @GetMapping("/nearpharmacy")
    public String pharmacyNear(){
        return "pharmacy";
    }

    @GetMapping("/accessDenied")
    public String accessDenied(){
        return "403";
    }


//        //DIAGNOSIS END
//    @GetMapping("/diagnosis")
//    public String diagnosisForm(Model model){
//        DiagnosisRequest request = new DiagnosisRequest();
//        model.addAttribute("symptoms", request);
//        return "diagnosis-form";
//
//    }
//    @GetMapping("/getSymptoms")
//    public String getSymptoms(Model model){
//        String url = "https://api.infermedica.com/v3/concepts?types=symptom";
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("App-Id", "91236c46");
//        headers.set("App-Key", "dad90ce3dcd3f2381f7b38ba5b997db1");
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class,headers);
//        String responseBody = response.getBody();
//        model.addAttribute("result", responseBody);
//        return "symptoms";
//
//    }
//    @PostMapping("/diagnose")
//    public String diagnose( @RequestParam int age, @RequestParam String sex, @RequestParam List<String> symptomsIds, Model model){
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("App-Id", appId);
//        headers.set("App-Key",appKey);
//
//        DiagnosisRequest request = new DiagnosisRequest();
//        request.setSex("male");
//        request.setAge(30);
//        request.setEvidence(getEvidence(symptomsIds));
//
//        HttpEntity<DiagnosisRequest> entity = new HttpEntity<>(request, headers);
//
//        // Make the API call
//        ResponseEntity<DiagnosisResponse> response = restTemplate.postForEntity(
//                "https://api.infermedica.com/v3/diagnosis",
//                entity,
//                DiagnosisResponse.class);
//
//        // Process the API response and return the diagnosis result
//        DiagnosisResponse diagnosis = response.getBody();
//        String result = diagnosis.getConditionName();
//        model.addAttribute("result", result);
//        return "diagnosis-result";
//    }
//
//    private List<Evidence> getEvidence(List<String> symptoms) {
//        List<Evidence> evidenceList = new ArrayList<>();
//        for (String symptom : symptoms) {
//            Evidence evidence = new Evidence();
//            evidence.setId(symptom);
//            evidence.setChoiceid("present");
//            evidenceList.add(evidence);
//        }
//        return evidenceList;
//    }
//


//    Doctors coner
// ####################################################################################################################################################
@GetMapping("/dregister")
public String showDoctorRegistrationForm(Model model){
    DoctorDTO doctor = new DoctorDTO();
    model.addAttribute("doctor", doctor);
    return "dsignup";

}

    @PostMapping("/dsave")
    public String registration(@Valid @ModelAttribute("doctor") @RequestBody DoctorDTO doctor,
                               BindingResult result, Model model){
        Doctor existingDoctor = doctorService.findDoctorByEmail(doctor.getEmail());

        if(existingDoctor != null && existingDoctor.getEmail() != null && !existingDoctor.getEmail().isEmpty()){
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if(result.hasErrors()){
            model.addAttribute("doctor", doctor);
            return "dsignup";
        }

        model.addAttribute("doctor", doctor);
        doctorService.saveDoctor(doctor);
        Twillio newRegistration = new Twillio();
        newRegistration.sendMessage();
        return "redirect:/doctors";
    }



//    appointments

    @GetMapping("/dappointment")
    public String dappointment(){

        return "dappointment";
    }

//    doctor dashboard
    @GetMapping("/ddashboard")
    public String ddashboard(){

        return "ddashboard";
    }

//    doctor pharmacy recommendation

    @GetMapping("/dpharmacy")
    public String dpharmacy(){

        return "dpharmacy";
    }


//    Doctor profile
    @GetMapping("/dprofile")
    public String dprofile(){

        return "dprofile";
    }






//    #############################################################################@####################################
//    Pharmacy
@GetMapping("/pregister")
public String showPharmacyRegistrationForm(Model model){
    PharmacyDTO pharmacyDTO = new PharmacyDTO();
    model.addAttribute("pharmacy", pharmacyDTO);
    return "psignup";
}

    @PostMapping("/psave")
    public String registration(@Valid @ModelAttribute("pharmacy") PharmacyDTO pharmacyDTO,
                               BindingResult result, Model model){
        Pharmacy existingPharmacy = pharmacyService.findPharmacyByEmail(pharmacyDTO.getEmail());

        if(existingPharmacy != null && existingPharmacy.getEmail() != null && !existingPharmacy.getEmail().isEmpty()){
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if(result.hasErrors()){
            model.addAttribute("pharmacy", pharmacyDTO);
            return "psignup";
        }

        model.addAttribute("pharmacy", pharmacyDTO);
        pharmacyService.save(pharmacyDTO);
        Twillio newRegistration = new Twillio();
        newRegistration.sendMessage();
        return "redirect:/pharmacy";
    }



//    pharmacy dashboard
    @GetMapping ("/pdashboard")
    public String pdashboard(){

        return "pdashboard";
    }

//    pharmacy profile
@GetMapping ("/pprofile")
public String pprofile(){

    return "pprofile";
}

//pharmacy orders
@GetMapping ("/porder")
public String orders(){

    return "order";
}


//
//##########################//Todolist center##########################################################

    @GetMapping("/todoform")
    public String todoform(Model model){
        Todo todo = new Todo();
        model.addAttribute("todo", todo);
        return "todoform";
    }
//    Add a todolist in the database
    @PostMapping("/addtodo")
    public String addTodo(@RequestParam String user, @RequestParam String activity, @RequestParam LocalTime time){
       Todo todo = new Todo();
       todo.setUser(user);
       todo.setActivity(activity);
       todo.setTime(time);
       todoService.save(todo);

       return "redirect:/dashboard";


    }

//    get the todolist
    @GetMapping("/todos")
    public String todos(Model model, @RequestParam String user){
       List<Todo> alltodos = todoService.userTodos(user);
//       System.out.println(alltodos);
       model.addAttribute("todos", alltodos);
       return "redirect:/dashboard";

    }




//    ##############@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@##############################################
//    Appointment corner
    @GetMapping("/appointmentform")
    public String appointmentForm(Model model){
       List<Doctor> doctors = doctorService.findAllDoctors();
       AppointmentDTO appointmentDTO = new AppointmentDTO();

           model.addAttribute("doctors", doctors);
           model.addAttribute("appointment", appointmentDTO);

       return "apointmentform";
    }

    @PostMapping("/requestappointment")
    public String requestAppointment(@RequestBody AppointmentDTO appointmentDTO){
       Appointment appointment = new Appointment();
       appointment.setAppointmentDate(appointmentDTO.getAppointmentDate());
       appointment.setTitle(appointmentDTO.getTitle());
       appointment.setDoctorEmail(appointmentDTO.getDoctorEmail());
       appointment.setPatientEmail(appointmentDTO.getPatientEmail());
       appointment.setCreatedOn(LocalDateTime.now());

       appointmentService.save(appointment);

       return "redirect:/appointment";


    }
    @PostMapping("/requestapointment")
    public String requestapp(@RequestParam String title, @RequestParam String doctorEmail, @RequestParam String patientEmail, @RequestParam String appointmentDate){
       Appointment appointment = new Appointment();
       appointment.setAppointmentDate(LocalDateTime.parse(appointmentDate));
       appointment.setCreatedOn(LocalDateTime.now());
       appointment.setTitle(title);
       appointment.setPatientEmail(patientEmail);
       appointment.setDoctorEmail(doctorEmail);

       appointmentService.save(appointment);

       return "redirect:/appointment";

    }




//    #################################@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@##########################
//    Message repository



//    Get all Messages
    @GetMapping("/messages")
public List<Messages> findAllMessages(){
       return messageService.allMessage();

    }

    @GetMapping("/messages/{id}")
    public Optional<Messages> searchMessage(@PathVariable Long id){
      return messageService.searchMessage(id);

    }

    @PostMapping("/messagesave")
    public String addMessage(@RequestParam String name, @RequestParam String email, @RequestParam String message){
        Messages messages = new Messages();
        messages.setMessage(message);
        messages.setName(name);
        messages.setEmail(email);
        messageService.newMessage(messages);

        return "redirect:/contacts";
    }

    @DeleteMapping("/message/{id}")
    public void deleteMessage(@PathVariable Long id)
    {
        messageService.deleteMessage(id);
    }





//    ###########################################@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@###########################################
//    admin dashboard
    @GetMapping("/admindashboard")
    public String adminDashboard(Model model, Authentication authentication){
        String username = authentication.getName();
        List<Todo> todos = todoService.userTodos(username);
        model.addAttribute("todos", todos);

        return "adminpanel";
    }


//    adminprofile
    @GetMapping("/adminprofile")
    public String adminProfile(){

        return "adminprofile";
    }


//    All appointments
    @GetMapping("/adminappointment")
    public String allAppointment(Model model){
       List<Appointment> allAppointment = appointmentService.findAllAppointments();
       model.addAttribute("appointments", allAppointment);
       return "adminappointments";

    }

//    allPatients
@GetMapping("/adminpatients")
public String users(Model model){
    List<PatientDTO> patients = patientService.findAllUsers();
    model.addAttribute("patients", patients);
    return "adminPatients";
}



//all pharmacy
@GetMapping("/adminpharmacy")
public String pharmacy(Model model){
    List<Pharmacy> pharmacy = pharmacyService.findAllPharmacy();
    model.addAttribute("pharmacys", pharmacy);
    return "adminPharmacy";
}



//All doctors
@GetMapping("/admindoctors")
public String doctors(Model model){
    List<Doctor> doctors = doctorService.findAllDoctors();
    model.addAttribute("doctors", doctors);
    return "adminDoctors";
}

//All Todos
    @GetMapping("/admintodolist")
    public String todolist(Model model){
        List<Todo> allTodos = todoService.allTodo();
        model.addAttribute("todos", allTodos);
        return "adminTodoList";

    }

//    All Messages
@GetMapping("/adminmessages")
public String messageList(Model model){
    List<Messages> allMessage = messageService.allMessage();
    model.addAttribute("messages", allMessage);
    return "adminMessages";

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