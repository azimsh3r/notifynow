package com.azimsh3r.notificationservice.service

import com.azimsh3r.notificationservice.dto.ContactDTO
import com.azimsh3r.notificationservice.dto.TemplateDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DeliveryService @Autowired constructor(val templateService: TemplateService){

    fun deliverNotification(contact: ContactDTO) {
        val template: TemplateDTO? = contact.templateId?.let { templateService.findTemplateById(it) }

        if (template == null) {
            println("Template with ID ${contact.templateId} not found in Redis.")
            return
        }

        sendNotification(contact.name ?: "recipient", template.content)
    }

    private fun sendNotification(recipient: String, message: String) {
        println("Notification sent to $recipient: $message")
    }
}
