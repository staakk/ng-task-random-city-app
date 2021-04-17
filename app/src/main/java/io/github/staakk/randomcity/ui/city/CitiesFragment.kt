package io.github.staakk.randomcity.ui.city

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerFragment
import io.github.staakk.randomcity.R
import io.github.staakk.randomcity.databinding.FragmentCitiesBinding
import javax.inject.Inject

class CitiesFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<CitiesViewModel>({ requireActivity() }) { viewModelFactory }

    private var _binding: FragmentCitiesBinding? = null

    private val binding get() = _binding!!

    private val citiesAdapter: CitiesAdapter = CitiesAdapter {
        viewModel.selectCity(it)

        if (!resources.getBoolean(R.bool.isTabletLandscape)) {
            parentFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container, CityDetailsFragment())
                .commit()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCitiesBinding.inflate(inflater, container, false)

        with(binding.cities) {
            adapter = citiesAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        lifecycle.addObserver(viewModel)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.cities.observe(viewLifecycleOwner) {
            citiesAdapter.items = it
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        citiesAdapter.items = emptyList()
    }
}