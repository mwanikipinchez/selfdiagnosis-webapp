package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.service;

import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model.Messages;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class MessageServiceImpl implements MessageService{



    private MessageRepository messageRepository;


    @Autowired

    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public List<Messages> allMessage() {
        return messageRepository.findAll();
    }

    @Override
    public Optional<Messages> searchMessage(Long id) {
        return messageRepository.findById(id);
    }

    @Override
    public void deleteMessage(Long id) {
        messageRepository.deleteById(id);

    }

    @Override
    public Messages newMessage(Messages messages) {

        return messageRepository.save(messages);
    }
}
