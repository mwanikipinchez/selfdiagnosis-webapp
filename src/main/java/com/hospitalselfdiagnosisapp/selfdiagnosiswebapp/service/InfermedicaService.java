//package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.service;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.Arrays;
//import java.util.List;
//
//
//@Service
//public class InfermedicaService {
//
//
//    @Value("${infermedica.app-id}")
//     private String appId;
//    @Value("${infermedica.app-key}")
//     private String appKey;
//
//
//
//    @Autowired
//    private ObjectMapper objectMapper;
//    private static final String APIURL = "https://api.infermedica.com/v3/diagnosis";
//
//    private RestTemplate restTemplate = new RestTemplate();
//
////    public Evidence generateDiagnosis(String sex, int age, List<Evidence> evidence) throws Exception {
//////        DiagnosisRequest diagnosisRequest = new DiagnosisRequest(sex, age, evidence);
//////        String jsonRequest = objectMapper.writeValueAsString(diagnosisRequest);
//////        String jsonResponse = makeApiCall(jsonRequest, "diagnosis");
//////        DiagnosisResponse[] diagnosisResponses = objectMapper.readValue(jsonResponse, DiagnosisResponse[].class);
//////        List<DiagnosisResponse> diagnosisResponseList = Arrays.asList(diagnosisResponses);
//////        String conditionName = diagnosisResponseList.get(diagnosisResponseList.size() - 1).getConditionName();
//////        double probability = diagnosisResponseList.get(diagnosisResponseList.size() - 1).getProbability();
//////        List<String> advice = diagnosisResponseList.get(diagnosisResponseList.size() - 1).getManagement().getAdvice();
//////        Evidence diagnosis = new Diagnosis(conditionName, probability, advice);
//////        return diagnosis;
////
////
////
////    }
//
////    private String makeApiCall(String jsonRequest, String endpoint) {
////        RestTemplate restTemplate = new RestTemplate();
////        String url = apiUrl + endpoint;
////        return restTemplate.postForObject(url, jsonRequest, String.class);
////    }
////        // create headers with authorization info
////        HttpHeaders headers = new HttpHeaders();
////        headers.set("App-Id", appId);
////        headers.set("App-Key", appKey);
////        headers.setContentType(MediaType.APPLICATION_JSON);
////
////        // create request entity with headers and request body
////        HttpEntity<DiagnosisRequest> entity = new HttpEntity<>(request, headers);
////
////        // make request and retrieve response
////        DiagnosisResponse response = restTemplate.postForObject(DIAGNOSE_ENDPOINT, entity, DiagnosisResponse.class);
////
////        // extract diagnosis from response and return
////        Diagnosis diagnosis = response.getDiagnosis();
////        return diagnosis;
////    }
//
//
//
//
//
//    public String diagnose(List<String> symptoms) {
//        // Set up the request headers and body
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("App-Id", "YOUR_APP_ID_HERE");
//        headers.set("App-Key", "YOUR_APP_KEY_HERE");
//
//        DiagnosisRequest request = new DiagnosisRequest();
//        request.setSex("male");
//        request.setAge(30);
//        request.setEvidence(getEvidence(symptoms));
//
//        HttpEntity<DiagnosisRequest> entity = new HttpEntity<>(request, headers);
//
//        // Make the API call
//        ResponseEntity<DiagnosisResponse> response = restTemplate.postForEntity(
//                "https://api.infermedica.com/covid19/diagnosis",
//                entity,
//                DiagnosisResponse.class);
//
//        // Process the API response and return the diagnosis result
//        DiagnosisResponse diagnosis = response.getBody();
//        String result = diagnosis.getConditions().get(0).getName();
//        return "The most likely diagnosis is: " + result;
//    }
//
//    private List<Evidence> getEvidence(List<String> symptoms) {
//        List<Evidence> evidenceList = new ArrayList<>();
//        for (String symptom : symptoms) {
//            Evidence evidence = new Evidence();
//            evidence.setId(symptom);
//            evidence.setChoiceId("present");
//            evidenceList.add(evidence);
//        }
//        return evidenceList;
//    }
//}
//
//
//}
