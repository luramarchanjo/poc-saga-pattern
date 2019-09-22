package com.example.stock.infra

import com.example.stock.domain.ProductService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/products")
class ProductResource(val productService: ProductService) {

    private val log = LoggerFactory.getLogger(this::class.java)

    @PostMapping
    fun post(@RequestBody request: ProductPostRequest): ResponseEntity<ProductResponse> {
        log.info("Receiving a POST /v1/products")
        val product = productService.create(request.name, request.value, request.quantity)
        val response = ProductResponse(product.id, product.name, product.value, product.quantity)
        log.info("Received a POST /v1/products")

        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun getAll() = productService.listAll()

}