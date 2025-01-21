package com.azimsh3r.notificationservice

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.context.annotation.Bean
import org.springframework.kafka.annotation.EnableKafka

@SpringBootApplication
@EnableKafka
@RefreshScope
class NotificationServiceApplication {

    @Bean
    fun objectMapper() : ObjectMapper = ObjectMapper()
}

fun main(args: Array<String>) {
    runApplication<NotificationServiceApplication>(*args)
}
