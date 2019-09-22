package com.example.stock.domain

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ProductService(val repository: ProductRepository) {

    private val log = LoggerFactory.getLogger(this::class.java)

    fun create(name: String, value: Double, quantity: Double): Product {
        val customer = Product(name, value, quantity)
        log.info("Creating $customer")
        repository.save(customer)
        log.info("Created $customer")
        return customer
    }

    fun listAll() = repository.findAll()

}