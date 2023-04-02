package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model;

/* Import SDK classes */
import com.africastalking.Callback;
import com.africastalking.SmsService;
import com.africastalking.sms.Message;
import com.africastalking.sms.Recipient;
import com.africastalking.AfricasTalking;
import org.springframework.stereotype.Service;

import java.util.List;
import java.io.IOException;

@Service
public class TestSendingWithSenderID {


        /* Set your app credentials */
        String USERNAME = "freshapp23";
        String API_KEY = "462936250e6991a277ed212531a806d818247e2da71b204cbac1d45699562de7";


        public void adminSms(){
            /* Initialize SDK */
            AfricasTalking.initialize(USERNAME, API_KEY);

            /* Get the SMS service */
            SmsService sms = AfricasTalking.getService(AfricasTalking.SERVICE_SMS);

            /* Set the numbers you want to send to in international format */
//            String[] recipients = new String[] {
//                    "+254745902129", "+254717160100"
//            };
            String to = "+254717198471"; // replace with recipient phone number
            String message = "Hello Admin. There is a new registration on St. Mary Hospital web application";
//            String from = "StMary";

            /* Set your message */
//            String message = "New registration in your application";

            /* Set your shortCode or senderId */
//            String from = "St Mary"; // or "ABCDE"

            /* That’s it, hit send and we’ll take care of the rest */
            try {
                List<Recipient> response = sms.send(message,  new String[]{to}, true);
                for (Recipient recipient : response) {
                    System.out.print(recipient.number);
                    System.out.print(" : ");
                    System.out.println(recipient.status);
                }
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    public void clientSms(String customerNumber, String message) {
        /* Initialize SDK */
        AfricasTalking.initialize(USERNAME, API_KEY);

        /* Get the SMS service */
        SmsService sms = AfricasTalking.getService(AfricasTalking.SERVICE_SMS);

        /* Set the numbers you want to send to in international format */
//            String[] recipients = new String[] {
//                    "+254745902129", "+254717160100"
//            };
//        String to = "+254717198471"; // replace with recipient phone number
//        String message = "Thank You for registering with St. Mary Telemedical Services. You can login to patient portal and experience our exception services.";
//            String from = "StMary";

        /* Set your message */
//            String message = "New registration in your application";

        /* Set your shortCode or senderId */
//            String from = "St Mary"; // or "ABCDE"

        /* That’s it, hit send and we’ll take care of the rest */
        try {
            List<Recipient> response = sms.send(message, new String[]{customerNumber}, true);
            for (Recipient recipient : response) {
                System.out.print(recipient.number);
                System.out.print(" : ");
                System.out.println(recipient.status);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
        }



