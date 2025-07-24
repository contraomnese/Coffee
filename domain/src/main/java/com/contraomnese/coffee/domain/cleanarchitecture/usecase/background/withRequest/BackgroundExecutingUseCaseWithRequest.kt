package com.contraomnese.coffee.domain.cleanarchitecture.usecase.background.withRequest

import com.contraomnese.coffee.domain.cleanarchitecture.coroutine.CoroutineContextProvider
import com.contraomnese.coffee.domain.cleanarchitecture.usecase.UseCaseWithRequest
import kotlinx.coroutines.withContext

abstract class BackgroundExecutingUseCaseWithRequest<REQUEST, RESULT>(
    private val coroutineContextProvider: CoroutineContextProvider,
) : UseCaseWithRequest<REQUEST, RESULT> {
    final override suspend operator fun invoke(
        input: REQUEST,
    ): RESULT {
        val result = withContext(coroutineContextProvider.io) {
            executeInBackground(input)
        }
        return result
    }

    internal abstract suspend fun executeInBackground(
        request: REQUEST,
    ): RESULT
}