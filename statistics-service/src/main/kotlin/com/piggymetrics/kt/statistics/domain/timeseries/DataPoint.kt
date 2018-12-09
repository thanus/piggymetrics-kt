package com.piggymetrics.kt.statistics.domain.timeseries

import com.piggymetrics.kt.statistics.domain.Currency
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal

@Document(collection = "datapoints")
data class DataPoint(
        @field:Id val id: DataPointId,
        val incomes: Set<ItemMetric>,
        val expenses: Set<ItemMetric>,
        val statistics: Map<StatisticMetric, BigDecimal>,
        val rates: Map<Currency, BigDecimal>
)
