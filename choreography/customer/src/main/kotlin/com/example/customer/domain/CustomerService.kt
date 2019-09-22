package com.example.customer.domain

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CustomerService(val repository: CustomerRepository) {

    private val log = LoggerFactory.getLogger(this::class.java)

    fun create(name: String, creditLimit: Double): Customer {
        val customer = Customer(name, creditLimit)
        log.info("Creating $customer")
        repository.save(customer)
        log.info("Created $customer")
        return customer
    }

    fun listAll() = repository.findAll()

}