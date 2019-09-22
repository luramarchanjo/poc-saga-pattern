package com.example.stock.domain

import com.example.customer.grpc.CustomerServiceGrpc
import com.example.customer.grpc.ReserveCredit
import com.example.customer.grpc.ReserveCreditResponse
import io.grpc.stub.StreamObserver
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.lang.RuntimeException

@Service
class ProductService(val repository: ProductRepository) : CustomerServiceGrpc.CustomerServiceImplBase() {

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