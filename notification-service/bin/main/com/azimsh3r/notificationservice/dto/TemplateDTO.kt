package com.azimsh3r.notificationservice.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty


data class TemplateDTO @JsonCreator constructor(
    @JsonProperty("id") val id: Int,
    @JsonProperty("name") val name: String?,
    @JsonProperty("content") val content: String?,
    @JsonProperty("variables") val variables: String?,
)