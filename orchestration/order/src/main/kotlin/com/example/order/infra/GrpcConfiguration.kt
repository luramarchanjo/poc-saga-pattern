package com.example.order.infra

import com.example.customer.grpc.CustomerServiceGrpc
import com.example.stock.grpc.ProductServiceGrpc
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GrpcConfiguration {

    @Bean("customerChannel")
    fun createCustomerChannel() = ManagedChannelBuilder
            .forAddress("localhost", 5000)
            .usePlaintext()
            .build()

    @Bean("stockChannel")
    fun createStockChannel() = ManagedChannelBuilder
            .forAddress("localhost", 5001)
            .usePlaintext()
            .build()

    @Bean
    fun createCustomerServiceGrpc(customerChannel: ManagedChannel) = CustomerServiceGrpc.newBlockingStub(customerChannel)

    @Bean
    fun createProductServiceGrpc(stockChannel: ManagedChannel) = ProductServiceGrpc.newBlockingStub(stockChannel)

}