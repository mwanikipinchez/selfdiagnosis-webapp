//package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.config;
//
//import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model.InfermedicaSymptoms;
//import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.service.InfermedicaSymptomsService;
//import lombok.AllArgsConstructor;
//import lombok.NoArgsConstructor;
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.*;
//import org.springframework.web.client.RestTemplate;
//
//
//public class Infermedica {
//    @Autowired
//    InfermedicaSymptomsService infermedicaSymptomsService;
//
//
////    @Autowired
////    public Infermedica(InfermedicaSymptomsService infermedicaSymptomsService) {
////        this.infermedicaSymptomsService = infermedicaSymptomsService;
////    }
//
//    public Infermedica() {
//    }
//
//    public void fetchData() {
//        RestTemplate restTemplate = new RestTemplate();
//        String url = "https://api.infermedica.com/v3/symptoms?age.value=25&age.unit=year";
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("App-Id", "91236c46");
//        headers.set("App-Key", "dad90ce3dcd3f2381f7b38ba5b997db1");
//
//        HttpEntity<?> entity = new HttpEntity<>(headers);
//        ResponseEntity<String> response = restTemplate.exchange( "https://api.infermedica.com/v3/symptoms?age.value=25&age.unit=year",
//                HttpMethod.GET,
//                entity,
//                String.class);
//
////        String responseBody = response.getBody();
////
////        JSONObject jsonObject = new JSONObject(responseBody);
////        JSONArray jsonArray = jsonObject.getJSONArray("data");
////
////        for (int i = 0; i < jsonArray.length(); i++) {
////            JSONObject symptomObject = jsonArray.getJSONObject(i);
////
////            InfermedicaSymptoms infermedicaSymptoms = new InfermedicaSymptoms();
////
////            infermedicaSymptoms.setId(symptomObject.getString("id"));
////            infermedicaSymptoms.setName(symptomObject.getString("name"));
////            infermedicaSymptoms.setCommon_name(symptomObject.optString("common_name"));
////            infermedicaSymptoms.setSex_filter(symptomObject.optString("sex_filter"));
////            infermedicaSymptoms.setCategory(symptomObject.optString("category"));
////            infermedicaSymptoms.setSeriousness(symptomObject.optString("seriousness"));
////            infermedicaSymptoms.setExtras(symptomObject.optString("extras"));
////            infermedicaSymptoms.setImage_url(symptomObject.optString("image_url"));
////            infermedicaSymptoms.setImage_source(symptomObject.optString("image_source"));
////            infermedicaSymptoms.setParent_id(symptomObject.optString("parent_id"));
////            infermedicaSymptoms.setParent_relation(symptomObject.optString("parent_relation"));
////
////            infermedicaSymptomsService.save(infermedicaSymptoms);
//
//
////        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class, headers);
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
////            JSONObject jsonObject = new JSONObject(jsonString);
////            JSONObject extrasObject = jsonObject.getJSONObject("extras");
////            String customProperty = extrasObject.getString("custom_property");
////            infermedicaSymptoms.setExtras(jsonObject.getString("extras"));
////            infermedicaSymptoms.setImage_url(jsonObject.getString("image_url"));
////            infermedicaSymptoms.setImage_source(jsonObject.getString("image_source"));
////            infermedicaSymptoms.setParent_id(jsonObject.getString("parent_id"));
////            infermedicaSymptoms.setParent_relation(jsonObject.getString("parent_relation"));
//
//            infermedicaSymptomsService.save(infermedicaSymptoms);
//        }
//    }
//}
