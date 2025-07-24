package com.contraomnese.coffee.data.mappers

import com.contraomnese.coffee.data.network.model.LocationNetwork
import com.contraomnese.coffee.domain.locations.model.LocationItem

fun LocationNetwork.toDomain(): LocationItem =
    LocationItem(
        id = id,
        title = name,
        latitude = point.latitude,
        longitude = point.longitude
    )