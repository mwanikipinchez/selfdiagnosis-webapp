//package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model;
//
//import com.twilio.Twilio;
//import com.twilio.rest.api.v2010.account.Message;
//import com.twilio.type.PhoneNumber;
//
//public class SmsSender {
//
//    public static final String ACCOUNT_SID = "AC7d946beb60deacee2d73f57e130b8a58";
//    public static final String AUTH_TOKEN = "4f293b99d6f1c61377067f3ead7d399b";
//
//    public static void sendSMS(String toNumber, String message) {
//        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
//        Message twilioMessage = Message.creator(
//                new PhoneNumber(toNumber),
//                new PhoneNumber("+18039027343"),
//                message
//        ).create();
//        System.out.println("Message SID: " + twilioMessage.getSid());
//    }
//}
