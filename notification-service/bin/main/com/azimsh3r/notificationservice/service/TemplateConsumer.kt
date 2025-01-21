package com.azimsh3r.notificationservice.service

import com.azimsh3r.notificationservice.dto.TemplateDTO
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Service

@Service
class TemplateConsumer @Autowired constructor(val objectMapper: ObjectMapper, val templateService: TemplateService) {

    @KafkaListener(topics = ["templates-topic"], groupId = "notification-service")
    fun consumeTemplate(message: String) {
        try {
            val template : TemplateDTO = objectMapper.readValue(message, TemplateDTO::class.java)

            println(template.content)
            templateService.cacheTemplate(template)
        } catch (e: JsonProcessingException) {
            e.printStackTrace()
        }
    }
}
