package com.contraomnese.coffee.domain.auth.usecases

import com.contraomnese.coffee.domain.auth.model.Token
import com.contraomnese.coffee.domain.auth.repository.AuthTokenRepository
import com.contraomnese.coffee.domain.cleanarchitecture.usecase.StreamingUseCase
import kotlinx.coroutines.flow.Flow

class ObserveAuthTokenUseCase(
    private val repository: AuthTokenRepository
): StreamingUseCase<Token?> {

    override fun invoke(): Flow<Token?> = repository.observeToken()

}