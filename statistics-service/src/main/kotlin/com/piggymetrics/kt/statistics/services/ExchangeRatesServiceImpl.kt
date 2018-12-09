package com.piggymetrics.kt.statistics.services

import com.piggymetrics.kt.statistics.client.ExchangeRatesClient
import com.piggymetrics.kt.statistics.domain.Currency
import com.piggymetrics.kt.statistics.domain.ExchangeRatesContainer
import com.piggymetrics.kt.statistics.domain.getBaseCurrency
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate

@Service
class ExchangeRatesServiceImpl(private val exchangeRatesClient: ExchangeRatesClient) : ExchangeRatesService {

    private var ratesContainer: ExchangeRatesContainer? = null

    override fun getCurrentRates(): Map<Currency, BigDecimal> {
        if (ratesContainer == null || ratesContainer?.date != LocalDate.now()) {
            ratesContainer = exchangeRatesClient.getRates(getBaseCurrency())
        }

        val container = ratesContainer ?: throw RuntimeException("ExchangeRatesContainer is null")

        return mapOf(
                Currency.EUR to container.rates.getValue(Currency.EUR.name),
                Currency.RUB to container.rates.getValue(Currency.RUB.name),
                Currency.USD to BigDecimal.ONE
        )
    }

    override fun convert(from: Currency, to: Currency, amount: BigDecimal): BigDecimal {
        val currentRates = getCurrentRates()
        val ratio = currentRates.getValue(to).divide(currentRates.getValue(from), 4, RoundingMode.HALF_UP)

        return amount.multiply(ratio)
    }
}
