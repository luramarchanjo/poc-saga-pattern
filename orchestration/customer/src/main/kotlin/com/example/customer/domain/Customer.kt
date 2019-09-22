package com.example.customer.domain

import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Customer(val name: String, var creditLimit: Double) {

    @Id
    val id = UUID.randomUUID().toString()

    val at = LocalDateTime.now(ZoneId.of("UTC"))

}