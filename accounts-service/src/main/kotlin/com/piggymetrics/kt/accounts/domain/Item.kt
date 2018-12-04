package com.piggymetrics.kt.accounts.domain

import java.math.BigDecimal

data class Item(
        val title: String,
        val amount: BigDecimal,
        val currency: Currency,
        val period: TimePeriod,
        val icon: String
)
