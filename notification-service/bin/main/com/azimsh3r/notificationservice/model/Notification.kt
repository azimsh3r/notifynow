package com.azimsh3r.notificationservice.model

import com.azimsh3r.notificationservice.enums.NotificationStatus

data class Notification(
    var id: Int,
    var receiverId: Int,
    var templateId: Int,
    var status: NotificationStatus? = null
)