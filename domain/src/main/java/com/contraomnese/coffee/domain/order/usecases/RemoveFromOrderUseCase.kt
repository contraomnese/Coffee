package com.contraomnese.coffee.domain.order.usecases

import com.contraomnese.coffee.domain.order.repository.OrderRepository
import com.contraomnese.coffee.domain.cleanarchitecture.coroutine.CoroutineContextProvider
import com.contraomnese.coffee.domain.cleanarchitecture.usecase.background.withRequest.BackgroundExecutingUseCaseWithRequest


class RemoveFromOrderUseCase(
    private val repository: OrderRepository,
    private val coroutineContextProvider: CoroutineContextProvider,
) : BackgroundExecutingUseCaseWithRequest<Int, Unit>(coroutineContextProvider) {

    override suspend fun executeInBackground(request: Int) = repository.remove(request)
}