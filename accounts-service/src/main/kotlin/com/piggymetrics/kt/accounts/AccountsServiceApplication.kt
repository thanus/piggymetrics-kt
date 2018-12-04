package com.piggymetrics.kt.accounts

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@EnableDiscoveryClient
@SpringBootApplication
class AccountsServiceApplication

fun main(args: Array<String>) {
    runApplication<AccountsServiceApplication>(*args)
}
