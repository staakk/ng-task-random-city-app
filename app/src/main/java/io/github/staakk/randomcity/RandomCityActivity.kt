package io.github.staakk.randomcity

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerAppCompatActivity
import io.github.staakk.randomcity.ui.city.CitiesViewModel
import io.github.staakk.randomcity.ui.splash.SplashFragment
import javax.inject.Inject

class RandomCityActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<CitiesViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, SplashFragment())
                .commit()
        }

        supportActionBar?.hide()

        viewModel.selectedCity.observe(this) {
            Log.d("Asd", it.name)
        }
    }
}