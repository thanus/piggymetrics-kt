package com.piggymetrics.kt.statistics.domain

enum class Currency {
    USD, EUR, RUB;
}

fun getBaseCurrency(): Currency = Currency.USD
