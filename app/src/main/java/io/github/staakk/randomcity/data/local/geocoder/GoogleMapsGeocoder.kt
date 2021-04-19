package io.github.staakk.randomcity.data.local.geocoder

import android.content.Context
import io.github.staakk.randomcity.data.model.Coordinate
import io.reactivex.Scheduler
import io.reactivex.Single
import java.io.IOException

class GoogleMapsGeocoder(
    context: Context,
    private val scheduler: Scheduler,
    private val geocoder: android.location.Geocoder = android.location.Geocoder(context)
) : Geocoder {

    override fun nameToCoordinate(locationName: String): Single<Coordinate> =
        Single.create<Coordinate> { emitter ->
            if (locationName.isBlank()) {
                emitter.onError(Geocoder.Error.LocationNameMissing())
            }
            try {
                val addresses = geocoder.getFromLocationName(locationName, 1)
                if (addresses.isNullOrEmpty()) {
                    emitter.onError(Geocoder.Error.LocationNotFound(locationName))
                } else {
                    emitter.onSuccess(
                        addresses.first().let { Coordinate(it.latitude, it.longitude) })
                }
            } catch (exception: IOException) {
                emitter.onError(Geocoder.Error.IOException(exception))
            }
        }.subscribeOn(scheduler)
}