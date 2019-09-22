package com.example.order.infra

import com.example.customer.grpc.CustomerServiceGrpc
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GrpcConfiguration {

    @Bean
    fun createManagedChannel() = ManagedChannelBuilder
            .forAddress("localhost", 5000)
            .usePlaintext()
            .build()

    @Bean
    fun createCustomerServiceGrpc(managedChannel: ManagedChannel) = CustomerServiceGrpc.newBlockingStub(managedChannel)

}