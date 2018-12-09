package com.piggymetrics.kt.statistics.domain

enum class TimePeriod(val baseRatio: Double) {
    YEAR(365.2425), QUARTER(91.3106), MONTH(30.4368), DAY(1.0), HOUR(0.0416)
}
