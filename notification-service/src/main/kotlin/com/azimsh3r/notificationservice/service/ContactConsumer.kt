package com.azimsh3r.notificationservice.service

import com.azimsh3r.notificationservice.dto.ContactDTO
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class ContactConsumer @Autowired constructor(val objectMapper: ObjectMapper, val deliveryService: DeliveryService){

    @KafkaListener(topics = ["contacts-topic"], groupId = "notification-service")
    fun consumeContact(message: String) {
        try{
            val contact : ContactDTO = objectMapper.readValue(message, ContactDTO::class.java)

            deliveryService.deliverNotification(contact)
        } catch (e: JsonProcessingException) {
            e.printStackTrace()
        }
    }
}
