package com.piggymetrics.kt.statistics.domain

import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "accounts")
data class Account(
        val incomes: List<Item>,
        val expenses: List<Item>,
        val saving: Saving
)
