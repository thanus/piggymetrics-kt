package com.piggymetrics.kt.accounts.domain

import java.math.BigDecimal

data class Saving(
        val amount: BigDecimal,
        val currency: Currency,
        val interest: BigDecimal,
        val deposit: Boolean,
        val capitalization: Boolean
)
