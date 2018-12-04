package com.piggymetrics.kt.accounts.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import javax.validation.Valid
import javax.validation.constraints.Size

@Document(collection = "accounts")
data class Account(
        @field:Id val name: String,
        val lastSeen: LocalDateTime,
        @field:Valid val incomes: List<Item>,
        @field:Valid val expenses: List<Item>,
        val saving: Saving,
        @field:Size(max = 20000) val note: String
)
