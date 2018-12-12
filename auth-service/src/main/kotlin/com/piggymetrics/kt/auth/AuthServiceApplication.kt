package com.piggymetrics.kt.auth

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer

@EnableResourceServer
@EnableDiscoveryClient
@SpringBootApplication
class AuthServiceApplication

fun main(args: Array<String>) {
    runApplication<AuthServiceApplication>(*args)
}
