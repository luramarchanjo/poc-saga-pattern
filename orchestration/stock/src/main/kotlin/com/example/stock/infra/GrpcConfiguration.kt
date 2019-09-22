package com.example.stock.infra

import com.example.stock.domain.ProductService
import io.grpc.ServerBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GrpcConfiguration {

    @Bean
    fun start(customerService: ProductService) = ServerBuilder.forPort(5001)
            .addService(customerService)
            .build()
            .start()

}