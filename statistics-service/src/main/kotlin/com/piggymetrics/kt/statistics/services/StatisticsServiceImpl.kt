package com.piggymetrics.kt.statistics.services

import com.piggymetrics.kt.statistics.domain.Account
import com.piggymetrics.kt.statistics.domain.Item
import com.piggymetrics.kt.statistics.domain.Saving
import com.piggymetrics.kt.statistics.domain.getBaseCurrency
import com.piggymetrics.kt.statistics.domain.timeseries.DataPoint
import com.piggymetrics.kt.statistics.domain.timeseries.DataPointId
import com.piggymetrics.kt.statistics.domain.timeseries.ItemMetric
import com.piggymetrics.kt.statistics.domain.timeseries.StatisticMetric
import com.piggymetrics.kt.statistics.repositories.DataPointRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

@Service
class StatisticsServiceImpl(private val ratesService: ExchangeRatesService,
                            private val dataPointRepository: DataPointRepository) : StatisticsService {

    override fun save(accountName: String, account: Account): DataPoint {
        val instant = LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()
        val dataPointId = DataPointId(accountName, Date.from(instant))

        val incomes = account.incomes.map { createItemMetric(it) }.toSet()
        val expenses = account.expenses.map { createItemMetric(it) }.toSet()

        val statistics = createStatisticsMetric(incomes, expenses, account.saving)

        val dataPoint = DataPoint(dataPointId, incomes, expenses, statistics, ratesService.getCurrentRates())
        return dataPointRepository.save(dataPoint)
    }

    private fun createStatisticsMetric(incomes: Set<ItemMetric>, expenses: Set<ItemMetric>, saving: Saving): Map<StatisticMetric, BigDecimal> {
        val savingAmount = ratesService.convert(saving.currency, getBaseCurrency(), saving.amount)

        val expensesAmount = expenses.map { it.amount }.fold(BigDecimal.ZERO, BigDecimal::add)
        val incomesAmount = incomes.map { it.amount }.fold(BigDecimal.ZERO, BigDecimal::add)

        return mapOf(
                StatisticMetric.EXPENSES_AMOUNT to expensesAmount,
                StatisticMetric.INCOMES_AMOUNT to incomesAmount,
                StatisticMetric.SAVING_AMOUNT to savingAmount
        )
    }

    private fun createItemMetric(item: Item): ItemMetric {
        val amount = ratesService.convert(item.currency, getBaseCurrency(), item.amount)
                .divide(item.period.baseRatio.toBigDecimal(), 4, RoundingMode.HALF_UP)

        return ItemMetric(item.title, amount)
    }
}
