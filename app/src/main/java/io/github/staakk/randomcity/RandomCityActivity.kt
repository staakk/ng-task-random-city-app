package io.github.staakk.randomcity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.github.staakk.randomcity.databinding.ActivityRandomCityBinding
import io.github.staakk.randomcity.ui.splash.SplashFragment

class RandomCityActivity : AppCompatActivity() {

    private var _binding: ActivityRandomCityBinding? = null

    private val binding: ActivityRandomCityBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRandomCityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, SplashFragment())
                .commit()
        }

        setSupportActionBar(binding.toolbar)

        supportActionBar?.hide()
    }
}