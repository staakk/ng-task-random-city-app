package io.github.staakk.randomcity.data.model

import com.google.android.gms.maps.model.LatLng

data class Coordinate(
    val latitude: Double,
    val longitude: Double
)

fun Coordinate.toLatLng() = LatLng(latitude, longitude)