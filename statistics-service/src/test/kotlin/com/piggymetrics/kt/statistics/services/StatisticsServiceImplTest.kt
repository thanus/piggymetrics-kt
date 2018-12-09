package com.piggymetrics.kt.statistics.services

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.piggymetrics.kt.statistics.domain.*
import com.piggymetrics.kt.statistics.domain.Currency
import com.piggymetrics.kt.statistics.domain.timeseries.DataPoint
import com.piggymetrics.kt.statistics.domain.timeseries.StatisticMetric
import com.piggymetrics.kt.statistics.repositories.DataPointRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.AdditionalAnswers.returnsFirstArg
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

class StatisticsServiceImplTest {
    companion object {
        val SALARY = Item("Salary", BigDecimal(9100), Currency.USD, TimePeriod.MONTH)
        val GROCERY = Item("Grocery", BigDecimal(500), Currency.RUB, TimePeriod.DAY)
        val VACATION = Item("Vacation", BigDecimal(3400), Currency.EUR, TimePeriod.YEAR)
        private val SAVING = Saving(BigDecimal(1000), Currency.EUR, BigDecimal(3.2), true, false)
        val ACCOUNT = Account(listOf(SALARY), listOf(GROCERY, VACATION), SAVING)
        val RATES = mapOf(
                Currency.EUR to BigDecimal("0.8"),
                Currency.RUB to BigDecimal("80"),
                Currency.USD to BigDecimal.ONE
        )
    }

    private val dataPointRepository: DataPointRepository = mock()
    private val exchangeRatesService: ExchangeRatesService = mock()
    private val statisticsService: StatisticsService = StatisticsServiceImpl(exchangeRatesService, dataPointRepository)

    @Test
    fun shouldSaveDataPointWithDataPointId() {
        stubRateServiceAndDataPointRepository()

        val dataPoint = statisticsService.save("account", ACCOUNT)

        assertEquals(dataPoint.id.account, "account")
        assertEquals(dataPoint.id.date, Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()))
    }

    @Test
    fun shouldSaveDataPointWithStatistics() {
        stubRateServiceAndDataPointRepository()

        val dataPoint = statisticsService.save("account", ACCOUNT)

        val expectedExpensesAmount = BigDecimal("17.8861")
        val expectedIncomesAmount = BigDecimal("298.9802")
        val expectedSavingAmount = BigDecimal("1250")

        assertTrue(expectedExpensesAmount.compareTo(dataPoint.statistics[StatisticMetric.EXPENSES_AMOUNT]) == 0)
        assertTrue(expectedIncomesAmount.compareTo(dataPoint.statistics[StatisticMetric.INCOMES_AMOUNT]) == 0)
        assertTrue(expectedSavingAmount.compareTo(dataPoint.statistics[StatisticMetric.SAVING_AMOUNT]) == 0)
    }

    @Test
    fun shouldSaveDataPointWithIncomesAndExpenses() {
        stubRateServiceAndDataPointRepository()

        val dataPoint = statisticsService.save("account", ACCOUNT)

        val expectedNormalizedSalaryAmount = BigDecimal("298.9802")
        val expectedNormalizedVacationAmount = BigDecimal("11.6361")
        val expectedNormalizedGroceryAmount = BigDecimal("6.25")

        val salaryItemMetric = dataPoint.incomes.first { it.title == SALARY.title }
        val vacationItemMetric = dataPoint.expenses.first { it.title == VACATION.title }
        val groceryItemMetric = dataPoint.expenses.first { it.title == GROCERY.title }

        assertTrue(expectedNormalizedSalaryAmount.compareTo(salaryItemMetric.amount) == 0)
        assertTrue(expectedNormalizedVacationAmount.compareTo(vacationItemMetric.amount) == 0)
        assertTrue(expectedNormalizedGroceryAmount.compareTo(groceryItemMetric.amount) == 0)

        assertEquals(RATES, dataPoint.rates)
    }

    private fun stubRateServiceAndDataPointRepository() {
        whenever(exchangeRatesService.getCurrentRates()).thenReturn(RATES)
        whenever(exchangeRatesService.convert(any(), any(), any()))
                .then {
                    (it.getArgument(2) as BigDecimal)
                            .divide(RATES[it.getArgument(0)], 4, RoundingMode.HALF_UP)
                }
        whenever(dataPointRepository.save(any<DataPoint>())).then(returnsFirstArg<DataPoint>())
    }
}
