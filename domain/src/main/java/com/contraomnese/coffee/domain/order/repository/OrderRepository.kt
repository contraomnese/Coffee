package com.contraomnese.coffee.domain.order.repository

import com.contraomnese.coffee.domain.menu.model.MenuItem
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    suspend fun add(item: MenuItem)
    fun observe(): Flow<Map<MenuItem, Int>>
    suspend fun remove(id: Int)
    suspend fun clear()
}