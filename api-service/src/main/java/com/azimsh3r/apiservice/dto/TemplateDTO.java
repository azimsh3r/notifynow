package com.azimsh3r.apiservice.dto;

import lombok.Data;

@Data
public class TemplateDTO {
    private Integer id;

    private String name;

    private String content;

    private String variables;
}
