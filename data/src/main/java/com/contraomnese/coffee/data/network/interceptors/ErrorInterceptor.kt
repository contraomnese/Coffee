package com.contraomnese.coffee.data.network.interceptors

import com.contraomnese.coffee.data.exceptions.AppException
import com.contraomnese.coffee.domain.cleanarchitecture.exception.LoginAlreadyUsedException
import com.contraomnese.coffee.domain.cleanarchitecture.exception.RequestBodyException
import com.contraomnese.coffee.domain.cleanarchitecture.exception.UserNotAuthorizedException
import com.contraomnese.coffee.domain.cleanarchitecture.exception.UserNotExistException
import okhttp3.Interceptor
import okhttp3.Response

class ErrorInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        when (response.code) {
            400 -> throw RequestBodyException(response.message)
            401 -> throw UserNotAuthorizedException(response.message)
            404 -> throw UserNotExistException(response.message)
            406 -> throw LoginAlreadyUsedException(response.message)
        }
        if (!response.isSuccessful) throw AppException(response.message)
        return response
    }
}