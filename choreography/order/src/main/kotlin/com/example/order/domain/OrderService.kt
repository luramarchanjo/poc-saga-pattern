package com.example.order.domain

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class OrderService(
        val orderRepository: OrderRepository
) {

    private val log = LoggerFactory.getLogger(this::class.java)

    fun create(customerId: String, products: List<Product>): Order {

        // #1 Save order as PENDING
        val order = Order(customerId, OrderStatus.PENDING, products)
        log.info("Creating $order")
        orderRepository.save(order)
        log.info("Created $order")

        // #2 Reserve credit
        val customerStatus = CustomerStatus.APPROVED

        // #3 Reserve product
        val productStatus = ProductStatus.APPROVED

        // #4 Save order as APPROVED or DENIED
        order.status = if (CustomerStatus.APPROVED == customerStatus && ProductStatus.APPROVED == productStatus) OrderStatus.APPROVED else OrderStatus.DENIED
        orderRepository.save(order)

        return order
    }

    fun getAll() = orderRepository.findAll()

}