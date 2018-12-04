package com.piggymetrics.kt.accounts.controllers

import com.piggymetrics.kt.accounts.domain.User
import com.piggymetrics.kt.accounts.services.AccountsService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class AccountsController(private val accountsService: AccountsService) {

    @PostMapping
    fun createNewAccount(@Valid @RequestBody user: User) = accountsService.create(user)

}
