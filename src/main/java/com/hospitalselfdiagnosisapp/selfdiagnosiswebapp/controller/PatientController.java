package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.dto.AppointmentDTO;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.dto.DoctorDTO;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.dto.PatientDTO;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.dto.PharmacyDTO;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model.*;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.repository.InfermedicaSymptomsRepository;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.repository.MessageRepository;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.repository.PharmacyProductsRepository;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.repository.PharmacyRepository;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.service.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class PatientController {
    private final String infermedicaUrl = "https://api.infermedica.com/v3/diagnosis";


    private RestTemplate restTemplate;
    private PatientService patientService;
    private DoctorService doctorService;
    PharmacyService pharmacyService;
    TodoService todoService;
    AppointmentService appointmentService;
    MessageService messageService;
    InfermedicaSymptomsService infermedicaSymptomsService;

    PharmacyProductsService pharmacyProductsService;
    @Value("${infermedica.app-id}")
    private String appId;
    @Value("${infermedica.app-key}")
    private String appKey;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private PharmacyRepository pharmacyRepository;
    @Autowired
    private PharmacyProductsRepository pharmacyProductsRepository;
    @Autowired
    private InfermedicaSymptomsRepository infermedicaSymptomsRepository;


    @Autowired
    public PatientController(InfermedicaSymptomsService infermedicaSymptomsService, PharmacyProductsService pharmacyProductsService, RestTemplate restTemplate, MessageService messageService, PatientService patientService, DoctorService doctorService, PharmacyService pharmacyService, TodoService todoService, AppointmentService appointmentService) {
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.pharmacyService = pharmacyService;
        this.todoService = todoService;
        this.appointmentService = appointmentService;
        this.messageService = messageService;
        this.restTemplate = restTemplate;
        this.pharmacyProductsService = pharmacyProductsService;
        this.infermedicaSymptomsService = infermedicaSymptomsService;
    }


    @GetMapping("/")
    public String home(Model model) {
        SymptomSearch symptom = new SymptomSearch();
        model.addAttribute("symptom", symptom);
        Messages messages = new Messages();
        model.addAttribute("messages", messages);
        return "index";
    }

    @GetMapping("/login")
    public String login() {

        return "login";
    }

    @GetMapping("/pharmacyindex")
    public String pharmacyIndex() {
        return "pharmacyindex";
    }

    @GetMapping("/doctorindex")
    public String doctorIndex() {
        return "doctor";
    }

    @GetMapping("/services")
    public String service() {
        return "services";
    }

    @GetMapping("/projects")
    public String project() {
        return "projects";
    }

    @GetMapping("/pricing")
    public String pricing() {
        return "pricing";
    }

    @GetMapping("/contacts")
    public String contacts(Model model) {
        Messages messages = new Messages();
        model.addAttribute("messages", messages);
        return "contacts";
    }

    @GetMapping("/doctor")
    public String doctor() {
        return "doctors";
    }

    @GetMapping("/pharmacuticals")
    public String pharmacy() {
        return "pharmacy";
    }


    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        // create model object to store form data
        PatientDTO patient = new PatientDTO();
        model.addAttribute("patient", patient);
        return "signup";
    }

    @PostMapping("/register/save")
    public String registration(@ModelAttribute("patient") @Valid PatientDTO patientDTO,
                               BindingResult result,
                               Model model) {
        Patient existingUser = patientService.findPatientByEmail(patientDTO.getEmail());

        if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if (result.hasErrors()) {
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
    public String patientLandingPage(Model model, Authentication authentication) {
        String username = authentication.getName();
        List<Todo> todos = todoService.userTodos(username);
        model.addAttribute("todos", todos);

        return "dashboard";
    }

    //    diagnosis view
    @GetMapping("/diagnosisform")
    public String diagnosisForm() {
        return "diagnosis";
    }

    //    reset password view
    @GetMapping("/resetPassword")
    public String resetPassword() {
        return "forgot-password";
    }

    //    Calling appointment view
    @GetMapping("/appointment")
    public String appointment(Model model, Authentication authentication) {
        String username = authentication.getName();
        List<Appointment> allAppointment = appointmentService.findByPatientEmail(username);
        model.addAttribute("appointments", allAppointment);
        return "appointment";
    }

    //    Profile view
    @GetMapping("/profile")
    public String updateProfile() {

        return "profile";
    }

    //    pharmacy near you
    @GetMapping("/nearpharmacy")
    public String pharmacyNear(Model model, Authentication authentication) {
        String username = authentication.getName();
        Patient patient = patientService.findPatientByEmail(username);
        List<Pharmacy> pharmacies = pharmacyService.nearPharmacy(patient.getAddress());
        model.addAttribute("pharmacies", pharmacies);
        return "pharmacy";
    }

    @GetMapping("/accessDenied")
    public String accessDenied() {
        return "403";
    }


    //    Doctors coner
// ####################################################################################################################################################
    @GetMapping("/dregister")
    public String showDoctorRegistrationForm(Model model) {
        DoctorDTO doctor = new DoctorDTO();
        model.addAttribute("doctor", doctor);
        return "dsignup";

    }

    @PostMapping("/dsave")
    public String registration(@Valid @ModelAttribute("doctor") @RequestBody DoctorDTO doctor,
                               BindingResult result, Model model) {
        Doctor existingDoctor = doctorService.findDoctorByEmail(doctor.getEmail());

        if (existingDoctor != null && existingDoctor.getEmail() != null && !existingDoctor.getEmail().isEmpty()) {
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if (result.hasErrors()) {
            model.addAttribute("doctor", doctor);
            return "dsignup";
        }

        model.addAttribute("doctor", doctor);
        doctorService.saveDoctor(doctor);
//        Twillio newRegistration = new Twillio();
//        newRegistration.sendMessage();
        return "redirect:/ddashboard";
    }


//    appointments

    @GetMapping("/dappointment")
    public String dappointment(Model model, Authentication authentication) {
        String username = authentication.getName();
        List<Appointment> doctorAppointments = appointmentService.findByDoctorEmail(username);
        model.addAttribute("doctorappointments", doctorAppointments);

        return "dappointment";
    }

    //    doctor dashboard
    @GetMapping("/ddashboard")
    public String ddashboard(Model model, Authentication authentication) {
        String username = authentication.getName();
        List<Todo> todos = todoService.userTodos(username);
        model.addAttribute("todos", todos);

        return "ddashboard";
    }

//    doctor pharmacy recommendation

    @GetMapping("/dpharmacy")
    public String dpharmacy() {

        return "dpharmacy";
    }


    //    Doctor profile
    @GetMapping("/dprofile")
    public String dprofile() {

        return "dprofile";
    }


    //    #############################################################################@####################################
//    Pharmacy
    @GetMapping("/pregister")
    public String showPharmacyRegistrationForm(Model model) {
        PharmacyDTO pharmacyDTO = new PharmacyDTO();
        model.addAttribute("pharmacy", pharmacyDTO);
        return "psignup";
    }

    @PostMapping("/psave")
    public String registration(@Valid @ModelAttribute("pharmacy") PharmacyDTO pharmacyDTO,
                               BindingResult result, Model model) {
        Pharmacy existingPharmacy = pharmacyService.findPharmacyByEmail(pharmacyDTO.getEmail());

        if (existingPharmacy != null && existingPharmacy.getEmail() != null && !existingPharmacy.getEmail().isEmpty()) {
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if (result.hasErrors()) {
            model.addAttribute("pharmacy", pharmacyDTO);
            return "psignup";
        }

        model.addAttribute("pharmacy", pharmacyDTO);
        pharmacyService.save(pharmacyDTO);
//        Twillio newRegistration = new Twillio();
//        newRegistration.sendMessage();
        return "redirect:/pdashboard";
    }


    //    pharmacy dashboard
    @GetMapping("/pdashboard")
    public String pdashboard(Model model, Authentication authentication) {
        String username = authentication.getName();
        List<Todo> todos = todoService.userTodos(username);
        model.addAttribute("todos", todos);

        return "pdashboard";
    }

    //    pharmacy profile
    @GetMapping("/pprofile")
    public String pprofile() {

        return "pprofile";
    }

    //pharmacy orders
    @GetMapping("/porder")
    public String orders() {

        return "order";
    }


    //Pharmacy todolist
    @PostMapping("/pharmacytodo")
    public String pharmacyTodo(@RequestParam String user, @RequestParam String activity, @RequestParam LocalTime time) {
        Todo todo = new Todo();
        todo.setUser(user);
        todo.setActivity(activity);
        todo.setTime(time);
        todoService.save(todo);

        return "redirect:/pdashboard";


    }

    //doctor todolist
    @GetMapping("/doctortodoform")
    public String doctorform(Model model) {
        Todo todo = new Todo();
        model.addAttribute("todo", todo);
        return "doctortodoform";
    }

    //doctor todolist saving
    @PostMapping("/doctortodo")
    public String doctorTodo(@RequestParam String user, @RequestParam String activity, @RequestParam LocalTime time) {
        Todo todo = new Todo();
        todo.setUser(user);
        todo.setActivity(activity);
        todo.setTime(time);
        todoService.save(todo);

        return "redirect:/ddashboard";


    }

    //pharmacytodo form
    @GetMapping("/pharmacytodoform")
    public String pharmacyform(Model model) {
        Todo todo = new Todo();
        model.addAttribute("todo", todo);
        return "pharmacytodoform";
    }

    @GetMapping("/pharmacyproducts")
    public String pharmacyProducts(Model model, Authentication authentication) {
        String pharmacy = authentication.getName();
        List<PharmacyProducts> myProducts = pharmacyProductsRepository.findByPharmacy(pharmacy);
        model.addAttribute("myProducts", myProducts);


        return "pharmacyproducts";
    }

    @PostMapping("/addpharmacyproducts")
    public String newProduct(@RequestParam String pharmacy, @RequestParam String name, @RequestParam int quantity, @RequestParam int unitPrice) {
        PharmacyProducts product = new PharmacyProducts();
        product.setName(name);
        product.setPharmacy(pharmacy);
        product.setUnitPrice(unitPrice);
        product.setQuantity(quantity);
        pharmacyProductsService.newPharmacyProduct(product);

        return "redirect:/pharmacyproducts";

    }


//
//##########################//Todolist center##########################################################

    @GetMapping("/todoform")
    public String todoform(Model model) {
        Todo todo = new Todo();
        model.addAttribute("todo", todo);
        return "todoform";
    }

    //    Add a todolist in the database
    @PostMapping("/addtodo")
    public String addTodo(@RequestParam String user, @RequestParam String activity, @RequestParam LocalTime time) {
        Todo todo = new Todo();
        todo.setUser(user);
        todo.setActivity(activity);
        todo.setTime(time);
        todoService.save(todo);

        return "redirect:/dashboard";


    }

    //    get the todolist
    @GetMapping("/todos")
    public String todos(Model model, @RequestParam String user) {
        List<Todo> alltodos = todoService.userTodos(user);
//       System.out.println(alltodos);
        model.addAttribute("todos", alltodos);
        return "redirect:/dashboard";

    }


    //    ##############@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@##############################################
//    Appointment corner
    @GetMapping("/appointmentform")
    public String appointmentForm(Model model) {
        List<Doctor> doctors = doctorService.findAllDoctors();
        AppointmentDTO appointmentDTO = new AppointmentDTO();

        model.addAttribute("doctors", doctors);
        model.addAttribute("appointment", appointmentDTO);

        return "apointmentform";
    }

    @PostMapping("/requestappointment")
    public String requestAppointment(@RequestBody AppointmentDTO appointmentDTO) {
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
    public String requestapp(@RequestParam String title, @RequestParam String doctorEmail, @RequestParam String patientEmail, @RequestParam String appointmentDate) {
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
    public List<Messages> findAllMessages() {
        return messageService.allMessage();

    }

    @GetMapping("/messages/{id}")
    public Optional<Messages> searchMessage(@PathVariable Long id) {
        return messageService.searchMessage(id);

    }

    @PostMapping("/messagesave")
    public String addMessage(@RequestParam String name, @RequestParam String email, @RequestParam String message) {
        Messages messages = new Messages();
        messages.setMessage(message);
        messages.setName(name);
        messages.setEmail(email);
        messageService.newMessage(messages);

        return "redirect:/contacts";
    }

    @DeleteMapping("/message/{id}")
    public void deleteMessage(@PathVariable Long id) {
        messageService.deleteMessage(id);
    }


    //    ###########################################@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@###########################################
//    admin dashboard
    @GetMapping("/admindashboard")
    public String adminDashboard(Model model, Authentication authentication) {
        String username = authentication.getName();
        List<Todo> todos = todoService.userTodos(username);
        model.addAttribute("todos", todos);

        return "adminpanel";
    }


    //    adminprofile
    @GetMapping("/adminprofile")
    public String adminProfile() {

        return "adminprofile";
    }


    //    All appointments
    @GetMapping("/adminappointment")
    public String allAppointment(Model model) {
        List<Appointment> allAppointment = appointmentService.findAllAppointments();
        model.addAttribute("appointments", allAppointment);
        return "adminappointments";

    }

    //    allPatients
    @GetMapping("/adminpatients")
    public String users(Model model) {
        List<PatientDTO> patients = patientService.findAllUsers();
        model.addAttribute("patients", patients);
        return "adminPatients";
    }


    //all pharmacy
    @GetMapping("/adminpharmacy")
    public String pharmacy(Model model) {
        List<Pharmacy> pharmacy = pharmacyService.findAllPharmacy();
        model.addAttribute("pharmacys", pharmacy);
        return "adminPharmacy";
    }


    //All doctors
    @GetMapping("/admindoctors")
    public String doctors(Model model) {
        List<Doctor> doctors = doctorService.findAllDoctors();
        model.addAttribute("doctors", doctors);
        return "adminDoctors";
    }

    //All Todos
    @GetMapping("/admintodolist")
    public String todolist(Model model) {
        List<Todo> allTodos = todoService.allTodo();
        model.addAttribute("todos", allTodos);
        return "adminTodoList";

    }

    //    All Messages
    @GetMapping("/adminmessages")
    public String messageList(Model model) {
        List<Messages> allMessage = messageService.allMessage();
        model.addAttribute("messages", allMessage);
        return "adminMessages";

    }

    @PostMapping("/admintodo")
    public String adminTodo(@RequestParam String user, @RequestParam String activity, @RequestParam LocalTime time) {
        Todo todo = new Todo();
        todo.setUser(user);
        todo.setActivity(activity);
        todo.setTime(time);
        todoService.save(todo);

        return "redirect:/admindashboard";
    }

    @GetMapping("/admintodoform")
    public String adminTodoForm(Model model) {
        Todo todo = new Todo();
        model.addAttribute("todo", todo);
        return "admintodoform";
    }


//    ########################################################DIAGNOSIS END ######################################################

    @GetMapping("/showDiagnosisForm")
    public String showDiagnosisForm(Model model) {

        return "mainDiagnosisForm";
    }


//    Calling /diagnosis endpoint of infermedica api
    @PostMapping("/mainDiagnosis")
    public String infermedicaDiagnosis(@RequestParam int age, @RequestParam String gender, @RequestParam String symptom1, @RequestParam String symptom2, @RequestParam String symptom3, Model model) throws JsonProcessingException {
          String id1, id2, id3;
        InfermedicaSymptoms infermedicaSymptom1 = infermedicaSymptomsRepository.findByName(symptom1);
        InfermedicaSymptoms infermedicaSymptom2 = infermedicaSymptomsRepository.findByName(symptom2);
        InfermedicaSymptoms infermedicaSymptom3 = infermedicaSymptomsRepository.findByName(symptom3);

//Headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("App-Id", appId);
        headers.set("App-Key", appKey);

         if(infermedicaSymptom1 != null && infermedicaSymptom2 != null && infermedicaSymptom3 != null){
             model.addAttribute("id3", infermedicaSymptom3.getId());
             model.addAttribute("id2", infermedicaSymptom2.getId());
             model.addAttribute("id1", infermedicaSymptom1.getId());

//   API call

             String jsonBody = "{\"sex\":\"" + gender + "\",\"age\":{\"value\":"  + age + "},\"evidence\":["
                     + "{\"id\":\"" + infermedicaSymptom1.getId() + "\",\"choice_id\":\"present\"},"
                     + "{\"id\":\"" + infermedicaSymptom2.getId() + "\",\"choice_id\":\"present\"},"
                     + "{\"id\":\"" + infermedicaSymptom3.getId() + "\",\"choice_id\":\"present\"}"
                     + "]}";
           //  \"age\":{\"value\":"  + age + "}


             HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);

             ResponseEntity<String> response = restTemplate.postForEntity(
                     infermedicaUrl,
                     request,
                     String.class
             );


//             System.out.println(response.getBody());

             // Extract the conditions part from the response
             ObjectMapper objectMapper = new ObjectMapper();
             JsonNode rootNode = objectMapper.readTree(response.getBody());

             List<Condition> conditions = new ArrayList<>();
             for (JsonNode conditionNode : rootNode.get("conditions")) {
                 Condition condition = objectMapper.treeToValue(conditionNode, Condition.class);
                 conditions.add(condition);
             }
             model.addAttribute("conditions", conditions);


//             JsonNode conditionsNode = rootNode.get("conditions");
//
//             // Loop through the conditions and access their properties
//             for (JsonNode condition : conditionsNode) {
//                 String id = condition.get("id").asText();
//                 String name = condition.get("name").asText();
//                 String commonName = condition.get("common_name").asText();
//                 double probability = condition.get("probability").asDouble();
//                 System.out.println("Condition ID: " + id);
//                 System.out.println("Condition name: " + name);
//                 System.out.println("Condition common name: " + commonName);
//                 System.out.println("Condition probability: " + probability);
//             }




//
         } else if(infermedicaSymptom1 != null && infermedicaSymptom2 != null && infermedicaSymptom3 == null){
           //  model.addAttribute("id3", infermedicaSymptom3);
             model.addAttribute("id2", infermedicaSymptom2.getId());
             model.addAttribute("id1", infermedicaSymptom1.getId());

//             String jsonBody = "{\"sex\":\"" + gender + "\",\"age\":{\"value\":"  + age + "}\",\"evidence\":["
//                     + "{\"id\":\"" + infermedicaSymptom1.getId() + "\",\"choice_id\":\"present\"},"
//                     + "{\"id\":\"" + infermedicaSymptom2.getId() + "\",\"choice_id\":\"present\"}"
//                     + "]}";
             String jsonBody = "{\"sex\":\"" + gender + "\",\"age\":{\"value\":"  + age + "},\"evidence\":["
                     + "{\"id\":\"" + infermedicaSymptom1.getId() + "\",\"choice_id\":\"present\"},"
                     + "{\"id\":\"" + infermedicaSymptom2.getId() + "\",\"choice_id\":\"present\"}"
                     + "]}";

             HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);

             ResponseEntity<String> response = restTemplate.postForEntity(
                     infermedicaUrl,
                     request,
                     String.class
             );

             // Extract the conditions part from the response
             ObjectMapper objectMapper = new ObjectMapper();
             JsonNode rootNode = objectMapper.readTree(response.getBody());


             List<Condition> conditions = new ArrayList<>();
             for (JsonNode conditionNode : rootNode.get("conditions")) {
                 Condition condition = objectMapper.treeToValue(conditionNode, Condition.class);
                 conditions.add(condition);
             }
             model.addAttribute("conditions", conditions);

//             JsonNode conditionsNode = rootNode.get("conditions");
//
//            // Loop through the conditions and access their properties
//             for (JsonNode condition : conditionsNode) {
//                 String id = condition.get("id").asText();
//                 String name = condition.get("name").asText();
//                 String commonName = condition.get("common_name").asText();
//                 double probability = condition.get("probability").asDouble();
//                 System.out.println("Condition ID: " + id);
//                 System.out.println("Condition name: " + name);
//                 System.out.println("Condition common name: " + commonName);
//                 System.out.println("Condition probability: " + probability);
//             }


             //System.out.println(response.getBody());


         } else if(infermedicaSymptom1 != null && infermedicaSymptom2 == null && infermedicaSymptom3 != null){
             model.addAttribute("id3", infermedicaSymptom3.getId());
             //model.addAttribute("id2", infermedicaSymptom2);
             model.addAttribute("id1", infermedicaSymptom1.getId());

             String jsonBody = "{\"sex\":\"" + gender + "\",\"age\":{\"value\":"  + age + "},\"evidence\":["
                     + "{\"id\":\"" + infermedicaSymptom1.getId() + "\",\"choice_id\":\"present\"},"
                     + "{\"id\":\"" + infermedicaSymptom3.getId() + "\",\"choice_id\":\"present\"}"
                     + "]}";
             HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);

             ResponseEntity<String> response = restTemplate.postForEntity(
                     infermedicaUrl,
                     request,
                     String.class
             );

            // System.out.println(response.getBody());
             // Extract the conditions part from the response
             ObjectMapper objectMapper = new ObjectMapper();
             JsonNode rootNode = objectMapper.readTree(response.getBody());

             List<Condition> conditions = new ArrayList<>();
             for (JsonNode conditionNode : rootNode.get("conditions")) {
                 Condition condition = objectMapper.treeToValue(conditionNode, Condition.class);
                 conditions.add(condition);
             }
             model.addAttribute("conditions", conditions);

//             JsonNode conditionsNode = rootNode.get("conditions");
//
//             // Loop through the conditions and access their properties
//             for (JsonNode condition : conditionsNode) {
//                 String id = condition.get("id").asText();
//                 String name = condition.get("name").asText();
//                 String commonName = condition.get("common_name").asText();
//                 double probability = condition.get("probability").asDouble();
//                 System.out.println("Condition ID: " + id);
//                 System.out.println("Condition name: " + name);
//                 System.out.println("Condition common name: " + commonName);
//                 System.out.println("Condition probability: " + probability);
//             }
         } else if(infermedicaSymptom1 != null && infermedicaSymptom2 == null && infermedicaSymptom3 == null){
//            model.addAttribute("id3", infermedicaSymptom3);
//            model.addAttribute("id2", infermedicaSymptom2);
            model.addAttribute("id1", infermedicaSymptom1.getId());

             String jsonBody = "{\"sex\":\"" + gender + "\",\"age\":{\"value\":"  + age + "},\"evidence\":["
                     + "{\"id\":\"" + symptom1 + "\",\"choice_id\":\"present\"}"
                     + "]}";
             HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);

             ResponseEntity<String> response = restTemplate.postForEntity(
                     infermedicaUrl,
                     request,
                     String.class
             );

            // System.out.println(response.getBody());
             // Extract the conditions part from the response
             ObjectMapper objectMapper = new ObjectMapper();
             JsonNode rootNode = objectMapper.readTree(response.getBody());

             List<Condition> conditions = new ArrayList<>();
             for (JsonNode conditionNode : rootNode.get("conditions")) {
                 Condition condition = objectMapper.treeToValue(conditionNode, Condition.class);
                 conditions.add(condition);
             }
             model.addAttribute("conditions", conditions);

//             JsonNode conditionsNode = rootNode.get("conditions");
//
//             // Loop through the conditions and access their properties
//             for (JsonNode condition : conditionsNode) {
//                 String id = condition.get("id").asText();
//                 String name = condition.get("name").asText();
//                 String commonName = condition.get("common_name").asText();
//                 double probability = condition.get("probability").asDouble();
//                 System.out.println("Condition ID: " + id);
//                 System.out.println("Condition name: " + name);
//                 System.out.println("Condition common name: " + commonName);
//                 System.out.println("Condition probability: " + probability);
//             }

        } else if(infermedicaSymptom1 == null && infermedicaSymptom2 != null && infermedicaSymptom3 != null){
             model.addAttribute("id3", infermedicaSymptom3.getId());
             model.addAttribute("id2", infermedicaSymptom2.getId());

             String jsonBody = "{\"sex\":\"" + gender + "\",\"age\":{\"value\":"  + age + "},\"evidence\":["
                     + "{\"id\":\"" + infermedicaSymptom2.getId() + "\",\"choice_id\":\"present\"},"
                     + "{\"id\":\"" + infermedicaSymptom3.getId() + "\",\"choice_id\":\"present\"}"
                     + "]}";
             HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);

             ResponseEntity<String> response = restTemplate.postForEntity(
                     infermedicaUrl,
                     request,
                     String.class
             );

            // System.out.println(response.getBody());
             // Extract the conditions part from the response
             ObjectMapper objectMapper = new ObjectMapper();
             JsonNode rootNode = objectMapper.readTree(response.getBody());

             List<Condition> conditions = new ArrayList<>();
             for (JsonNode conditionNode : rootNode.get("conditions")) {
                 Condition condition = objectMapper.treeToValue(conditionNode, Condition.class);
                 conditions.add(condition);
             }
             model.addAttribute("conditions", conditions);


//             JsonNode conditionsNode = rootNode.get("conditions");
//
//             // Loop through the conditions and access their properties
//             for (JsonNode condition : conditionsNode) {
//                 String id = condition.get("id").asText();
//                 String name = condition.get("name").asText();
//                 String commonName = condition.get("common_name").asText();
//                 double probability = condition.get("probability").asDouble();
//                 System.out.println("Condition ID: " + id);
//                 System.out.println("Condition name: " + name);
//                 System.out.println("Condition common name: " + commonName);
//                 System.out.println("Condition probability: " + probability);
//             }
         } else if(infermedicaSymptom1 == null && infermedicaSymptom2 != null && infermedicaSymptom3 == null){

             model.addAttribute("id2", infermedicaSymptom2.getId());

             String jsonBody = "{\"sex\":\"" + gender + "\",\"age\":{\"value\":"  + age + "},\"evidence\":["
                     + "{\"id\":\"" + infermedicaSymptom2.getId() + "\",\"choice_id\":\"present\"}"
                     + "]}";
             HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);

             ResponseEntity<String> response = restTemplate.postForEntity(
                     infermedicaUrl,
                     request,
                     String.class
             );
             //System.out.println(response.getBody());
             // Extract the conditions part from the response
             ObjectMapper objectMapper = new ObjectMapper();
             JsonNode rootNode = objectMapper.readTree(response.getBody());

             List<Condition> conditions = new ArrayList<>();
             for (JsonNode conditionNode : rootNode.get("conditions")) {
                 Condition condition = objectMapper.treeToValue(conditionNode, Condition.class);
                 conditions.add(condition);
             }
             model.addAttribute("conditions", conditions);

//
//             JsonNode conditionsNode = rootNode.get("conditions");
//
//             // Loop through the conditions and access their properties
//             for (JsonNode condition : conditionsNode) {
//                 String id = condition.get("id").asText();
//                 String name = condition.get("name").asText();
//                 String commonName = condition.get("common_name").asText();
//                 double probability = condition.get("probability").asDouble();
//                 System.out.println("Condition ID: " + id);
//                 System.out.println("Condition name: " + name);
//                 System.out.println("Condition common name: " + commonName);
//                 System.out.println("Condition probability: " + probability);
//             }

         } else if(infermedicaSymptom1 == null && infermedicaSymptom2 == null && infermedicaSymptom3 != null){
             model.addAttribute("id3", infermedicaSymptom3.getId());

             String jsonBody = "{\"sex\":\"" + gender + "\",\"age\":{\"value\":"  + age + "},\"evidence\":["
                     + "{\"id\":\"" + infermedicaSymptom3.getId() + "\",\"choice_id\":\"present\"}"
                     + "]}";

             HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);

             ResponseEntity<String> response = restTemplate.postForEntity(
                     infermedicaUrl,
                     request,
                     String.class
             );
             //System.out.println(response.getBody());
             // Extract the conditions part from the response
             ObjectMapper objectMapper = new ObjectMapper();
             JsonNode rootNode = objectMapper.readTree(response.getBody());


             List<Condition> conditions = new ArrayList<>();
             for (JsonNode conditionNode : rootNode.get("conditions")) {
                 Condition condition = objectMapper.treeToValue(conditionNode, Condition.class);
                 conditions.add(condition);
             }
             model.addAttribute("conditions", conditions);

//             JsonNode conditionsNode = rootNode.get("conditions");
//             // Loop through the conditions and access their properties
//             for (JsonNode condition : conditionsNode) {
//                 String id = condition.get("id").asText();
//                 String name = condition.get("name").asText();
//                 String commonName = condition.get("common_name").asText();
//                 double probability = condition.get("probability").asDouble();
//                 System.out.println("Condition ID: " + id);
//                 System.out.println("Condition name: " + name);
//                 System.out.println("Condition common name: " + commonName);
//                 System.out.println("Condition probability: " + probability);
//             }

         } else {

             model.addAttribute("conditions", "We are unable to diagnose based on the symptoms you have given, give more specific symptoms");


         }





        return "infermedicaResults";

    }




