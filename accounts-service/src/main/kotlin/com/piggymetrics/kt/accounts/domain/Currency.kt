package com.piggymetrics.kt.accounts.domain

enum class Currency {
    USD, EUR, RUB;
}

fun getDefaultCurrency(): Currency = Currency.USD
