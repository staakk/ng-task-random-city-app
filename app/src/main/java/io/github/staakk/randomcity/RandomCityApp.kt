package io.github.staakk.randomcity

import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import io.github.staakk.randomcity.di.DaggerAppComponent

class RandomCityApp : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(applicationContext)
    }


}