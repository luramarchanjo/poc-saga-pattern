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

    fun reserveProduct(productId: String, quantity: Int): ProductStatus {
        var response = ProductStatus.DENIED
        val optional = repository.findById(productId)
        if (optional.isPresent) {
            val product = optional.get()
            val currentQuantity = product.quantity
            if (currentQuantity >= quantity) {
                product.quantity -= quantity
                repository.save(product)
                response = ProductStatus.APPROVED
                log.info("Product $productId quantity was APPROVED")
            } else {
                log.warn("Product $productId quantity was DENIED")
            }
        } else{
            log.warn("Product $productId was not found")
        }

        return response
    }

}