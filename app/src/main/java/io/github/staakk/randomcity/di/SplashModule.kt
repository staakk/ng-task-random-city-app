package io.github.staakk.randomcity.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import io.github.staakk.randomcity.ui.splash.SplashFragment
import io.github.staakk.randomcity.ui.splash.SplashViewModel


@Module
abstract class SplashModule {

    @ContributesAndroidInjector(modules = [
        ViewModelBuilder::class
    ])
    internal abstract fun splashFragment(): SplashFragment

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    internal abstract fun bindViewModel(viewModel: SplashViewModel): ViewModel

}