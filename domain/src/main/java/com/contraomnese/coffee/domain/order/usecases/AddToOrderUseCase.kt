package com.contraomnese.coffee.domain.order.usecases

import com.contraomnese.coffee.domain.order.repository.OrderRepository
import com.contraomnese.coffee.domain.cleanarchitecture.coroutine.CoroutineContextProvider
import com.contraomnese.coffee.domain.cleanarchitecture.usecase.background.withRequest.BackgroundExecutingUseCaseWithRequest
import com.contraomnese.coffee.domain.menu.model.MenuItem

class AddToOrderUseCase(
    private val repository: OrderRepository,
    private val coroutineContextProvider: CoroutineContextProvider,
) : BackgroundExecutingUseCaseWithRequest<MenuItem, Unit>(coroutineContextProvider) {

    override suspend fun executeInBackground(request: MenuItem) = repository.add(request)
}