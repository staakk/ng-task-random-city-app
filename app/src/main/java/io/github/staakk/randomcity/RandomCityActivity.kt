package io.github.staakk.randomcity

import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import io.github.staakk.randomcity.ui.splash.SplashFragment

class RandomCityActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, SplashFragment())
                .commit()
        }

        supportActionBar?.hide()
    }
}