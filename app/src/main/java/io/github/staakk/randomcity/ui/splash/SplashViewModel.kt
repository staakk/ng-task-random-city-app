package io.github.staakk.randomcity.ui.splash

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.github.staakk.randomcity.data.CityProducer
import io.github.staakk.randomcity.di.SchedulerQualifier
import io.github.staakk.randomcity.di.SchedulerQualifier.Type
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val cityProducer: CityProducer,
    @SchedulerQualifier(Type.MAIN) private val mainScheduler: Scheduler
) : ViewModel(), LifecycleObserver {

    val cityEmitted = object : LiveData<Boolean>() {

        private var disposable: Disposable? = null

        override fun onActive() {
            super.onActive()
            cityProducer.getEmissionObservable()
                .observeOn(mainScheduler)
                .subscribeBy(
                    onError = {
                        Timber.e(it)
                        value = false
                    },
                    onComplete = {
                        value = true
                    }
                )
                .also { disposable = it }
        }

        override fun onInactive() {
            super.onInactive()
            disposable?.dispose()
        }
    }
}