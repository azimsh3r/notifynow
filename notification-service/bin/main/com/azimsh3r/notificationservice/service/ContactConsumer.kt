package com.azimsh3r.notificationservice.service

import ContactDTO
import com.azimsh3r.notificationservice.dao.NotificationDAO
import com.azimsh3r.notificationservice.enums.NotificationStatus
import com.azimsh3r.notificationservice.model.Notification
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class ContactConsumer @Autowired constructor(val objectMapper: ObjectMapper, val deliveryService: DeliveryService, val notificationDAO: NotificationDAO){

    @KafkaListener(topics = ["contacts-topic"], groupId = "notification-service")
    fun consumeContact(message: String) {
        try{
            val contact : ContactDTO = objectMapper.readValue(message, ContactDTO::class.java)

            val notification : Notification? = notificationDAO.getNotificationById(contact.id, contact.templateId)

            if (notification != null && notification.status == NotificationStatus.PENDING) {
                deliveryService.deliverNotification(contact)
            }
        } catch (e: JsonProcessingException) {
            e.printStackTrace()
        }
    }
}
