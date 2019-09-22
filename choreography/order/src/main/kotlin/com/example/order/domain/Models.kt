package com.example.order.domain

import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Document(collection = "orders")
data class Order(val customerId: String, var status: OrderStatus, val products : List<Product>) {

    var id: String = UUID.randomUUID().toString()
    var at = LocalDateTime.now(ZoneId.of("UTC"))

    fun getValue() = products.sumByDouble { product -> product.getTotal() }

}

data class Product(val id: String, val value: Double, val quantity: Int) {

    fun getTotal() = quantity * value

}