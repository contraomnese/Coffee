package com.contraomnese.coffee.di

import com.contraomnese.coffee.BuildConfig
import com.contraomnese.coffee.data.network.api.CoffeeApi
import com.contraomnese.coffee.data.network.interceptors.AuthInterceptor
import com.contraomnese.coffee.data.repository.AuthRepositoryImpl
import com.contraomnese.coffee.data.repository.AuthTokenRepositoryImpl
import com.contraomnese.coffee.data.repository.OrderRepositoryImpl
import com.contraomnese.coffee.data.repository.LocationsRepositoryImpl
import com.contraomnese.coffee.data.repository.MenuRepositoryImpl
import com.contraomnese.coffee.data.storage.api.OrderStorage
import com.contraomnese.coffee.data.storage.api.TokenStorage
import com.contraomnese.coffee.data.storage.memory.OrderMemoryStorage
import com.contraomnese.coffee.data.storage.memory.TokenMemoryStorage
import com.contraomnese.coffee.domain.auth.repository.AuthRepository
import com.contraomnese.coffee.domain.auth.repository.AuthTokenRepository
import com.contraomnese.coffee.domain.order.repository.OrderRepository
import com.contraomnese.coffee.domain.locations.repository.LocationsRepository
import com.contraomnese.coffee.domain.menu.repository.MenuRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val dataModule = module {

    // network
    single<Gson> {
        GsonBuilder().create()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BuildConfig.COFFEE_API_BASE_URL)
            .client(get<OkHttpClient>())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }

    factory<OkHttpClient> {
        OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(get<AuthInterceptor>())
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    factory<HttpLoggingInterceptor> {
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.BASIC
            }
        }
    }

    single<AuthInterceptor> {
        AuthInterceptor(authTokenRepository = get())
    }

    single<CoffeeApi> { get<Retrofit>().create(CoffeeApi::class.java) }

    // storage
    single<TokenStorage> { TokenMemoryStorage(context = get()) }
    single <OrderStorage> { OrderMemoryStorage() }

    // repository
    single<AuthRepository> { AuthRepositoryImpl(api = get()) }
    single<AuthTokenRepository> { AuthTokenRepositoryImpl(storage = get()) }
    single<LocationsRepository> { LocationsRepositoryImpl(api = get()) }
    single<MenuRepository> { MenuRepositoryImpl(api = get()) }
    single <OrderRepository> { OrderRepositoryImpl(storage = get()) }
}