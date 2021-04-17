package io.github.staakk.randomcity.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import io.github.staakk.randomcity.data.CityDataSource
import io.github.staakk.randomcity.data.CityProducer
import io.github.staakk.randomcity.data.local.LocalCityDataSource
import io.github.staakk.randomcity.data.local.RandomCityDatabase
import javax.inject.Singleton

@Module
object ApplicationModule {

    @Singleton
    @Provides
    fun provideCityProducer(cityDataSource: CityDataSource): CityProducer =
        CityProducer(cityDataSource)

    @Singleton
    @Provides
    fun provideCityDataSource(database: RandomCityDatabase): CityDataSource =
        LocalCityDataSource(database.cityDao())

    @Singleton
    @Provides
    fun provideDataBase(context: Context): RandomCityDatabase =
        Room.databaseBuilder(context, RandomCityDatabase::class.java, "RandomCity.db")
            .build()
}