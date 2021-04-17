package io.github.staakk.randomcity.data

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit
import kotlin.random.Random


class CityProducer(
    private val cityDataSource: CityDataSource
) : LifecycleObserver {

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
    fun start() {
        disposable = Observable.interval(5, TimeUnit.SECONDS)
            .observeOn(Schedulers.io())
            .map {
                val name = cities[Random.nextInt(cities.size)]
                val color = ColorParser.parse(colors[Random.nextInt(colors.size)])
                City(name = name, color = color, createdAt = LocalDateTime.now())
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
    fun stop() {
        disposable?.dispose()
        disposable = null
    }
}