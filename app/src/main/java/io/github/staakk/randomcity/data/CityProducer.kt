package io.github.staakk.randomcity.data

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import io.github.staakk.randomcity.data.colorparser.ColorParser
import io.github.staakk.randomcity.data.local.geocoder.Geocoder
import io.github.staakk.randomcity.data.model.City
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit
import kotlin.random.Random

private const val CITY_PRODUCTION_INTERVAL_SECONDS = 5L

private val cities = listOf(
    "Gdańsk",
    "Warszawa",
    "Poznań",
    "Białystok",
    "Wrocław",
    "Katowice",
    "Kraków"
)
private val colors = listOf("Yellow", "Green", "Blue", "Red", "Black", "White")

class CityProducer(
    private val cityDataSource: CityDataSource,
    private val geocoder: Geocoder,
    private val colorParser: ColorParser,
    private val scheduler: Scheduler
) : LifecycleObserver {

    private var disposable: Disposable? = null

    private val publishSubject = PublishSubject.create<City>()

    fun getEmissionObservable(): Completable = Completable.create { emitter ->
        publishSubject
            .subscribeBy(
                onNext = { emitter.onComplete() },
                onError = emitter::onError
            )
            .also { emitter.setDisposable(it) }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    @Suppress("unused") // Lifecycle event.
    fun start() {
        disposable = Observable.interval(
            CITY_PRODUCTION_INTERVAL_SECONDS,
            TimeUnit.SECONDS,
            scheduler
        ).flatMap {
                val cityName = cities[Random.nextInt(cities.size)]
                geocoder.nameToCoordinate(cityName)
                    .map { it to cityName }
                    .toObservable()
            }
            .map { (coordinate, cityName) ->
                val color = colorParser.parse(colors[Random.nextInt(colors.size)])
                City(
                    name = cityName,
                    color = color,
                    createdAt = LocalDateTime.now(),
                    coordinate = coordinate
                )
            }
            .subscribeBy(
                onNext = {
                    publishSubject.onNext(it)
                    cityDataSource.insert(it)
                },
                onError = Timber::e
            )
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    @Suppress("unused") // Lifecycle event.
    fun stop() {
        disposable?.dispose()
        disposable = null
    }
}