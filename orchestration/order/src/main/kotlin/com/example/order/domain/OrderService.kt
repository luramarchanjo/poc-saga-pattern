package com.example.order.domain

import com.example.customer.grpc.CustomerServiceGrpc
import com.example.customer.grpc.ReserveCredit
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class OrderService(val orderRepository: OrderRepository, val customerService: CustomerServiceGrpc.CustomerServiceBlockingStub) {

    private val log = LoggerFactory.getLogger(this::class.java)

    fun create(customerId: String, products : List<Product>): Order {
        val order = Order(customerId, OrderStatus.PENDING, products)
        log.info("Creating $order")
        orderRepository.save(order)
        log.info("Created $order")

        val reserveCredit = customerService.reserveCredit(ReserveCredit.newBuilder()
                .setCustomerId(order.customerId)
                .setValue(order.getValue())
                .build())

        val orderStatus = OrderStatus.valueOf(reserveCredit.response)
        order.status = orderStatus
        orderRepository.save(order)
        log.info("Order=[${order.id}] status=[${order.status}]")

        return order
    }

    fun getAll() = orderRepository.findAll()

}