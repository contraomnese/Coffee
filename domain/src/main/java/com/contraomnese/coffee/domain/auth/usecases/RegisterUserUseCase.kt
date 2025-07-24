package com.contraomnese.coffee.domain.auth.usecases

import com.contraomnese.coffee.domain.auth.model.Token
import com.contraomnese.coffee.domain.cleanarchitecture.coroutine.CoroutineContextProvider
import com.contraomnese.coffee.domain.cleanarchitecture.usecase.background.withRequest.BackgroundExecutingUseCaseWithRequest
import com.contraomnese.coffee.domain.auth.model.UserParams
import com.contraomnese.coffee.domain.auth.repository.AuthRepository

class RegisterUserUseCase(
    private val repository: AuthRepository,
    private val coroutineContextProvider: CoroutineContextProvider,
) : BackgroundExecutingUseCaseWithRequest<UserParams, Token>(coroutineContextProvider) {

    override suspend fun executeInBackground(request: UserParams): Token =
        repository.registerUser(email = request.login, password = request.password)

}