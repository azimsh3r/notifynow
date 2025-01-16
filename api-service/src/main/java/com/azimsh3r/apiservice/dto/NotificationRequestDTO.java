package com.azimsh3r.apiservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NotificationRequestDTO {
    @JsonProperty("template_id")
    private int templateId;
}
