package com.contraomnese.coffee.data.repository

import com.contraomnese.coffee.data.mappers.HttpExceptionMapper
import com.contraomnese.coffee.data.mappers.toDomain
import com.contraomnese.coffee.data.mappers.toStorage
import com.contraomnese.coffee.data.storage.api.TokenStorage
import com.contraomnese.coffee.domain.auth.model.Token
import com.contraomnese.coffee.domain.auth.repository.AuthTokenRepository
import com.contraomnese.coffee.domain.cleanarchitecture.exception.UnknownDomainException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException

class AuthTokenRepositoryImpl(
    private val storage: TokenStorage,
): AuthTokenRepository {
    override suspend fun getToken(): Token? =
        try {
            storage.getToken()?.toDomain()
        } catch (throwable: Throwable) {
            throw when (throwable) {
                is HttpException -> HttpExceptionMapper.map(throwable)
                else -> UnknownDomainException(throwable)
            }
        }

    override suspend fun saveToken(token: Token) {
        try {
            storage.saveToken(token.toStorage())
        } catch (throwable: Throwable) {
            throw when (throwable) {
                is HttpException -> HttpExceptionMapper.map(throwable)
                else -> UnknownDomainException(throwable)
            }
        }
    }

    override fun observeToken(): Flow<Token?> = storage.observeToken().map { it?.toDomain() }
}