package io.github.staakk.randomcity.data

import io.github.staakk.randomcity.data.colorparser.ColorParser
import io.github.staakk.randomcity.data.local.geocoder.Geocoder
import io.github.staakk.randomcity.data.model.Coordinate
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.junit.Test
import java.util.concurrent.TimeUnit

class CityProducerTest {

    private val mockCityDataSource = mockk<CityDataSource>()

    private val mockGeocoder = mockk<Geocoder> {
        every { nameToCoordinate(any()) } answers {
            Single.create { it.onSuccess(Coordinate(0.0, 0.0)) }
        }
    }

    private val mockColorParser = mockk<ColorParser> {
        every { parse(any()) } returns 0
    }

    private val testScheduler = TestScheduler()

    private val tested =
        CityProducer(mockCityDataSource, mockGeocoder, mockColorParser, testScheduler)

    @Test
    fun shouldProduceCityEveryFiveSeconds() {
        tested.start()

        testScheduler.advanceTimeBy(4, TimeUnit.SECONDS)
        verify(exactly = 0) { mockCityDataSource.insert(any()) }

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        verify { mockCityDataSource.insert(any()) }

        tested.stop()
    }

    @Test
    fun shouldNotifyAboutNextEmission() {
        val emissionCompletable = tested.getEmissionObservable()

        tested.start()

        val testCompletable = emissionCompletable.test()

        testScheduler.advanceTimeBy(5, TimeUnit.SECONDS)

        testCompletable.assertComplete()

        tested.stop()
    }

    @Test
    fun shouldNotProduceCityAfterStop() {
        tested.start()
        tested.stop()

        testScheduler.advanceTimeBy(5, TimeUnit.SECONDS)
        verify(exactly = 0) { mockCityDataSource.insert(any()) }
    }
}