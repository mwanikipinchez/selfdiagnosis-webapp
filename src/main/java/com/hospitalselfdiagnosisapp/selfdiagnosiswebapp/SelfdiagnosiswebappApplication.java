package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp;

//import com.twilio.Twilio;
//import com.twilio.rest.api.v2010.account.Message;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SelfdiagnosiswebappApplication {
//	public static final String ACCOUNT_SID = "AC7d946beb60deacee2d73f57e130b8a58";
//	public static final String AUTH_TOKEN = "4f293b99d6f1c61377067f3ead7d399b";


	public static void main(String[] args) {
		SpringApplication.run(SelfdiagnosiswebappApplication.class, args);
//		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
//		Message message = Message.creator(
//						new com.twilio.type.PhoneNumber("+254745902129"),
//						new com.twilio.type.PhoneNumber("+18039027343"),
//						"Where's Wallace?")
//				.create();
//
//		System.out.println(message.getSid());


	}

}
