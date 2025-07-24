package com.contraomnese.coffee.data.repository

import com.contraomnese.coffee.data.mappers.HttpExceptionMapper
import com.contraomnese.coffee.data.mappers.toDomain
import com.contraomnese.coffee.data.network.api.CoffeeApi
import com.contraomnese.coffee.domain.cleanarchitecture.exception.UnknownDomainException
import com.contraomnese.coffee.domain.locations.model.LocationItem
import com.contraomnese.coffee.domain.locations.repository.LocationsRepository
import retrofit2.HttpException

class LocationsRepositoryImpl(
    private val api: CoffeeApi
): LocationsRepository {
    override suspend fun getLocations(): List<LocationItem> {
        return try {
            api.locations().map { it.toDomain() }
        } catch (throwable: Throwable) {
            throw when (throwable) {
                is HttpException -> HttpExceptionMapper.map(throwable)
                else -> UnknownDomainException(throwable)
            }
        }
    }

}