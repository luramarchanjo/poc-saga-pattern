package com.example.order.domain

import com.example.order.infra.ReserveCreditRequest
import com.fasterxml.jackson.databind.ObjectMapper
import io.nats.client.Connection
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets

@Service
class OrderService(val orderRepository: OrderRepository, val connection: Connection, val objectMapper: ObjectMapper) {

    private val log = LoggerFactory.getLogger(this::class.java)

    fun create(customerId: String, products: List<Product>): Order {

        // #1 Save order as PENDING
        val order = Order(customerId, OrderStatus.PENDING, products)
        log.info("Creating $order")
        orderRepository.save(order)
        log.info("Created $order")

        // #2 Reserve credit
        val reserveCreditRequest = ReserveCreditRequest(order.customerId, order.getValue())
        val reserveCreditFuture = connection.request("customer-reserve-credit", objectMapper.writeValueAsBytes(reserveCreditRequest))
        val reserveCreditResponse = reserveCreditFuture.get()
        val customerStatus = objectMapper.readValue(String(reserveCreditResponse.data, StandardCharsets.UTF_8), CustomerStatus::class.java)

        // #3 Reserve product
        val productStatus = ProductStatus.APPROVED

        // #4 Save order as APPROVED or DENIED
        order.status = if (CustomerStatus.APPROVED == customerStatus && ProductStatus.APPROVED == productStatus) OrderStatus.APPROVED else OrderStatus.DENIED
        orderRepository.save(order)

        return order
    }

    fun getAll() = orderRepository.findAll()

}