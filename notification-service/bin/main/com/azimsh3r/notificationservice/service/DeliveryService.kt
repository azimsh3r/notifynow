package com.azimsh3r.notificationservice.service

import ContactDTO
import com.azimsh3r.notificationservice.dao.NotificationDAO
import com.azimsh3r.notificationservice.dto.TemplateDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DeliveryService @Autowired constructor(val templateService: TemplateService, val notificationDAO: NotificationDAO){

    fun deliverNotification(contact: ContactDTO) {
        val template: TemplateDTO? = templateService.findTemplateById(contact.templateId)

        if (template == null) {
            println("Template with ID ${contact.templateId} not found in Redis.")
            return
        }

        template.content?.let {
            sendNotification(contact.name ?: "recipient", it)
            notificationDAO.updateNotificationStatus(contact.id, template.id, "SUCCESS")
        }
    }

    private fun sendNotification(recipient: String, message: String) {
        println("Notification sent to $recipient: $message")
    }
}
