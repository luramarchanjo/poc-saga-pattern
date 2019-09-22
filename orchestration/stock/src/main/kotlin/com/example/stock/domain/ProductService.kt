package com.example.stock.domain

import com.example.stock.grpc.ProductServiceGrpc
import com.example.stock.grpc.ReserveProductRequest
import com.example.stock.grpc.ReserveProductResponse
import io.grpc.stub.StreamObserver
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ProductService(val repository: ProductRepository) : ProductServiceGrpc.ProductServiceImplBase() {

    private val log = LoggerFactory.getLogger(this::class.java)

    fun create(name: String, value: Double, quantity: Double): Product {
        val customer = Product(name, value, quantity)
        log.info("Creating $customer")
        repository.save(customer)
        log.info("Created $customer")
        return customer
    }

    fun listAll() = repository.findAll()

    override fun reserveProduct(request: ReserveProductRequest?, responseObserver: StreamObserver<ReserveProductResponse>?) {
        try {
            val optional = repository.findById(request?.productId ?: "")
            if (optional.isPresent && request != null && responseObserver != null) {
                val product = optional.get()
                val quantity = product.quantity
                var response : String?

                if (quantity >= request.quantity) {
                    product.quantity -= request.quantity
                    repository.save(product)
                    response = ProductStatus.APPROVED.name
                } else {
                    response = ProductStatus.DENIED.name
                }

                responseObserver.onNext(ReserveProductResponse.newBuilder()
                        .setResponse(response)
                        .build())
            } else {
                responseObserver?.onError(RuntimeException("Product ${request?.productId} not found"))
            }
        } finally {
            responseObserver?.onCompleted()
        }
    }

}