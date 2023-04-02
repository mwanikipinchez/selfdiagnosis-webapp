package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.service;

import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model.Messages;

import java.util.List;
import java.util.Optional;

public interface MessageService {
    List<Messages> allMessage();
    Optional<Messages> searchMessage(Long id);
    void deleteMessage(Long id);

    Messages newMessage(Messages messages);

}
