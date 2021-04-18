package io.github.staakk.randomcity.data.local.city

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.github.staakk.randomcity.data.local.converter.LocalDateTimeConverter
import io.github.staakk.randomcity.data.model.City

@Database(entities = [City::class], version = 1)
@TypeConverters(LocalDateTimeConverter::class)
abstract class RandomCityDatabase : RoomDatabase() {

    abstract fun cityDao(): CityDao
}