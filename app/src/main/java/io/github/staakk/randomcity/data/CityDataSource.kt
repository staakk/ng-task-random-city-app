package io.github.staakk.randomcity.data

import io.reactivex.Observable

interface CityDataSource {

    fun insert(city: City)

    fun getOrderedByName(): Observable<List<City>>
}