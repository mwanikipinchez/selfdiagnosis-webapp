package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.controller;



import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Controller
public class DiagnosisController {

    private final String appId = "91236c46";
    private final String appKey = "dad90ce3dcd3f2381f7b38ba5b997db1";
    private final String infermedicaUrl = "https://api.infermedica.com/v3/";
    RestTemplate restTemplate = new RestTemplate();


    @GetMapping("/infermedica")
    public String symptoms(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("App-Id", appId);
        headers.set("App-Key", appKey);

        return "blank";
    }
    @GetMapping("/trial")
    public String dianose(){
        return "blank";
    }
    @PostMapping("/diagnose")
    public String diagnose(
            @RequestParam("age") String age,
            @RequestParam("sex") String sex,
            @RequestParam("symptoms") String symptoms
    ) {


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("App-Id", appId);
        headers.set("App-Key", appKey);

        String[] symptomList = symptoms.split(",");
        String jsonBody = "{\"sex\":\"" + sex + "\",\"age\":\"" + age + "\",\"evidence\":[";

        for (String symptomName : symptomList) {
            jsonBody += "{\"id\":\"" + symptomName + "\",\"choice_id\":\"present\"},";
        }

        jsonBody = jsonBody.substring(0, jsonBody.length() - 1);
        jsonBody += "]}";

        HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                infermedicaUrl + "diagnosis",
                request,
                String.class
        );
        System.out.println(response.getBody());

        return response.getBody();
    }
}
