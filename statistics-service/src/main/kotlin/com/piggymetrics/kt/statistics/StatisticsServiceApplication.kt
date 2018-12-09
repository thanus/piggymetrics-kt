package com.piggymetrics.kt.statistics

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.openfeign.EnableFeignClients

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
class StatisticsServiceApplication

fun main(args: Array<String>) {
    runApplication<StatisticsServiceApplication>(*args)
}
