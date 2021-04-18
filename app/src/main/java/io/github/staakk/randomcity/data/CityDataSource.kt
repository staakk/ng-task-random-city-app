package io.github.staakk.randomcity.data

import io.github.staakk.randomcity.data.model.City
import io.reactivex.Observable

interface CityDataSource {

    /**
     * Save a [city].
     */
    fun insert(city: City)

    /**
     * Get observable list of cities ordered ascending by name.
     */
    fun getOrderedByNameAsc(): Observable<List<City>>
}