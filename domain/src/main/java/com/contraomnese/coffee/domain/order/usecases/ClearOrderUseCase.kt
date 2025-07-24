package com.contraomnese.coffee.domain.order.usecases

import com.contraomnese.coffee.domain.cleanarchitecture.coroutine.CoroutineContextProvider
import com.contraomnese.coffee.domain.cleanarchitecture.usecase.background.withoutRequest.BackgroundExecutingUseCase
import com.contraomnese.coffee.domain.order.repository.OrderRepository

class ClearOrderUseCase(
    private val repository: OrderRepository,
    private val coroutineContextProvider: CoroutineContextProvider,
) : BackgroundExecutingUseCase<Unit>(coroutineContextProvider) {

    override suspend fun executeInBackground() = repository.clear()
}