package io.github.staakk.randomcity

import androidx.lifecycle.ProcessLifecycleOwner
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import io.github.staakk.randomcity.data.CityProducer
import io.github.staakk.randomcity.di.DaggerAppComponent
import timber.log.Timber
import javax.inject.Inject

class RandomCityApp : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(applicationContext)
    }

    @Inject
    lateinit var cityProducer: CityProducer

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        ProcessLifecycleOwner.get().lifecycle.addObserver(cityProducer)
    }
}