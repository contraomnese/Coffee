package com.contraomnese.coffee.data.mappers

import com.contraomnese.coffee.data.network.model.MenuItemNetwork
import com.contraomnese.coffee.data.storage.model.MenuEntity
import com.contraomnese.coffee.domain.menu.model.MenuItem


fun MenuItemNetwork.toDomain(): MenuItem =
    MenuItem(
        id = id,
        title = name,
        imageUrl = imageUrl,
        price = price
    )

fun MenuEntity.toDomain(): MenuItem = MenuItem(
    id = id,
    title = title,
    imageUrl = imageUrl,
    price = price
)

fun MenuItem.toStorage(): MenuEntity = MenuEntity(
    id = id,
    title = title,
    imageUrl = imageUrl,
    price = price
)
