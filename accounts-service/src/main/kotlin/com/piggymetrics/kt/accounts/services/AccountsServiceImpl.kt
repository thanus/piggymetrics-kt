package com.piggymetrics.kt.accounts.services

import com.piggymetrics.kt.accounts.domain.Account
import com.piggymetrics.kt.accounts.domain.Saving
import com.piggymetrics.kt.accounts.domain.User
import com.piggymetrics.kt.accounts.domain.getDefaultCurrency
import com.piggymetrics.kt.accounts.repositories.AccountsRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDateTime

@Service
class AccountsServiceImpl(private val accountsRepository: AccountsRepository) : AccountsService {

    override fun create(user: User): Account {
        val saving = Saving(BigDecimal.ONE, getDefaultCurrency(), BigDecimal.ONE, false, false)
        val account = Account(user.username, LocalDateTime.now(), emptyList(), emptyList(), saving, "")

        return accountsRepository.save(account)
    }
}
