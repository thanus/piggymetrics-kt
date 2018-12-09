package com.piggymetrics.kt.statistics.repositories

import com.piggymetrics.kt.statistics.domain.timeseries.DataPoint
import com.piggymetrics.kt.statistics.domain.timeseries.DataPointId
import com.piggymetrics.kt.statistics.domain.timeseries.ItemMetric
import com.piggymetrics.kt.statistics.domain.timeseries.StatisticMetric
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.test.context.junit4.SpringRunner
import java.math.BigDecimal
import java.util.*

@DataMongoTest
@RunWith(SpringRunner::class)
class DataPointRepositoryTest {

    @Autowired
    lateinit var dataPointRepository: DataPointRepository

    @Test
    fun shouldSaveDataPoint() {
        val salary = ItemMetric("salary", BigDecimal(20000))

        val grocery = ItemMetric("grocery", BigDecimal(1000))
        val vacation = ItemMetric("vacation", BigDecimal(2000))

        val pointId = DataPointId("account", Date(0))

        val dataPoint = DataPoint(
                pointId,
                setOf(salary),
                setOf(grocery, vacation),
                mapOf(
                        StatisticMetric.SAVING_AMOUNT to BigDecimal(400000),
                        StatisticMetric.INCOMES_AMOUNT to BigDecimal(20000),
                        StatisticMetric.EXPENSES_AMOUNT to BigDecimal(3000)
                ),
                emptyMap()
        )

        dataPointRepository.save(dataPoint)

        val points = dataPointRepository.findByIdAccount(pointId.account)

        assertEquals(1, points.size)
        assertEquals(pointId.date, points[0].id.date)
        assertEquals(dataPoint.incomes.size, points[0].incomes.size)
        assertEquals(dataPoint.expenses.size, points[0].expenses.size)
        assertEquals(dataPoint.statistics.size, points[0].statistics.size)
    }

    @Test
    fun shouldRewriteDataPointWithinADay() {
        val earlyAmount = BigDecimal(100)
        val lateAmount = BigDecimal(200)

        val pointId = DataPointId("account", Date(0))

        val earlier = DataPoint(
                pointId,
                emptySet(),
                emptySet(),
                mapOf(StatisticMetric.SAVING_AMOUNT to earlyAmount),
                emptyMap()
        )

        dataPointRepository.save(earlier)

        val later = DataPoint(
                pointId,
                emptySet(),
                emptySet(),
                mapOf(StatisticMetric.SAVING_AMOUNT to lateAmount),
                emptyMap()
        )

        dataPointRepository.save(later)

        val points = dataPointRepository.findByIdAccount(pointId.account)

        assertEquals(1, points.size)
        assertEquals(lateAmount, points[0].statistics[StatisticMetric.SAVING_AMOUNT])
    }
}