//    Return symptoms to the view
    @GetMapping("/infermedicaSymptoms")
    public String infermedicaSymptoms(Model model){
       int pageNo = 1;
       int pageSize = 25;
        Page< InfermedicaSymptoms > page = infermedicaSymptomsService.findPaginated(pageNo, pageSize);
        List < InfermedicaSymptoms > listSymptoms = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listSymptoms", listSymptoms);

        return "infermedicaSymptoms";
    }


    //    getting all symptoms from the infermedica api
    @GetMapping("/infermedicaData")
    public String home() {
//        String url = "https://api.infermedica.com/v3/symptoms?age.value=25&age.unit=year";
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("App-Id", appId);
//        headers.set("App-Key", appKey);
//
//        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class, headers);
//        String responseBody = response.getBody();
//        JSONArray jsonArray = new JSONArray(responseBody);
//        for (int i = 0; i < jsonArray.length(); i++) {
//            JSONObject jsonObject = jsonArray.getJSONObject(i);
//
//            InfermedicaSymptoms infermedicaSymptoms = new InfermedicaSymptoms();
//            infermedicaSymptoms.setId(jsonObject.getString("id"));
//            infermedicaSymptoms.setName(jsonObject.getString("name"));
//            infermedicaSymptoms.setCommon_name(jsonObject.getString("common_name"));
//            infermedicaSymptoms.setSex_filter(jsonObject.getString("sex_filter"));
//            infermedicaSymptoms.setCategory(jsonObject.getString("category"));
//            infermedicaSymptoms.setSeriousness(jsonObject.getString("seriousness"));
////            infermedicaSymptoms.setExtras(jsonObject.getString("extras"));
////            infermedicaSymptoms.setImage_url(jsonObject.getString("image_url"));
////            infermedicaSymptoms.setImage_source(jsonObject.getString("image_source"));
////            infermedicaSymptoms.setParent_id(jsonObject.getString("parent_id"));
////            infermedicaSymptoms.setParent_relation(jsonObject.getString("parent_relation"));
//
//            infermedicaSymptomsService.save(infermedicaSymptoms);
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.infermedica.com/v3/symptoms?age.value=25&age.unit=year";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("App-Id", "91236c46");
        headers.set("App-Key", "dad90ce3dcd3f2381f7b38ba5b997db1");

        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange( "https://api.infermedica.com/v3/symptoms?age.value=25&age.unit=year",
                HttpMethod.GET,
                entity,
                String.class);

//        String responseBody = response.getBody();
//
//        JSONObject jsonObject = new JSONObject(responseBody);
//        JSONArray jsonArray = jsonObject.getJSONArray("data");
//
//        for (int i = 0; i < jsonArray.length(); i++) {
//            JSONObject symptomObject = jsonArray.getJSONObject(i);
//
//            InfermedicaSymptoms infermedicaSymptoms = new InfermedicaSymptoms();
//
//            infermedicaSymptoms.setId(symptomObject.getString("id"));
//            infermedicaSymptoms.setName(symptomObject.getString("name"));
//            infermedicaSymptoms.setCommon_name(symptomObject.optString("common_name"));
//            infermedicaSymptoms.setSex_filter(symptomObject.optString("sex_filter"));
//            infermedicaSymptoms.setCategory(symptomObject.optString("category"));
//            infermedicaSymptoms.setSeriousness(symptomObject.optString("seriousness"));
//            infermedicaSymptoms.setExtras(symptomObject.optString("extras"));
//            infermedicaSymptoms.setImage_url(symptomObject.optString("image_url"));
//            infermedicaSymptoms.setImage_source(symptomObject.optString("image_source"));
//            infermedicaSymptoms.setParent_id(symptomObject.optString("parent_id"));
//            infermedicaSymptoms.setParent_relation(symptomObject.optString("parent_relation"));
//
//            infermedicaSymptomsService.save(infermedicaSymptoms);


//        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class, headers);
        String responseBody = response.getBody();
        JSONArray jsonArray = new JSONArray(responseBody);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            InfermedicaSymptoms infermedicaSymptoms = new InfermedicaSymptoms();
            infermedicaSymptoms.setId(jsonObject.getString("id"));
            infermedicaSymptoms.setName(jsonObject.getString("name"));
            infermedicaSymptoms.setCommon_name(jsonObject.getString("common_name"));
            infermedicaSymptoms.setSex_filter(jsonObject.getString("sex_filter"));
            infermedicaSymptoms.setCategory(jsonObject.getString("category"));
            infermedicaSymptoms.setSeriousness(jsonObject.getString("seriousness"));
//            JSONObject jsonObject = new JSONObject(jsonString);
//            JSONObject extrasObject = jsonObject.getJSONObject("extras");
//            String customProperty = extrasObject.getString("custom_property");
//            infermedicaSymptoms.setExtras(jsonObject.getString("extras"));
//            infermedicaSymptoms.setImage_url(jsonObject.getString("image_url"));
//            infermedicaSymptoms.setImage_source(jsonObject.getString("image_source"));
//            infermedicaSymptoms.setParent_id(jsonObject.getString("parent_id"));
//            infermedicaSymptoms.setParent_relation(jsonObject.getString("parent_relation"));

            infermedicaSymptomsService.save(infermedicaSymptoms);
        }


        return "redirect:/";


    }



    //    Calling /diagnosis endpoint of infermedica api
    @PostMapping("/dashboardDiagnosis")
    public String infermedicadashboardDiagnosis(@RequestParam int age, @RequestParam String gender, @RequestParam String symptom1, @RequestParam String symptom2, @RequestParam String symptom3, Model model) throws JsonProcessingException {
        String id1, id2, id3;
        InfermedicaSymptoms infermedicaSymptom1 = infermedicaSymptomsRepository.findByName(symptom1);
        InfermedicaSymptoms infermedicaSymptom2 = infermedicaSymptomsRepository.findByName(symptom2);
        InfermedicaSymptoms infermedicaSymptom3 = infermedicaSymptomsRepository.findByName(symptom3);

//Headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("App-Id", appId);
        headers.set("App-Key", appKey);

        if(infermedicaSymptom1 != null && infermedicaSymptom2 != null && infermedicaSymptom3 != null){
//            model.addAttribute("id3", infermedicaSymptom3.getId());
//            model.addAttribute("id2", infermedicaSymptom2.getId());
//            model.addAttribute("id1", infermedicaSymptom1.getId());

//   API call

            String jsonBody = "{\"sex\":\"" + gender + "\",\"age\":{\"value\":"  + age + "},\"evidence\":["
                    + "{\"id\":\"" + infermedicaSymptom1.getId() + "\",\"choice_id\":\"present\"},"
                    + "{\"id\":\"" + infermedicaSymptom2.getId() + "\",\"choice_id\":\"present\"},"
                    + "{\"id\":\"" + infermedicaSymptom3.getId() + "\",\"choice_id\":\"present\"}"
                    + "]}";
            //  \"age\":{\"value\":"  + age + "}


            HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(
                    infermedicaUrl,
                    request,
                    String.class
            );


//             System.out.println(response.getBody());

            // Extract the conditions part from the response
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.getBody());

            List<Condition> conditions = new ArrayList<>();
            for (JsonNode conditionNode : rootNode.get("conditions")) {
                Condition condition = objectMapper.treeToValue(conditionNode, Condition.class);
                conditions.add(condition);
            }
            model.addAttribute("conditions", conditions);


//             JsonNode conditionsNode = rootNode.get("conditions");
//
//             // Loop through the conditions and access their properties
//             for (JsonNode condition : conditionsNode) {
//                 String id = condition.get("id").asText();
//                 String name = condition.get("name").asText();
//                 String commonName = condition.get("common_name").asText();
//                 double probability = condition.get("probability").asDouble();
//                 System.out.println("Condition ID: " + id);
//                 System.out.println("Condition name: " + name);
//                 System.out.println("Condition common name: " + commonName);
//                 System.out.println("Condition probability: " + probability);
//             }




//
        } else if(infermedicaSymptom1 != null && infermedicaSymptom2 != null && infermedicaSymptom3 == null){
            //  model.addAttribute("id3", infermedicaSymptom3);
            model.addAttribute("id2", infermedicaSymptom2.getId());
            model.addAttribute("id1", infermedicaSymptom1.getId());

//             String jsonBody = "{\"sex\":\"" + gender + "\",\"age\":{\"value\":"  + age + "}\",\"evidence\":["
//                     + "{\"id\":\"" + infermedicaSymptom1.getId() + "\",\"choice_id\":\"present\"},"
//                     + "{\"id\":\"" + infermedicaSymptom2.getId() + "\",\"choice_id\":\"present\"}"
//                     + "]}";
            String jsonBody = "{\"sex\":\"" + gender + "\",\"age\":{\"value\":"  + age + "},\"evidence\":["
                    + "{\"id\":\"" + infermedicaSymptom1.getId() + "\",\"choice_id\":\"present\"},"
                    + "{\"id\":\"" + infermedicaSymptom2.getId() + "\",\"choice_id\":\"present\"}"
                    + "]}";

            HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(
                    infermedicaUrl,
                    request,
                    String.class
            );

            // Extract the conditions part from the response
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.getBody());


            List<Condition> conditions = new ArrayList<>();
            for (JsonNode conditionNode : rootNode.get("conditions")) {
                Condition condition = objectMapper.treeToValue(conditionNode, Condition.class);
                conditions.add(condition);
            }
            model.addAttribute("conditions", conditions);

