package com.azimsh3r.notificationservice.dto

data class ContactDTO(
    var id: Int,
    var name : String?,
    var email: String?,
    var phoneNumber: String,
    var templateId: Int?
)
