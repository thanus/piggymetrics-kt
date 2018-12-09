package com.piggymetrics.kt.statistics.services

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.piggymetrics.kt.statistics.client.ExchangeRatesClient
import com.piggymetrics.kt.statistics.domain.Currency
import com.piggymetrics.kt.statistics.domain.ExchangeRatesContainer
import com.piggymetrics.kt.statistics.domain.getBaseCurrency
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.math.BigDecimal
import java.time.LocalDate

class ExchangeRatesServiceImplTest {

    private val exchangeRatesClient: ExchangeRatesClient = mock()
    private val exchangeRatesService: ExchangeRatesService = ExchangeRatesServiceImpl(exchangeRatesClient)

    @Test
    fun shouldGetCurrentRates() {
        val ratesContainer = ExchangeRatesContainer(
                LocalDate.now(),
                Currency.USD,
                mapOf(
                        Currency.EUR.name to BigDecimal("0.8"),
                        Currency.RUB.name to BigDecimal("80")
                )
        )

        whenever(exchangeRatesClient.getRates(getBaseCurrency())).thenReturn(ratesContainer)

        val currentRates = exchangeRatesService.getCurrentRates()
        verify(exchangeRatesClient).getRates(getBaseCurrency())

        assertEquals(ratesContainer.rates[Currency.EUR.name], currentRates[Currency.EUR])
        assertEquals(ratesContainer.rates[Currency.RUB.name], currentRates[Currency.RUB])
        assertEquals(BigDecimal.ONE, currentRates[Currency.USD])
    }

    @Test
    fun shouldRequestNewRatesWhenContainerContainsOutdatedRates() {
        val container = ExchangeRatesContainer(
                LocalDate.now().minusDays(1),
                Currency.USD,
                mapOf(
                        Currency.EUR.name to BigDecimal("0.8"),
                        Currency.RUB.name to BigDecimal("80")
                )
        )

        whenever(exchangeRatesClient.getRates(getBaseCurrency())).thenReturn(container)

        exchangeRatesService.getCurrentRates()
        exchangeRatesService.getCurrentRates()

        verify(exchangeRatesClient, times(2)).getRates(getBaseCurrency())
    }

    @Test
    fun shouldConvert() {
        val ratesContainer = ExchangeRatesContainer(
                LocalDate.now(),
                Currency.USD,
                mapOf(
                        Currency.EUR.name to BigDecimal("0.8"),
                        Currency.RUB.name to BigDecimal("80")
                )
        )

        whenever(exchangeRatesClient.getRates(getBaseCurrency())).thenReturn(ratesContainer)

        val amount = BigDecimal(100)
        val expectedConversionResult = BigDecimal("1.25")

        val result = exchangeRatesService.convert(Currency.RUB, Currency.USD, amount)

        assertTrue(expectedConversionResult.compareTo(result) == 0)
    }
}
