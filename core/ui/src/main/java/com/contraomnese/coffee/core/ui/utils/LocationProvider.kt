package com.contraomnese.coffee.core.ui.utils

import android.content.Context
import android.location.Location
import android.location.LocationManager

class LocationProvider(private val context: Context) {

    @Suppress("MissingPermission")
    fun getLastKnownLocation(): Location? {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.getProviders(true)
            .mapNotNull { provider -> locationManager.getLastKnownLocation(provider) }
            .maxByOrNull { it.accuracy }
    }
}