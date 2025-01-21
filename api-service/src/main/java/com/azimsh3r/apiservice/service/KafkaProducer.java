package com.azimsh3r.apiservice.service;

import com.azimsh3r.apiservice.dto.ContactDTO;
import com.azimsh3r.apiservice.dto.TemplateDTO;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper;

    @Autowired
    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendContact(ContactDTO contact) throws JsonProcessingException {
        String message = objectMapper.writeValueAsString(contact);
        kafkaTemplate.send("contacts-topic", message);
    }

    public void sendTemplate(TemplateDTO template) throws JsonProcessingException {
        String message = objectMapper.writeValueAsString(template);
        kafkaTemplate.send("templates-topic", message);
    }
}
