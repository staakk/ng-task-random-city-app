package io.github.staakk.randomcity.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Observable

@Dao
interface CityDao {

    @Insert
    fun insert(city: City)

    @Query("SELECT * FROM city ORDER BY name DESC")
    fun getOrderedByName(): Observable<List<City>>
}