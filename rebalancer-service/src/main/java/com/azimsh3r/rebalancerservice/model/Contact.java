package com.azimsh3r.rebalancerservice.model;

import com.azimsh3r.apiservice.enums.ContactStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name="contact")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="name")
    private String name;

    @Column(name="email")
    private String email;

    @Column(name="phone_number")
    private String phoneNumber;

    @Column(name="group")
    private String group;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private ContactStatus status;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @Column(name="extra_info")
    private String extraInfo;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User uploadedBy;
}
