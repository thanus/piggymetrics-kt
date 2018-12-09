package com.piggymetrics.kt.accounts.repositories

import com.piggymetrics.kt.accounts.domain.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.test.context.junit4.SpringRunner
import java.math.BigDecimal
import java.time.LocalDateTime

@DataMongoTest
@RunWith(SpringRunner::class)
class AccountsRepositoryTest {

    @Autowired
    lateinit var accountsRepository: AccountsRepository

    @Test
    fun shouldSaveAccount() {
        val account = accountsRepository.save(getStubAccount())

        assertTrue(accountsRepository.findById(account.name).isPresent)
        assertEquals(account, accountsRepository.findById(account.name).get())
        assertEquals(1, accountsRepository.count())
    }

    private fun getStubAccount(): Account {
        val saving = Saving(BigDecimal(1500), Currency.USD, BigDecimal("3.32"), true, false)

        val vacation = Item("Vacation", BigDecimal(3400), Currency.EUR, TimePeriod.YEAR, "tourism")
        val grocery = Item("Grocery", BigDecimal(10), Currency.USD, TimePeriod.DAY, "meal")
        val salary = Item("Salary", BigDecimal(9100), Currency.USD, TimePeriod.MONTH, "wallet")

        return Account("test", LocalDateTime.now(), listOf(grocery, vacation), listOf(salary), saving, "test note")
    }
}
