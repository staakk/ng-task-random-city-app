package io.github.staakk.randomcity.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.github.staakk.randomcity.data.converter.LocalDateTimeConverter

@Database(entities = [City::class], version = 1)
@TypeConverters(LocalDateTimeConverter::class)
abstract class RandomCityDatabase : RoomDatabase() {

    abstract fun cityDao(): CityDao
}