//             JsonNode conditionsNode = rootNode.get("conditions");
//
//            // Loop through the conditions and access their properties
//             for (JsonNode condition : conditionsNode) {
//                 String id = condition.get("id").asText();
//                 String name = condition.get("name").asText();
//                 String commonName = condition.get("common_name").asText();
//                 double probability = condition.get("probability").asDouble();
//                 System.out.println("Condition ID: " + id);
//                 System.out.println("Condition name: " + name);
//                 System.out.println("Condition common name: " + commonName);
//                 System.out.println("Condition probability: " + probability);
//             }


            //System.out.println(response.getBody());


        } else if(infermedicaSymptom1 != null && infermedicaSymptom2 == null && infermedicaSymptom3 != null){
            model.addAttribute("id3", infermedicaSymptom3.getId());
            //model.addAttribute("id2", infermedicaSymptom2);
            model.addAttribute("id1", infermedicaSymptom1.getId());

            String jsonBody = "{\"sex\":\"" + gender + "\",\"age\":{\"value\":"  + age + "},\"evidence\":["
                    + "{\"id\":\"" + infermedicaSymptom1.getId() + "\",\"choice_id\":\"present\"},"
                    + "{\"id\":\"" + infermedicaSymptom3.getId() + "\",\"choice_id\":\"present\"}"
                    + "]}";
            HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(
                    infermedicaUrl,
                    request,
                    String.class
            );

            // System.out.println(response.getBody());
            // Extract the conditions part from the response
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.getBody());

            List<Condition> conditions = new ArrayList<>();
            for (JsonNode conditionNode : rootNode.get("conditions")) {
                Condition condition = objectMapper.treeToValue(conditionNode, Condition.class);
                conditions.add(condition);
            }
            model.addAttribute("conditions", conditions);

