package com.azimsh3r.apiservice.service;

import com.azimsh3r.apiservice.dto.ContactDTO;
import com.azimsh3r.apiservice.dto.TemplateDTO;
import com.azimsh3r.apiservice.enums.NotificationStatus;
import com.azimsh3r.apiservice.model.Contact;
import com.azimsh3r.apiservice.model.Notification;
import com.azimsh3r.apiservice.model.Template;
import com.azimsh3r.apiservice.model.User;
import com.azimsh3r.apiservice.repository.ContactRepository;
import com.azimsh3r.apiservice.repository.NotificationRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final TemplateService templateService;
    private final ContactService contactService;
    private final ModelMapper modelMapper;
    private final KafkaProducer kafkaProducer;
    private final ContactRepository contactRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, TemplateService templateService, ContactService contactService, ModelMapper modelMapper, KafkaProducer kafkaProducer, ContactRepository contactRepository) {
        this.notificationRepository = notificationRepository;
        this.templateService = templateService;
        this.contactService = contactService;
        this.modelMapper = modelMapper;
        this.kafkaProducer = kafkaProducer;
        this.contactRepository = contactRepository;
    }

    @Transactional
    public void sendNotifications(int templateId, User user) throws JsonProcessingException {
        Template template = templateService.getTemplateById(templateId);

        List<Contact> contacts = contactService.getContactsByUploadedById(user.getId());
        List<Notification> notifications = new ArrayList<>();

        for (Contact contact : contacts) {
            Notification notification = new Notification();
            notification.setReceiver(contact);
            notification.setTemplate(template);
            notification.setStatus(NotificationStatus.PENDING);
            notifications.add(notification);
        }

        notificationRepository.saveAll(notifications);

        kafkaProducer.sendTemplate(modelMapper.map(template, TemplateDTO.class));
        for (Contact contact : contacts) {
            ContactDTO contactDTO = modelMapper.map(contact, ContactDTO.class);
            contactDTO.setTemplateId(templateId);
            kafkaProducer.sendContact(contactDTO);
        }
    }
}
