package com.azimsh3r.apiservice.model;

import com.azimsh3r.apiservice.enums.NotificationStatus;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name="receiver_id", referencedColumnName = "id")
    private Contact receiver;

    @ManyToOne
    @JoinColumn(name="template_id", referencedColumnName = "id")
    private Template template;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private NotificationStatus status;
}
