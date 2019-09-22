package com.example.customer.infra

import com.example.customer.domain.CustomerService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/customers")
class CustomerResource(val customerService: CustomerService) {

    private val log = LoggerFactory.getLogger(this::class.java)

    @PostMapping
    fun post(@RequestBody request: CustomerPostRequest): ResponseEntity<CustomerResponse> {
        log.info("Receiving a POST /v1/customers")
        val order = customerService.create(request.name, request.creditLimit)
        val response = CustomerResponse(order.id, order.name, order.creditLimit)
        log.info("Received a POST /v1/customers")

        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun getAll() = customerService.listAll()

}