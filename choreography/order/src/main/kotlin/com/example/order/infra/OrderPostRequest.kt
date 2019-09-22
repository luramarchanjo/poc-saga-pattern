package com.example.order.infra

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class OrderPostRequest(
        @param:NotBlank val customerId: String,
        @param:NotNull val value: Double
)