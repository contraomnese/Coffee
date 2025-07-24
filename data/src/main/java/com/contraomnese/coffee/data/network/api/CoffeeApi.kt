package com.contraomnese.coffee.data.network.api

import com.contraomnese.coffee.data.network.model.LocationNetwork
import com.contraomnese.coffee.data.network.model.LoginRequest
import com.contraomnese.coffee.data.network.model.MenuItemNetwork
import com.contraomnese.coffee.data.network.model.TokenNetwork
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CoffeeApi {

    @POST("auth/login")
    suspend fun login(
        @Body body: LoginRequest
    ): TokenNetwork

    @POST("auth/register")
    suspend fun register(
        @Body body: LoginRequest
    ): TokenNetwork

    @GET("locations")
    suspend fun locations(): List<LocationNetwork>

    @GET("location/{id}/menu")
    suspend fun menuByLocationId(
        @Path("id") id: String
    ): List<MenuItemNetwork>
}