//             JsonNode conditionsNode = rootNode.get("conditions");
//
//             // Loop through the conditions and access their properties
//             for (JsonNode condition : conditionsNode) {
//                 String id = condition.get("id").asText();
//                 String name = condition.get("name").asText();
//                 String commonName = condition.get("common_name").asText();
//                 double probability = condition.get("probability").asDouble();
//                 System.out.println("Condition ID: " + id);
//                 System.out.println("Condition name: " + name);
//                 System.out.println("Condition common name: " + commonName);
//                 System.out.println("Condition probability: " + probability);
//             }
        } else if(infermedicaSymptom1 != null && infermedicaSymptom2 == null && infermedicaSymptom3 == null){
//            model.addAttribute("id3", infermedicaSymptom3);
//            model.addAttribute("id2", infermedicaSymptom2);
            model.addAttribute("id1", infermedicaSymptom1.getId());

            String jsonBody = "{\"sex\":\"" + gender + "\",\"age\":{\"value\":"  + age + "},\"evidence\":["
                    + "{\"id\":\"" + symptom1 + "\",\"choice_id\":\"present\"}"
                    + "]}";
            HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(
                    infermedicaUrl,
                    request,
                    String.class
            );

            // System.out.println(response.getBody());
            // Extract the conditions part from the response
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.getBody());

            List<Condition> conditions = new ArrayList<>();
            for (JsonNode conditionNode : rootNode.get("conditions")) {
                Condition condition = objectMapper.treeToValue(conditionNode, Condition.class);
                conditions.add(condition);
            }
            model.addAttribute("conditions", conditions);

