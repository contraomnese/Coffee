package com.contraomnese.coffee.domain.auth.usecases

import com.contraomnese.coffee.domain.auth.model.Token
import com.contraomnese.coffee.domain.auth.repository.AuthTokenRepository
import com.contraomnese.coffee.domain.cleanarchitecture.coroutine.CoroutineContextProvider
import com.contraomnese.coffee.domain.cleanarchitecture.usecase.background.withRequest.BackgroundExecutingUseCaseWithRequest

class SaveAuthTokenUseCase(
    private val repository: AuthTokenRepository,
    private val coroutineContextProvider: CoroutineContextProvider,
) : BackgroundExecutingUseCaseWithRequest<Token, Unit>(coroutineContextProvider) {
    override suspend fun executeInBackground(request: Token) {
        repository.saveToken(token = request)
    }
}