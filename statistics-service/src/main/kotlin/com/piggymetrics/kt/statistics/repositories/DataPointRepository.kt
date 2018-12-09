package com.piggymetrics.kt.statistics.repositories

import com.piggymetrics.kt.statistics.domain.timeseries.DataPoint
import com.piggymetrics.kt.statistics.domain.timeseries.DataPointId
import org.springframework.data.repository.CrudRepository

interface DataPointRepository : CrudRepository<DataPoint, DataPointId> {
    fun findByIdAccount(account: String): List<DataPoint>
}
