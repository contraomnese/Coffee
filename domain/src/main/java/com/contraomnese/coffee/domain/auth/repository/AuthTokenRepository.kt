package com.contraomnese.coffee.domain.auth.repository

import com.contraomnese.coffee.domain.auth.model.Token
import kotlinx.coroutines.flow.Flow

interface AuthTokenRepository {
    suspend fun getToken(): Token?
    suspend fun saveToken(token: Token)
    fun observeToken(): Flow<Token?>
}