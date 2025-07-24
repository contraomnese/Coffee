package com.contraomnese.coffee.data.storage.memory

import com.contraomnese.coffee.data.storage.api.OrderStorage
import com.contraomnese.coffee.data.storage.model.MenuEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class OrderMemoryStorage : OrderStorage {

    private val storage = mutableMapOf<MenuEntity, Int>()
    private val storageState: MutableStateFlow<Map<MenuEntity, Int>> = MutableStateFlow(mutableMapOf())

    override fun add(item: MenuEntity) {
        val existing = storage[item]

        if (existing != null) {
            storage[item] = existing + 1
        } else storage[item] = 1

        storageState.value = storage.toMap()

    }

    override fun remove(itemId: Int) {
        val item = storage.keys.find { it.id == itemId }

        if (item != null) {
            val currentCount = storage[item] ?: return

            if (currentCount <= 1) {
                storage.remove(item)
            } else {
                storage[item] = currentCount - 1
            }
            storageState.value = storage.toMap()
        }
    }

    override fun observe(): Flow<Map<MenuEntity, Int>> = storageState.asStateFlow()

    override fun clear() {
        storage.clear()
        storageState.value = storage.toMap()
    }
}