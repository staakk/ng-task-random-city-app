package io.github.staakk.randomcity.data.local.geocoder

import android.location.Address
import android.os.Build
import io.mockk.every
import io.mockk.mockk
import io.reactivex.schedulers.Schedulers
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import java.io.IOException
import java.util.*

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class GoogleMapsGeocoderTest {

    private val mockGeocoder = mockk<android.location.Geocoder>()

    private val tested = GoogleMapsGeocoder(
        context = RuntimeEnvironment.systemContext,
        scheduler = Schedulers.trampoline(),
        geocoder = mockGeocoder
    )

    private val testAddress = Address(Locale.US).apply {
        latitude = 1.0
        longitude = 2.0
    }

    @Test
    fun shouldReturnCoordinateIfResultFound() {
        every { mockGeocoder.getFromLocationName(any(), any()) } returns listOf(testAddress)

        tested.nameToCoordinate("name")
            .test()
            .assertValue {
                it.latitude == testAddress.latitude &&
                        it.longitude == testAddress.longitude
            }
            .dispose()
    }

    @Test
    fun shouldReturnErrorWhenNameMissing() {
        every { mockGeocoder.getFromLocationName(any(), any()) } returns listOf(testAddress)

        tested.nameToCoordinate("")
            .test()
            .assertError(Geocoder.Error.LocationNameMissing::class.java)
            .dispose()
    }

    @Test
    fun shouldReturnErrorWhenNoResultFound() {
        every { mockGeocoder.getFromLocationName(any(), any()) } returns emptyList()

        tested.nameToCoordinate("name")
            .test()
            .assertError(Geocoder.Error.LocationNotFound::class.java)
            .dispose()
    }

    @Test
    fun shouldReturnErrorWhenResultIsNull() {
        every { mockGeocoder.getFromLocationName(any(), any()) } returns null

        tested.nameToCoordinate("name")
            .test()
            .assertError(Geocoder.Error.LocationNotFound::class.java)
            .dispose()
    }

    @Test
    fun shouldReturnErrorWhenIOExceptionThrown() {
        val ioException = IOException()
        every { mockGeocoder.getFromLocationName(any(), any()) } answers {
            throw ioException
        }

        tested.nameToCoordinate("name")
            .test()
            .assertError {
                it is Geocoder.Error.IOException && it.cause == ioException
            }
            .dispose()
    }

}