//             JsonNode conditionsNode = rootNode.get("conditions");
//
//             // Loop through the conditions and access their properties
//             for (JsonNode condition : conditionsNode) {
//                 String id = condition.get("id").asText();
//                 String name = condition.get("name").asText();
//                 String commonName = condition.get("common_name").asText();
//                 double probability = condition.get("probability").asDouble();
//                 System.out.println("Condition ID: " + id);
//                 System.out.println("Condition name: " + name);
//                 System.out.println("Condition common name: " + commonName);
//                 System.out.println("Condition probability: " + probability);
//             }

        } else if(infermedicaSymptom1 == null && infermedicaSymptom2 != null && infermedicaSymptom3 != null){
            model.addAttribute("id3", infermedicaSymptom3.getId());
            model.addAttribute("id2", infermedicaSymptom2.getId());

            String jsonBody = "{\"sex\":\"" + gender + "\",\"age\":{\"value\":"  + age + "},\"evidence\":["
                    + "{\"id\":\"" + infermedicaSymptom2.getId() + "\",\"choice_id\":\"present\"},"
                    + "{\"id\":\"" + infermedicaSymptom3.getId() + "\",\"choice_id\":\"present\"}"
                    + "]}";
            HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(
                    infermedicaUrl,
                    request,
                    String.class
            );

            // System.out.println(response.getBody());
            // Extract the conditions part from the response
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.getBody());

            List<Condition> conditions = new ArrayList<>();
            for (JsonNode conditionNode : rootNode.get("conditions")) {
                Condition condition = objectMapper.treeToValue(conditionNode, Condition.class);
                conditions.add(condition);
            }
            model.addAttribute("conditions", conditions);


