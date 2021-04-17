package io.github.staakk.randomcity.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import io.github.staakk.randomcity.data.CityDataSource
import io.github.staakk.randomcity.data.CityProducer
import io.github.staakk.randomcity.data.local.city.LocalCityDataSource
import io.github.staakk.randomcity.data.local.city.RandomCityDatabase
import io.github.staakk.randomcity.data.local.geocoder.Geocoder
import io.github.staakk.randomcity.data.local.geocoder.GoogleMapsGeocoder
import javax.inject.Singleton

@Module
object ApplicationModule {

    @Singleton
    @Provides
    fun provideCityProducer(
        cityDataSource: CityDataSource,
        geocoder: Geocoder
    ): CityProducer = CityProducer(cityDataSource, geocoder)

    @Singleton
    @Provides
    fun provideCityDataSource(database: RandomCityDatabase): CityDataSource =
        LocalCityDataSource(database.cityDao())

    @Singleton
    @Provides
    fun provideDataBase(context: Context): RandomCityDatabase =
        Room.databaseBuilder(context, RandomCityDatabase::class.java, "RandomCity.db")
            .build()

    @Singleton
    @Provides
    fun provideGeocoder(context: Context): Geocoder =
        GoogleMapsGeocoder(context)
}