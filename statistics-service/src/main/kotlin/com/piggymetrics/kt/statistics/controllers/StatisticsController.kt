package com.piggymetrics.kt.statistics.controllers

import com.piggymetrics.kt.statistics.domain.Account
import com.piggymetrics.kt.statistics.domain.timeseries.DataPoint
import com.piggymetrics.kt.statistics.services.StatisticsService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class StatisticsController(private val statisticsService: StatisticsService) {

    @PutMapping("/{accountName}")
    fun saveAccountStatistics(@PathVariable accountName: String, @Valid @RequestBody account: Account): DataPoint =
            statisticsService.save(accountName, account)
}
