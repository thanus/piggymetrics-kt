package com.piggymetrics.kt.accounts.domain

import javax.validation.constraints.Size

data class User(@field:Size(min = 3, max = 20) val username: String, @field:Size(min = 6, max = 40) val password: String)
