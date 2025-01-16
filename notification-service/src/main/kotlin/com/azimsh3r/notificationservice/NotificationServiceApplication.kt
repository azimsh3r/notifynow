package com.azimsh3r.notificationservice

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class NotificationServiceApplication {

    @Bean
    fun objectMapper() : ObjectMapper = ObjectMapper()
}

fun main(args: Array<String>) {
    runApplication<NotificationServiceApplication>(*args)
}
