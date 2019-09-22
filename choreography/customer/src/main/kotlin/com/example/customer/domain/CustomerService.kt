package com.example.customer.domain

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CustomerService(val repository: CustomerRepository) {

    private val log = LoggerFactory.getLogger(this::class.java)

    fun create(name: String, creditLimit: Double): Customer {
        val customer = Customer(name, creditLimit)
        log.info("Creating $customer")
        repository.save(customer)
        log.info("Created $customer")
        return customer
    }

    fun listAll() = repository.findAll()

    fun reserveCredit(customerId: String, value: Double): CustomerStatus {
        var customerStatus = CustomerStatus.DENIED

        val optional = repository.findById(customerId)
        if (optional.isPresent) {
            val customer = optional.get()
            val currentCreditLimit = customer.creditLimit

            if (currentCreditLimit >= value) {
                customer.creditLimit -= value
                customerStatus = CustomerStatus.APPROVED
                repository.save(customer)
                log.info("Customer $customerId limit $value was approved")
            } else {
                log.warn("Customer $customerId limit $value was denied")
            }
        } else {
            log.warn("Customer $customerId was not found")
        }

        return customerStatus
    }

    fun addCredit(customerId: String, value: Double): CustomerStatus {
        var customerStatus = CustomerStatus.DENIED

        val optional = repository.findById(customerId)
        if (optional.isPresent) {
            val customer = optional.get()
            customer.creditLimit += value
            repository.save(customer)
            customerStatus = CustomerStatus.APPROVED
            log.warn("Customer $customerId limit $value was approved")
        } else {
            log.warn("Customer $customerId was not found")
        }

        return customerStatus
    }

}