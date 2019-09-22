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

    @Bean("ReserveCreditDispacher")
    fun createReserveCreditDispacher(connection: Connection, messageHandler: ReserveCreditMessageHandler): Dispatcher {
        val dispatcher = connection.createDispatcher(messageHandler)
        return dispatcher.subscribe("customer-reserve-credit")
    }

    @Bean("AddCreditDispacher")
    fun createAddCreditDispacher(connection: Connection, messageHandler: AddCreditMessageHandler): Dispatcher {
        val dispatcher = connection.createDispatcher(messageHandler)
        return dispatcher.subscribe("customer-add-credit")
    }

}