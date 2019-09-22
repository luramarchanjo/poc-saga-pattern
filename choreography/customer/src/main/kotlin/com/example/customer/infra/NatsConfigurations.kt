package com.example.customer.infra

import io.nats.client.Connection
import io.nats.client.Dispatcher
import io.nats.client.Nats
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class NatsConfigurations {

    @Bean
    fun createConnection() = Nats.connect()

    @Bean
    fun createDispatcher(connection: Connection, reserveCreditMessageHandler: ReserveCreditMessageHandler): Dispatcher {
        val dispatcher = connection.createDispatcher(reserveCreditMessageHandler)
        return dispatcher.subscribe("customer-reserve-credit")
    }

}