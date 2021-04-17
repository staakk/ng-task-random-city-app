package io.github.staakk.randomcity.data

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