package com.contraomnese.coffee.data.storage.api

import com.contraomnese.coffee.data.storage.model.TokenEntity
import kotlinx.coroutines.flow.Flow

interface TokenStorage {
    
    fun saveToken(tokenEntity: TokenEntity)

    fun getToken(): TokenEntity?

    fun observeToken(): Flow<TokenEntity?>

    fun clear()
}