package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp;

//import com.twilio.Twilio;
//import com.twilio.rest.api.v2010.account.Message;
//import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.config.Infermedica;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model.InfermedicaSymptoms;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model.TestSendingWithSenderID;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SelfdiagnosiswebappApplication {


	public static void main(String[] args) {

		SpringApplication.run(SelfdiagnosiswebappApplication.class, args);
//		TestSendingWithSenderID smsing = new TestSendingWithSenderID();
//		smsing.sendsms();



//		###########################################################
//		Infermedica infermedica = new Infermedica();
//		infermedica.fetchData();

//		@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

	}
	@Bean
	public RestTemplate create(){
		return new RestTemplate();
	}

}