//             JsonNode conditionsNode = rootNode.get("conditions");
//
//             // Loop through the conditions and access their properties
//             for (JsonNode condition : conditionsNode) {
//                 String id = condition.get("id").asText();
//                 String name = condition.get("name").asText();
//                 String commonName = condition.get("common_name").asText();
//                 double probability = condition.get("probability").asDouble();
//                 System.out.println("Condition ID: " + id);
//                 System.out.println("Condition name: " + name);
//                 System.out.println("Condition common name: " + commonName);
//                 System.out.println("Condition probability: " + probability);
//             }
        } else if(infermedicaSymptom1 == null && infermedicaSymptom2 != null && infermedicaSymptom3 == null){

            model.addAttribute("id2", infermedicaSymptom2.getId());

            String jsonBody = "{\"sex\":\"" + gender + "\",\"age\":{\"value\":"  + age + "},\"evidence\":["
                    + "{\"id\":\"" + infermedicaSymptom2.getId() + "\",\"choice_id\":\"present\"}"
                    + "]}";
            HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(
                    infermedicaUrl,
                    request,
                    String.class
            );
            //System.out.println(response.getBody());
            // Extract the conditions part from the response
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.getBody());

            List<Condition> conditions = new ArrayList<>();
            for (JsonNode conditionNode : rootNode.get("conditions")) {
                Condition condition = objectMapper.treeToValue(conditionNode, Condition.class);
                conditions.add(condition);
            }
            model.addAttribute("conditions", conditions);

