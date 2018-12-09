package com.piggymetrics.kt.statistics.client

import com.piggymetrics.kt.statistics.domain.Currency
import com.piggymetrics.kt.statistics.domain.ExchangeRatesContainer
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(url = "\${rates.url}", name = "rates-client")
interface ExchangeRatesClient {

    @RequestMapping(method = [RequestMethod.GET], value = ["/latest"])
    fun getRates(@RequestParam("base") base: Currency): ExchangeRatesContainer
}
