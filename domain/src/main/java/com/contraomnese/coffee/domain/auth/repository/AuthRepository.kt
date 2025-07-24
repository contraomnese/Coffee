package com.contraomnese.coffee.domain.auth.repository

import com.contraomnese.coffee.domain.auth.model.Email
import com.contraomnese.coffee.domain.auth.model.Password
import com.contraomnese.coffee.domain.auth.model.Token

interface AuthRepository {
    suspend fun registerUser(email: Email, password: Password): Token
    suspend fun loginUser(email: Email, password: Password): Token
}