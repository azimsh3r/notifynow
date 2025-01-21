package com.azimsh3r.rebalancerservice.model;

import com.azimsh3r.rebalancerservice.enums.UserStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "\"user\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="username")
    private String username;

    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="role")
    private String role;

    @Column(name = "organization_name")
    private String organizationName;

    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private UserStatus status;

    @Column(name="created_at")
    private LocalDateTime created_at;

    @Column(name="updated_at")
    private LocalDateTime updated_at;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Template> templates;

    @OneToMany(mappedBy = "uploadedBy", cascade = CascadeType.ALL)
    private List<Contact> contacts;
}
