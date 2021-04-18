package io.github.staakk.randomcity.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import io.github.staakk.randomcity.data.CityDataSource
import io.github.staakk.randomcity.data.CityProducer
import io.github.staakk.randomcity.data.colorparser.ColorParser
import io.github.staakk.randomcity.data.colorparser.ResourcesColorParser
import io.github.staakk.randomcity.data.local.city.LocalCityDataSource
import io.github.staakk.randomcity.data.local.city.RandomCityDatabase
import io.github.staakk.randomcity.data.local.geocoder.Geocoder
import io.github.staakk.randomcity.data.local.geocoder.GoogleMapsGeocoder
import io.github.staakk.randomcity.di.SchedulerQualifier.Type
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

@Module
object ApplicationModule {

    @Singleton
    @Provides
    fun provideCityProducer(
        cityDataSource: CityDataSource,
        geocoder: Geocoder,
        colorParser: ColorParser,
        @SchedulerQualifier(Type.IO) scheduler: Scheduler
    ): CityProducer = CityProducer(cityDataSource, geocoder, colorParser, scheduler)

    @Singleton
    @Provides
    fun provideCityDataSource(
        database: RandomCityDatabase,
        @SchedulerQualifier(Type.IO) scheduler: Scheduler
    ): CityDataSource = LocalCityDataSource(database.cityDao(), scheduler)

    @Singleton
    @Provides
    fun provideDataBase(context: Context): RandomCityDatabase =
        Room.databaseBuilder(context, RandomCityDatabase::class.java, "RandomCity.db")
            .build()

    @Singleton
    @Provides
    fun provideGeocoder(
        context: Context,
        @SchedulerQualifier(Type.IO) scheduler: Scheduler
    ): Geocoder = GoogleMapsGeocoder(context, scheduler)

    @Singleton
    @Provides
    fun provideColorParser(context: Context): ColorParser =
        ResourcesColorParser(context)

    @Singleton
    @Provides
    @SchedulerQualifier(Type.MAIN)
    fun provideMainThreadScheduler(): Scheduler = AndroidSchedulers.mainThread()

    @Singleton
    @Provides
    @SchedulerQualifier(Type.IO)
    fun provideIoScheduler(): Scheduler = Schedulers.io()

}