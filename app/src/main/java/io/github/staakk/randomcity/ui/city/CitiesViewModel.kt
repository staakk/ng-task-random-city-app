package io.github.staakk.randomcity.ui.city

import androidx.lifecycle.*
import io.github.staakk.randomcity.data.model.City
import io.github.staakk.randomcity.data.CityDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class CitiesViewModel @Inject constructor(
    private val cityDataSource: CityDataSource
) : ViewModel(), LifecycleObserver {

    private val _cities = MutableLiveData<List<City>>()

    val cities: LiveData<List<City>> = _cities

    private val _selectedCity = MutableLiveData<City>()

    val selectedCity: LiveData<City> = _selectedCity

    private val disposable: CompositeDisposable = CompositeDisposable()

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    @Suppress("unused") // Lifecycle event.
    fun observeCities() {
        cityDataSource.getOrderedByName()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { _cities.value = it },
                onError = { Timber.e(it) }
            )
            .also(disposable::add)
    }

    fun selectCity(city: City) {
        _selectedCity.value = city
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }
}