//
//             JsonNode conditionsNode = rootNode.get("conditions");
//
//             // Loop through the conditions and access their properties
//             for (JsonNode condition : conditionsNode) {
//                 String id = condition.get("id").asText();
//                 String name = condition.get("name").asText();
//                 String commonName = condition.get("common_name").asText();
//                 double probability = condition.get("probability").asDouble();
//                 System.out.println("Condition ID: " + id);
//                 System.out.println("Condition name: " + name);
//                 System.out.println("Condition common name: " + commonName);
//                 System.out.println("Condition probability: " + probability);
//             }

        } else if(infermedicaSymptom1 == null && infermedicaSymptom2 == null && infermedicaSymptom3 != null){
            model.addAttribute("id3", infermedicaSymptom3.getId());

            String jsonBody = "{\"sex\":\"" + gender + "\",\"age\":{\"value\":"  + age + "},\"evidence\":["
                    + "{\"id\":\"" + infermedicaSymptom3.getId() + "\",\"choice_id\":\"present\"}"
                    + "]}";

            HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(
                    infermedicaUrl,
                    request,
                    String.class
            );
            //System.out.println(response.getBody());
            // Extract the conditions part from the response
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.getBody());


            List<Condition> conditions = new ArrayList<>();
            for (JsonNode conditionNode : rootNode.get("conditions")) {
                Condition condition = objectMapper.treeToValue(conditionNode, Condition.class);
                conditions.add(condition);
            }
            model.addAttribute("conditions", conditions);

//             JsonNode conditionsNode = rootNode.get("conditions");
//             // Loop through the conditions and access their properties
//             for (JsonNode condition : conditionsNode) {
//                 String id = condition.get("id").asText();
//                 String name = condition.get("name").asText();
//                 String commonName = condition.get("common_name").asText();
//                 double probability = condition.get("probability").asDouble();
//                 System.out.println("Condition ID: " + id);
//                 System.out.println("Condition name: " + name);
//                 System.out.println("Condition common name: " + commonName);
//                 System.out.println("Condition probability: " + probability);
//             }

        } else {

            model.addAttribute("conditions", "We are unable to diagnose based on the symptoms you have given, give more specific symptoms");


        }





        return "diagnosisDashboardResults";

    }


}


    //DIAGNOSIS END
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
//
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
//
//
//
//
//
//
//






















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