package com.example.order.infra

import com.example.order.domain.OrderStatus

data class OrderResponse(
        val id: String,
        val customerId: String,
        val status: OrderStatus,
        val value: Double
)