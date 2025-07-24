package com.contraomnese.coffee.domain.auth.usecases

import com.contraomnese.coffee.domain.auth.model.Token
import com.contraomnese.coffee.domain.auth.repository.AuthTokenRepository
import com.contraomnese.coffee.domain.cleanarchitecture.coroutine.CoroutineContextProvider
import com.contraomnese.coffee.domain.cleanarchitecture.exception.AuthTokenNotFoundException
import com.contraomnese.coffee.domain.cleanarchitecture.usecase.background.withoutRequest.BackgroundExecutingUseCase

class GetAuthTokenUseCase(
    private val repository: AuthTokenRepository,
    private val coroutineContextProvider: CoroutineContextProvider
): BackgroundExecutingUseCase<Token>(coroutineContextProvider) {

    override suspend fun executeInBackground(): Token {
        return repository.getToken() ?: throw AuthTokenNotFoundException(message = "Token not found")
    }
}