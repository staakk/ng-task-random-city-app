package io.github.staakk.randomcity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.github.staakk.randomcity.ui.splash.SplashFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, SplashFragment())
                .commit()
        }
    }
}