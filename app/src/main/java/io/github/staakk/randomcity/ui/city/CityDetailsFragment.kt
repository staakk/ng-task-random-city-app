package io.github.staakk.randomcity.ui.city

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerFragment
import io.github.staakk.randomcity.R
import io.github.staakk.randomcity.databinding.FragmentCityDetailsBinding
import javax.inject.Inject

class CityDetailsFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<CitiesViewModel>({ requireActivity() }) { viewModelFactory }

    private var _binding: FragmentCityDetailsBinding? = null

    private val binding get() = _binding!!

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
        viewModel.selectedCity.observe(viewLifecycleOwner) {
            binding.root.text = it.name
            getActionBar()?.apply {
                title = it.name
                setBackgroundDrawable(ColorDrawable(it.color))

            }
        }
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