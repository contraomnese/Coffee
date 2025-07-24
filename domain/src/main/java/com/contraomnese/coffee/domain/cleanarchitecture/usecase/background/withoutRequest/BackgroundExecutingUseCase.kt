package com.contraomnese.coffee.domain.cleanarchitecture.usecase.background.withoutRequest

import com.contraomnese.coffee.domain.cleanarchitecture.coroutine.CoroutineContextProvider
import com.contraomnese.coffee.domain.cleanarchitecture.usecase.UseCase
import kotlinx.coroutines.withContext

abstract class BackgroundExecutingUseCase<RESULT>(
    private val coroutineContextProvider: CoroutineContextProvider
) : UseCase<RESULT> {
    final override suspend fun invoke(): RESULT {
        val result = withContext(coroutineContextProvider.io) {
            executeInBackground()
        }
        return result
    }

    internal abstract suspend fun executeInBackground(): RESULT
}