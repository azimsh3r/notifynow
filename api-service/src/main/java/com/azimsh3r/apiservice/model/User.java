package com.azimsh3r.apiservice.model;

import com.azimsh3r.apiservice.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

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

    @JsonIgnore
    @Column(name="role")
    private String role;

    @Column(name = "organization_name")
    private String organizationName;

    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private UserStatus status;

    @JsonIgnore
    @Column(name="created_at")
    private LocalDateTime created_at;

    @JsonIgnore
    @Column(name="updated_at")
    private LocalDateTime updated_at;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Template> templates;

    @OneToMany(mappedBy = "uploadedBy", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Contact> contacts;
}
