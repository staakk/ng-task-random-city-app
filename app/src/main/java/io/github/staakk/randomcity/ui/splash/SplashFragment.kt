package io.github.staakk.randomcity.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.github.staakk.randomcity.R
import io.github.staakk.randomcity.ui.city.CitiesFragment

class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onStart() {
        super.onStart()

        Handler(Looper.getMainLooper()).postDelayed(
            {
                requireFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, CitiesFragment())
                    .commit()
            },
            5000
        )
    }
}