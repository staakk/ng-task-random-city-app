package io.github.staakk.randomcity.ui.city

import android.graphics.drawable.ColorDrawable
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import dagger.android.support.DaggerFragment
import io.github.staakk.randomcity.R
import io.github.staakk.randomcity.data.model.toLatLng
import io.github.staakk.randomcity.databinding.FragmentCityDetailsBinding
import javax.inject.Inject

class CityDetailsFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<CitiesViewModel>({ requireActivity() }) { viewModelFactory }

    private var _binding: FragmentCityDetailsBinding? = null

    private val binding get() = _binding!!

    private var map: GoogleMap? = null

    private val onMapReadyCallback: OnMapReadyCallback = OnMapReadyCallback {
        if (!isAdded) return@OnMapReadyCallback
        map = it
        val polandBounds = LatLngBounds(
            LatLng(49.002024, 14.12298),
            LatLng(54.833333, 24.14585)
        )
        it.moveCamera(CameraUpdateFactory.newLatLngZoom(polandBounds.center, 6.5f))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (resources.getBoolean(R.bool.isTabletLandscape)) {
            parentFragmentManager.popBackStack()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCityDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment)
            .getMapAsync(onMapReadyCallback)

        viewModel.selectedCity.observe(viewLifecycleOwner) {
            getActionBar()?.apply {
                title = it.name
                setBackgroundDrawable(ColorDrawable(it.color))
            }
            focusMap(it.coordinate.toLatLng())
        }
    }

    private fun focusMap(latLng: LatLng) {
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 8f))
    }

    override fun onStart() {
        super.onStart()
        getActionBar()?.show()
    }

    override fun onStop() {
        super.onStop()
        getActionBar()?.hide()
    }

    private fun getActionBar(): ActionBar? {
        val activity = requireActivity()
        return if (activity is AppCompatActivity) {
            activity.supportActionBar
        } else {
            null
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}