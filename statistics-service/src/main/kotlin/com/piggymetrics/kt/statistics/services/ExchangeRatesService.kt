package com.piggymetrics.kt.statistics.services

import com.piggymetrics.kt.statistics.domain.Currency
import java.math.BigDecimal

interface ExchangeRatesService {
    fun getCurrentRates(): Map<Currency, BigDecimal>
    fun convert(from: Currency, to: Currency, amount: BigDecimal): BigDecimal
}
