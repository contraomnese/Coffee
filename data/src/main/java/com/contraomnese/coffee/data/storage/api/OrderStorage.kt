package com.contraomnese.coffee.data.storage.api

import com.contraomnese.coffee.data.storage.model.MenuEntity
import kotlinx.coroutines.flow.Flow

interface OrderStorage {

    fun add(menuItem: MenuEntity)

    fun remove(id: Int)

    fun observe(): Flow<Map<MenuEntity, Int>>

    fun clear()
}