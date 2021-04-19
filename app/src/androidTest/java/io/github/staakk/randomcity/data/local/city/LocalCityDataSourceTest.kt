package io.github.staakk.randomcity.data.local.city

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.github.staakk.randomcity.data.CityDataSource
import io.github.staakk.randomcity.data.model.City
import io.github.staakk.randomcity.data.model.Coordinate
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDateTime


class LocalCityDataSourceTest {

    private lateinit var database: RandomCityDatabase

    private lateinit var tested: CityDataSource

    @Rule
    @JvmField
    var instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RandomCityDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        tested = LocalCityDataSource(database.cityDao(), Schedulers.trampoline())
    }

    @Test
    fun shouldEmitUpdateWhenCityInserted() {
        val city = City(
            name = "test",
            color = 0,
            createdAt = LocalDateTime.now().withNano(0),
            coordinate = Coordinate(0.0, 0.0)
        )

        val testObserver = tested.getOrderedByNameAsc()
            .test()

        tested.insert(city)

        testObserver.assertValues(emptyList(), listOf(city))
        testObserver.assertNoErrors()

        testObserver.dispose()
    }

    @Test
    fun shouldReturnCitiesInAlphabeticalOrder() {
        val cityA = City(
            name = "A",
            color = 0,
            createdAt = LocalDateTime.now().withNano(0),
            coordinate = Coordinate(0.0, 0.0)
        )
        val cityB = City(
            name = "B",
            color = 0,
            createdAt = LocalDateTime.now().withNano(0),
            coordinate = Coordinate(0.0, 0.0)
        )

        tested.insert(cityA)
        tested.insert(cityB)

        val testObserver = tested.getOrderedByNameAsc()
            .test()

        testObserver.assertValues(listOf(cityA, cityB))
        testObserver.assertNoErrors()

        testObserver.dispose()
    }
}