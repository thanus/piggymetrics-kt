package com.piggymetrics.kt.accounts.services

import com.piggymetrics.kt.accounts.domain.Account
import com.piggymetrics.kt.accounts.domain.User

interface AccountsService {

    fun create(user: User): Account

}
