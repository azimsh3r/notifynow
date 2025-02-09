package com.azimsh3r.apiservice.dto;

import lombok.Data;

@Data
public class ContactDTO {
    private int id;
    private String name;
    private String email;
    private String phoneNumber;
    private String extraInfo;
    private int templateId;
}
