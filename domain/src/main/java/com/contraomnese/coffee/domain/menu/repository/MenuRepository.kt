package com.contraomnese.coffee.domain.menu.repository

import com.contraomnese.coffee.domain.menu.model.MenuItem

fun interface MenuRepository {

    suspend fun getMenu(id: Int): List<MenuItem>

}