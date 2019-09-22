package com.example.order.domain

import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Document(collection = "orders")
data class Order(val customerId: String, var status: OrderStatus, val value: Double) {

    var id: String = UUID.randomUUID().toString()
    var at = LocalDateTime.now(ZoneId.of("UTC"))

}