package com.contraomnese.coffee.domain.locations.usecases

import com.contraomnese.coffee.domain.cleanarchitecture.coroutine.CoroutineContextProvider
import com.contraomnese.coffee.domain.cleanarchitecture.usecase.background.withoutRequest.BackgroundExecutingUseCase
import com.contraomnese.coffee.domain.locations.model.LocationItem
import com.contraomnese.coffee.domain.locations.repository.LocationsRepository

class GetLocationsUseCase(
    private val repository: LocationsRepository,
    private val coroutineContextProvider: CoroutineContextProvider
): BackgroundExecutingUseCase<List<LocationItem>>(coroutineContextProvider) {

    override suspend fun executeInBackground(): List<LocationItem> = repository.getLocations()
}