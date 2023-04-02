//package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.service;
//
//import com.africastalking.Callback;
//import com.africastalking.SmsService;
//import com.africastalking.sms.Message;
//import com.africastalking.sms.Recipient;
//import com.africastalking.AfricasTalking;
//
//import com.africastalking.Callback;
//
//import java.util.List;
//import java.io.IOException;
//public class AfricasTalking {
//
//    public void sendSMS(){
//        /* Set your app credentials */
//        String USERNAME = "MyAppUsername";
//        String API_KEY = "MyAppAPIKey";
//
//        /* Initialize SDK */
//        AfricasTalking.initialize(USERNAME, API_KEY);
//
//        /* Get the SMS service */
//        SmsService sms = AfricasTalking.getService(AfricasTalking.SERVICE_SMS);
//
//        /* Set the numbers you want to send to in international format */
//        String[] recipients = new String[] {
//                "+254711XXXYYY", "+254702XXXYYY"
//        };
//
//        /* Set your message */
//        String message = "We are lumberjacks. We sleep all day and code all night";
//
//        /* Set your shortCode or senderId */
//        String from = "XXXXX"; // or "ABCDE"
//
//        /* That’s it, hit send and we’ll take care of the rest */
//        try {
//            List<Recipient> response = sms.send(message, from, recipients, true);
//            for (Recipient recipient : response) {
//                System.out.print(recipient.number);
//                System.out.print(" : ");
//                System.out.println(recipient.status);
//            }
//        } catch(Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//}
//
