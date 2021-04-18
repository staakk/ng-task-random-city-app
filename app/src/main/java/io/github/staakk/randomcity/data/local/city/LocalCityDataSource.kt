package io.github.staakk.randomcity.data.local.city

import io.github.staakk.randomcity.data.model.City
import io.github.staakk.randomcity.data.CityDataSource
import io.reactivex.Observable
import io.reactivex.Scheduler

class LocalCityDataSource constructor(
    private val cityDao: CityDao,
    private val scheduler: Scheduler
) : CityDataSource {

    override fun insert(city: City) {
        cityDao.insert(city)
    }

    override fun getOrderedByName(): Observable<List<City>> =
        cityDao.getOrderedByName()
            .subscribeOn(scheduler)
}