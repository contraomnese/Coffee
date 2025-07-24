package com.contraomnese.coffee.data.network.model

data class LocationNetwork(
    val id: Int,
    val name: String,
    val point: PointMapNetwork
)

data class PointMapNetwork(
    val latitude: Double,
    val longitude: Double
)
