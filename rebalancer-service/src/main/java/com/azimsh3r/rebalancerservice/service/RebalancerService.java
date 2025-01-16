package com.azimsh3r.rebalancerservice.service;

import com.azimsh3r.rebalancerservice.dto.ContactDTO;
import com.azimsh3r.rebalancerservice.model.Contact;
import com.azimsh3r.rebalancerservice.model.Notification;
import com.azimsh3r.rebalancerservice.repository.NotificationRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ListConsumerGroupOffsetsResult;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RebalancerService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final NotificationRepository notificationRepository;
    private final AdminClient adminClient;
    private final ModelMapper modelMapper;
    private final ObjectMapper jacksonObjectMapper;

    @Value("${kafka.topic.notification-requests}")
    private String notificationRequestsTopic;

    public RebalancerService(KafkaTemplate<String, String> kafkaTemplate,
                             NotificationRepository notificationRepository,
                             KafkaAdmin kafkaAdmin, ModelMapper modelMapper, ObjectMapper jacksonObjectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.notificationRepository = notificationRepository;
        this.adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties());
        this.modelMapper = modelMapper;
        this.jacksonObjectMapper = jacksonObjectMapper;
    }

    public void rebalance() throws JsonProcessingException {
        List<Notification> undeliveredNotifications = notificationRepository.findUndeliveredNotifications();

        Map<TopicPartition, OffsetAndMetadata> offsets = getKafkaOffsets();

        for (Notification notification : undeliveredNotifications) {
            Contact contact = notification.getReceiver();
            ContactDTO contactDTO = modelMapper.map(contact, ContactDTO.class);

            String message = jacksonObjectMapper.writeValueAsString(contactDTO);

            kafkaTemplate.send(notificationRequestsTopic, message);
        }
    }

    private Map<TopicPartition, OffsetAndMetadata> getKafkaOffsets() {
        Map<TopicPartition, OffsetAndMetadata> offsets = new HashMap<>();
        try {
            ListConsumerGroupOffsetsResult result = adminClient.listConsumerGroupOffsets("notification-service-group");
            offsets = result.partitionsToOffsetAndMetadata().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return offsets;
    }
}
