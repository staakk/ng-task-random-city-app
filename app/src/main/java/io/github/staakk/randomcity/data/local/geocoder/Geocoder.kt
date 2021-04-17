package io.github.staakk.randomcity.data.local.geocoder

import io.github.staakk.randomcity.data.model.Coordinate
import io.reactivex.Single

interface Geocoder {

    fun nameToCoordinate(locationName: String): Single<Coordinate>

    sealed class Error(message: String = "", cause: Throwable? = null) :
        Throwable(cause = cause, message = message) {

        class LocationNameMissing : Error("`locationName` is null or empty.")

        class IOException(cause: Throwable) : Error(cause = cause)

        class LocationNotFound(name: String) : Error("`$name` cannot be resolved to a coordinate.")
    }
}