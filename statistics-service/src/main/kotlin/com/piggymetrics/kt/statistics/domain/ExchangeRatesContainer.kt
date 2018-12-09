package com.piggymetrics.kt.statistics.domain

import java.math.BigDecimal
import java.time.LocalDate

data class ExchangeRatesContainer(
        val date: LocalDate,
        val base: Currency,
        val rates: Map<String, BigDecimal>
)
