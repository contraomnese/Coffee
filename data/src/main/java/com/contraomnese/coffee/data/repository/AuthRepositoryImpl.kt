package com.contraomnese.coffee.data.repository

import com.contraomnese.coffee.data.mappers.HttpExceptionMapper
import com.contraomnese.coffee.data.mappers.toDomain
import com.contraomnese.coffee.data.network.api.CoffeeApi
import com.contraomnese.coffee.data.network.model.LoginRequest
import com.contraomnese.coffee.domain.auth.model.Email
import com.contraomnese.coffee.domain.auth.model.Password
import com.contraomnese.coffee.domain.auth.model.Token
import com.contraomnese.coffee.domain.auth.repository.AuthRepository
import com.contraomnese.coffee.domain.cleanarchitecture.exception.UnknownDomainException
import retrofit2.HttpException

class AuthRepositoryImpl(
    private val api: CoffeeApi,
) : AuthRepository {
    override suspend fun registerUser(email: Email, password: Password): Token {
        return try {
            api.register(body = LoginRequest(login = email.value, password = password.value)).toDomain()
        } catch (throwable: Throwable) {
            throw when (throwable) {
                is HttpException -> HttpExceptionMapper.map(throwable)
                else -> UnknownDomainException(throwable)
            }
        }
    }

    override suspend fun loginUser(email: Email, password: Password): Token {
        return try {
            api.login(body = LoginRequest(login = email.value, password = password.value)).toDomain()
        } catch (throwable: Throwable) {
            throw when (throwable) {
                is HttpException -> HttpExceptionMapper.map(throwable)
                else -> UnknownDomainException(throwable)
            }
        }
    }
}