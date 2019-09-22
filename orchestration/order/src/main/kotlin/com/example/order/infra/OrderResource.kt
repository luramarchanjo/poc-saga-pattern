package com.example.order.infra

import com.example.order.domain.OrderService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/orders")
class OrderResource(val orderService: OrderService) {

    private val log = LoggerFactory.getLogger(this::class.java)

    @PostMapping
    fun post(@RequestBody request: OrderPostRequest): ResponseEntity<OrderResponse> {
        log.info("Receiving a POST /v1/orders")
        val order = orderService.create(request.customerId, request.value)
        val response = OrderResponse(order.id, order.customerId, order.status, order.value)
        log.info("Received a POST /v1/orders")

        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @GetMapping
    fun get(): ResponseEntity<MutableList<OrderResponse>> {
        log.info("Receiving a GET /v1/orders")
        val orders = orderService.getAll()
        val response = mutableListOf<OrderResponse>()
        orders.forEach { order ->
            response.add(OrderResponse(order.id, order.customerId, order.status, order.value))
        }
        log.info("Received a GET /v1/orders")

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response)
    }

}