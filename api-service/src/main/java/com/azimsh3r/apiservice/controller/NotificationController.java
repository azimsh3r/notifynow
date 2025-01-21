package com.azimsh3r.apiservice.controller;

import com.azimsh3r.apiservice.dto.NotificationRequestDTO;
import com.azimsh3r.apiservice.security.AuthenticationDetails;
import com.azimsh3r.apiservice.service.NotificationService;
import com.azimsh3r.apiservice.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationService notificationService;
    private final UserService userService;

    @Autowired
    public NotificationController(NotificationService notificationService, UserService userService) {
        this.notificationService = notificationService;
        this.userService = userService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendMessages(@RequestBody NotificationRequestDTO notificationRequestDTO, @AuthenticationPrincipal AuthenticationDetails authenticationDetails) throws JsonProcessingException {
        notificationService.sendNotifications(
                notificationRequestDTO.getTemplateId(),
                authenticationDetails.getUsername()
        );
        return ResponseEntity.ok("Notifications are sent successfully!");
    }
}
