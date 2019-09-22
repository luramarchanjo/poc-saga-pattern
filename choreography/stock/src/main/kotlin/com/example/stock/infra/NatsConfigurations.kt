package com.example.stock.infra

import io.nats.client.Nats
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class NatsConfigurations {

    @Bean
    fun createConnection() = Nats.connect()

}