package io.github.staakk.randomcity.data.local.geocoder

import io.github.staakk.randomcity.data.model.Coordinate
import io.reactivex.Single

/**
 * This class converts location name into a [Coordinate].
 */
interface Geocoder {

    /**
     * Converts [locationName] into a [Coordinate].
     *
     * @param locationName A location name.
     * @return [Coordinate] corresponding to a [locationName]. If no coordinate can be found then
     * one of the [Error]s is reported.
     */
    fun nameToCoordinate(locationName: String): Single<Coordinate>

    sealed class Error(message: String = "", cause: Throwable? = null) :
        Throwable(cause = cause, message = message) {

        /**
         * Indicates that the provided `locationName` is empty.
         */
        class LocationNameMissing : Error("`locationName` is empty.")

        /**
         * Indicates there was an IOException during geocoding.
         */
        class IOException(cause: Throwable) : Error(cause = cause)

        /**
         * Geocoder returned no results matching the `locationName`.
         */
        class LocationNotFound(name: String) : Error("`$name` cannot be resolved to a coordinate.")
    }
}