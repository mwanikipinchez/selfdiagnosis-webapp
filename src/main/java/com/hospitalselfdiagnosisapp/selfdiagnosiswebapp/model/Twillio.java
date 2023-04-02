package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model;

import com.twilio.Twilio;
import com.twilio.Twilio;
import com.twilio.converter.Promoter;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.net.URI;
import java.math.BigDecimal;


public class Twillio {
    // Find your Account Sid and Token at twilio.com/console
    public static final String ACCOUNT_SID = "ACb253d7c1d0f17631c13c7f20717056bc";
    public static final String AUTH_TOKEN = "f0d7b0ed1f511f1e1d43665f920d3758";

    public void sendMessage() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber("+254745902129"),
                        new com.twilio.type.PhoneNumber("+15855728689"),
                        "There is a new registration on your application. Check out")
                .create();

        System.out.println(message.getSid());
    }

}
