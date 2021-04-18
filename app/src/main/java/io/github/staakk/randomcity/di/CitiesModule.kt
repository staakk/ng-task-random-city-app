package io.github.staakk.randomcity.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import io.github.staakk.randomcity.RandomCityActivity
import io.github.staakk.randomcity.ui.city.CitiesFragment
import io.github.staakk.randomcity.ui.city.CitiesViewModel
import io.github.staakk.randomcity.ui.city.CityDetailsFragment

@Module
abstract class CitiesModule {

    @ContributesAndroidInjector(modules = [
        ViewModelBuilder::class
    ])
    abstract fun citiesFragment(): CitiesFragment

    @ContributesAndroidInjector(modules = [
        ViewModelBuilder::class
    ])
    abstract fun cityDetailsFragment(): CityDetailsFragment

    @Binds
    @IntoMap
    @ViewModelKey(CitiesViewModel::class)
    abstract fun citiesViewModel(viewModel: CitiesViewModel): ViewModel
}