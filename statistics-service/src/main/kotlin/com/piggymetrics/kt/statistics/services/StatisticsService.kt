package com.piggymetrics.kt.statistics.services

import com.piggymetrics.kt.statistics.domain.Account
import com.piggymetrics.kt.statistics.domain.timeseries.DataPoint

interface StatisticsService {
    fun save(accountName: String, account: Account): DataPoint
}
