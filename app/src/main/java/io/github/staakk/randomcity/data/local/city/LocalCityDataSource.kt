package io.github.staakk.randomcity.data.local.city

import io.github.staakk.randomcity.data.model.City
import io.github.staakk.randomcity.data.CityDataSource
import io.reactivex.Observable

class LocalCityDataSource constructor(
    private val cityDao: CityDao
) : CityDataSource {

    override fun insert(city: City) {
        cityDao.insert(city)
    }

    override fun getOrderedByName(): Observable<List<City>> =
        cityDao.getOrderedByName()
}