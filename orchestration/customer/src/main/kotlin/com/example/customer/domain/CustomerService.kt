package com.example.customer.domain

import com.example.customer.grpc.CustomerServiceGrpc
import com.example.customer.grpc.ReserveCredit
import com.example.customer.grpc.ReserveCreditResponse
import io.grpc.stub.StreamObserver
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.lang.RuntimeException

@Service
class CustomerService(val repository: CustomerRepository) : CustomerServiceGrpc.CustomerServiceImplBase() {

    private val log = LoggerFactory.getLogger(this::class.java)

    fun create(name: String, creditLimit: Double): Customer {
        val customer = Customer(name, creditLimit)
        log.info("Creating $customer")
        repository.save(customer)
        log.info("Created $customer")
        return customer
    }

    fun listAll() = repository.findAll()

    override fun reserveCredit(request: ReserveCredit?, responseObserver: StreamObserver<ReserveCreditResponse>?) {
        try {
            log.info("Received request $request")
            val optional = repository.findById(request?.customerId ?: "")
            if (optional.isPresent) {
                val customer = optional.get()
                val creditLimit = customer.creditLimit
                val value = request?.value ?: 0.0
                val response = if (creditLimit > value) "APPROVED" else "DENIED"

                if ("APPROVED" == response) {
                    customer.creditLimit -= value
                    repository.save(customer)
                }

                responseObserver?.onNext(ReserveCreditResponse.newBuilder()
                        .setResponse(response)
                        .build())
            } else {
                responseObserver?.onError(RuntimeException("Customer ${request?.customerId} not found"))
            }
        } finally {
            responseObserver?.onCompleted()
        }
    }

}