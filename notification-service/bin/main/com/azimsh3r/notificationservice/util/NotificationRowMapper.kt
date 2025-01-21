import com.azimsh3r.notificationservice.enums.NotificationStatus
import com.azimsh3r.notificationservice.model.Notification
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet
import java.sql.SQLException

class NotificationRowMapper : RowMapper<Notification> {
    @Throws(SQLException::class)
    override fun mapRow(rs: ResultSet, rowNum: Int): Notification {
        val id = rs.getInt("id")
        val receiverId = rs.getInt("receiver_id")
        val templateId = rs.getInt("template_id")
        val status = NotificationStatus.valueOf(rs.getString("status"))

        return Notification(id, receiverId, templateId, status)
    }
}