package com.piggymetrics.kt.accounts.repositories

import com.piggymetrics.kt.accounts.domain.Account
import org.springframework.data.repository.CrudRepository

interface AccountsRepository: CrudRepository<Account, String>
