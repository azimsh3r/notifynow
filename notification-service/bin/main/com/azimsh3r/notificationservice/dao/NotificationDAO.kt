package com.azimsh3r.notificationservice.dao

import NotificationRowMapper
import com.azimsh3r.notificationservice.model.Notification
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.queryForObject
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
class NotificationDAO {
    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    @Transactional
    fun updateNotificationStatus(contactId: Int, templateId: Int, status: String): Int {
        val sql = "UPDATE notification SET status = ? WHERE receiver_id = ? AND template_id = ?"
        return jdbcTemplate.update(sql, status, contactId, templateId)
    }

    @Transactional
    fun getNotificationById(contactId: Int, templateId: Int): Notification? {
        val sql = "SELECT id, receiver_id, template_id, status FROM notification WHERE receiver_id = ? AND template_id = ?"
        return try {
            jdbcTemplate.queryForObject(
                sql,
                arrayOf(contactId, templateId),
                NotificationRowMapper()
            )
        } catch (e: Exception) {
            null
        }
    }
}
