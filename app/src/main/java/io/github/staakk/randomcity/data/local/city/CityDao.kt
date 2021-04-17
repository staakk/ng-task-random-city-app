package io.github.staakk.randomcity.data.local.city

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.github.staakk.randomcity.data.model.City
import io.reactivex.Observable

@Dao
interface CityDao {

    @Insert
    fun insert(city: City)

    @Query("SELECT * FROM city ORDER BY name ASC")
    fun getOrderedByName(): Observable<List<City>>
}