package com.example.stock.domain

import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Product(val name: String, var value: Double, var quantity: Double) {

    @Id
    val id = UUID.randomUUID().toString()

    val at = LocalDateTime.now(ZoneId.of("UTC"))

}