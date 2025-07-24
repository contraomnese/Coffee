package com.contraomnese.coffee.data.network.interceptors

import com.contraomnese.coffee.domain.auth.repository.AuthTokenRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val authTokenRepository: AuthTokenRepository
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            authTokenRepository.getToken()
        }
        val request = if (token != null) {
            chain.request().newBuilder()
                .addHeader("Authorization", "Bearer ${token.token}")
                .build()
        } else {
            chain.request()
        }
        return chain.proceed(request)
    }
}