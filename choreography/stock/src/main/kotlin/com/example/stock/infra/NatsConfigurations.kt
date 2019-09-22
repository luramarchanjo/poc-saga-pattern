package com.example.stock.infra

import io.nats.client.Connection
import io.nats.client.Dispatcher
import io.nats.client.Nats
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class NatsConfigurations {

    @Bean
    fun createConnection() = Nats.connect()

    @Bean("ReserveProductDispacher")
    fun createReserveProductDispacher(connection: Connection, messageHandler: ReserveProductMessageHandler): Dispatcher {
        val dispatcher = connection.createDispatcher(messageHandler)
        return dispatcher.subscribe("stock-reserve-product")
    }

}