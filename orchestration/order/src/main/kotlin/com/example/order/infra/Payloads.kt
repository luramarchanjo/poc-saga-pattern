package com.example.order.infra

import com.example.order.domain.Product
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

data class OrderPostRequest(
        @param:NotBlank val customerId: String,
        @param:NotEmpty val products: Array<OrderProductPostRequest>
) {

    fun toProducts() = products.map { request -> request.toProduct() }

}

data class OrderProductPostRequest(val id: String, val value: Double, val quantity: Int) {

    fun toProduct() = Product(id, value, quantity)

}