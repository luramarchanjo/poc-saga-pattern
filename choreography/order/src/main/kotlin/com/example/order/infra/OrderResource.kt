package com.example.order.infra

import com.example.order.domain.OrderService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/orders")
class OrderResource(val orderService: OrderService) {

    private val log = LoggerFactory.getLogger(this::class.java)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun post(@RequestBody request: OrderPostRequest) = orderService.create(request.customerId, request.toProducts())

    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun get() = orderService.getAll()

}