package com.contraomnese.coffee.data.repository

import com.contraomnese.coffee.data.mappers.HttpExceptionMapper
import com.contraomnese.coffee.data.mappers.toDomain
import com.contraomnese.coffee.data.network.api.CoffeeApi
import com.contraomnese.coffee.domain.cleanarchitecture.exception.UnknownDomainException
import com.contraomnese.coffee.domain.menu.model.MenuItem
import com.contraomnese.coffee.domain.menu.repository.MenuRepository
import retrofit2.HttpException


class MenuRepositoryImpl(private val api: CoffeeApi) : MenuRepository {
    override suspend fun getMenu(id: Int): List<MenuItem> {
        return try {
            api.menuByLocationId(id.toString()).map { it.toDomain() }
        } catch (throwable: Throwable) {
            throw when (throwable) {
                is HttpException -> HttpExceptionMapper.map(throwable)
                else -> UnknownDomainException(throwable)
            }
        }
    }
}