package com.contraomnese.coffee.data.repository

import com.contraomnese.coffee.data.mappers.toDomain
import com.contraomnese.coffee.data.mappers.toStorage
import com.contraomnese.coffee.data.storage.api.OrderStorage
import com.contraomnese.coffee.domain.order.repository.OrderRepository
import com.contraomnese.coffee.domain.cleanarchitecture.exception.UnknownDomainException
import com.contraomnese.coffee.domain.menu.model.MenuItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class OrderRepositoryImpl(
    private val storage: OrderStorage,
) : OrderRepository {

    override suspend fun add(item: MenuItem) =
        try {
            storage.add(item.toStorage())
        } catch (throwable: Throwable) {
            throw UnknownDomainException(throwable)
        }

    override suspend fun remove(id: Int) =
        try {
            storage.remove(id)
        } catch (throwable: Throwable) {
            throw UnknownDomainException(throwable)
        }

    override suspend fun clear() {
        try {
            storage.clear()
        } catch (throwable: Throwable) {
            throw UnknownDomainException(throwable)
        }
    }


    override fun observe(): Flow<Map<MenuItem, Int>> =
        try {
            storage.observe()
                .map { map ->
                    map.entries.map { it.key.toDomain() to it.value }.toMap()
                }
        } catch (throwable: Throwable) {
            throw UnknownDomainException(throwable)
        }
}