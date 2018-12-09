package com.piggymetrics.kt.statistics.controllers

import com.piggymetrics.kt.statistics.domain.Account
import com.piggymetrics.kt.statistics.domain.timeseries.DataPoint
import com.piggymetrics.kt.statistics.services.StatisticsService
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.NotBlank

@RestController
class StatisticsController(private val statisticsService: StatisticsService) {

    @GetMapping("/{accountName}")
    fun getStatisticsByAccountName(@NotBlank @PathVariable accountName: String): List<DataPoint> =
            statisticsService.findByAccountName(accountName)

    @PutMapping("/{accountName}")
    fun saveAccountStatistics(@PathVariable accountName: String, @Valid @RequestBody account: Account) {
        statisticsService.save(accountName, account)
    }
}
