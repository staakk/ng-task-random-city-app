package io.github.staakk.randomcity.ui.city

import androidx.lifecycle.*
import io.github.staakk.randomcity.data.City
import javax.inject.Inject

class CitiesViewModel @Inject constructor() : ViewModel(), LifecycleObserver {

    private val _cities = MutableLiveData<List<City>>()

    val cities: LiveData<List<City>> = _cities

    private val _selectedCity = MutableLiveData<City>()

    val selectedCity: LiveData<City> = _selectedCity

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun observeCities() {
        _cities.value = listOf(City("Poznań"), City("Warszawa"))
    }

    fun selectCity(city: City) {
        _selectedCity.value = city
    }
}