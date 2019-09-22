package com.example.order.domain

import com.example.customer.grpc.AddCreditRequest
import com.example.customer.grpc.CustomerServiceGrpc
import com.example.customer.grpc.ReserveCredit
import com.example.stock.grpc.ProductServiceGrpc
import com.example.stock.grpc.ReserveProductRequest
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class OrderService(
        val orderRepository: OrderRepository,
        val customerService: CustomerServiceGrpc.CustomerServiceBlockingStub,
        val productService: ProductServiceGrpc.ProductServiceBlockingStub
) {

    private val log = LoggerFactory.getLogger(this::class.java)

    fun create(customerId: String, products: List<Product>): Order {

        // #1 Save order as PENDING
        val order = Order(customerId, OrderStatus.PENDING, products)
        log.info("Creating $order")
        orderRepository.save(order)
        log.info("Created $order")

        // #2 Reserve credit
        val reserveCredit = customerService.reserveCredit(ReserveCredit.newBuilder()
                .setCustomerId(order.customerId)
                .setValue(order.getValue())
                .build())
        val customerStatus = CustomerStatus.valueOf(reserveCredit.response)

        // #3 Reserve product
        var productStatus = ProductStatus.DENIED
        if (CustomerStatus.APPROVED == customerStatus) {
            for (product in order.products) {
                val reserveProduct = productService.reserveProduct(ReserveProductRequest.newBuilder()
                        .setProductId(product.id)
                        .setQuantity(product.quantity)
                        .build())
                productStatus = ProductStatus.valueOf(reserveProduct.response)
                if (ProductStatus.DENIED == productStatus) {
                    customerService.addCredit(AddCreditRequest.newBuilder()
                            .setCustomerId(order.customerId)
                            .setValue(order.getValue())
                            .build())
                    break // stop the loop
                }
            }
        }

        // #4 Save order as APPROVED or DENIED
        order.status = if (CustomerStatus.APPROVED == customerStatus && ProductStatus.APPROVED == productStatus) OrderStatus.APPROVED else OrderStatus.DENIED
        orderRepository.save(order)

        return order
    }

    fun getAll() = orderRepository.findAll()

}