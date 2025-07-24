package com.contraomnese.coffee.data.network.model

import com.google.gson.annotations.SerializedName

data class MenuItemNetwork(
    val id: Int,
    val name: String,
    @SerializedName("imageURL") val imageUrl: String,
    val price: Int
)