package com.contraomnese.coffee.data.mappers

import com.contraomnese.coffee.domain.cleanarchitecture.exception.DomainException
import com.contraomnese.coffee.domain.cleanarchitecture.exception.LoginAlreadyUsedException
import com.contraomnese.coffee.domain.cleanarchitecture.exception.RequestBodyException
import com.contraomnese.coffee.domain.cleanarchitecture.exception.UnknownDomainException
import com.contraomnese.coffee.domain.cleanarchitecture.exception.UserNotAuthorizedException
import com.contraomnese.coffee.domain.cleanarchitecture.exception.UserNotExistException
import retrofit2.HttpException

object HttpExceptionMapper {
    fun map(throwable: HttpException): DomainException {
        return when (throwable.code()) {
            400 -> RequestBodyException("Bad Request")
            401 -> UserNotAuthorizedException("Unauthorized")
            404 -> UserNotExistException("User not found")
            406 -> LoginAlreadyUsedException("Login already used")
            else -> UnknownDomainException(throwable)
        }
    }
}