package com.contraomnese.coffee.domain.locations.repository

import com.contraomnese.coffee.domain.locations.model.LocationItem

fun interface LocationsRepository {

    suspend fun getLocations(): List<LocationItem>

}