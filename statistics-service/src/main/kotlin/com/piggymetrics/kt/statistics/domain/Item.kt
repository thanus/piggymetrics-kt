package com.piggymetrics.kt.statistics.domain

import java.math.BigDecimal
import javax.validation.constraints.Size

data class Item(
        @field:Size(min = 1, max = 20) val title: String,
        val amount: BigDecimal,
        val currency: Currency,
        val period: TimePeriod
)
