package com.contraomnese.coffee.core.ui.utils

import android.location.Location
import kotlin.math.roundToInt

object DistanceCalculator {

    fun distanceInMeters(
        userLat: Double,
        userLon: Double,
        targetLat: Double,
        targetLon: Double
    ): Int {
        val results = FloatArray(1)
        Location.distanceBetween(userLat, userLon, targetLat, targetLon, results)
        return results[0].roundToInt()
    }

    fun distanceInKilometers(
        userLat: Double,
        userLon: Double,
        targetLat: Double,
        targetLon: Double
    ): Double {
        val meters = distanceInMeters(userLat, userLon, targetLat, targetLon)
        return (meters / 100.0).roundToInt() / 10.0
    }
}