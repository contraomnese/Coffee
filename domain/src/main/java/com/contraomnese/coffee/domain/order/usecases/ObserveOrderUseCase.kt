package com.contraomnese.coffee.domain.order.usecases

import com.contraomnese.coffee.domain.order.repository.OrderRepository
import com.contraomnese.coffee.domain.cleanarchitecture.usecase.StreamingUseCase
import com.contraomnese.coffee.domain.menu.model.MenuItem
import kotlinx.coroutines.flow.Flow

class ObserveOrderUseCase(
    private val repository: OrderRepository
) : StreamingUseCase<Map<MenuItem, Int>> {

    override fun invoke(): Flow<Map<MenuItem, Int>> = repository.observe()
}