package com.piggymetrics.kt.accounts.services

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.piggymetrics.kt.accounts.domain.Account
import com.piggymetrics.kt.accounts.domain.Saving
import com.piggymetrics.kt.accounts.domain.User
import com.piggymetrics.kt.accounts.domain.getDefaultCurrency
import com.piggymetrics.kt.accounts.repositories.AccountsRepository
import org.junit.Assert.*
import org.junit.Test
import java.math.BigDecimal
import java.time.LocalDateTime

class AccountsServiceImplTest {

    private val accountsRepository: AccountsRepository = mock()
    private val accountsService: AccountsService = AccountsServiceImpl(accountsRepository)

    @Test
    fun shouldCreateAccountForUser() {
        val user = User("username", "password")

        val saving = Saving(BigDecimal.ONE, getDefaultCurrency(), BigDecimal.ONE, false, false)
        val account = Account(user.username, LocalDateTime.now(), emptyList(), emptyList(), saving, "")

        whenever(accountsRepository.save(any<Account>())).thenReturn(account)

        val savedAccount = accountsService.create(user)

        assertEquals(BigDecimal.ONE, savedAccount.saving.amount)
        assertEquals(getDefaultCurrency(), savedAccount.saving.currency)
        assertEquals(BigDecimal.ONE, savedAccount.saving.interest)
        assertFalse(savedAccount.saving.deposit)
        assertFalse(savedAccount.saving.capitalization)

        assertEquals(user.username, savedAccount.name)
        assertNotNull(savedAccount.lastSeen)
        assertEquals(0, savedAccount.incomes.size)
        assertEquals(0, savedAccount.expenses.size)
        assertEquals("", savedAccount.note)
    }
}
