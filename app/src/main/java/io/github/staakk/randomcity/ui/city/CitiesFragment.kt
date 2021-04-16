package io.github.staakk.randomcity.ui.city

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.staakk.randomcity.data.City
import io.github.staakk.randomcity.databinding.FragmentCitiesBinding

class CitiesFragment : Fragment() {

    private var _binding: FragmentCitiesBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =  FragmentCitiesBinding.inflate(inflater, container, false)
        with(binding.cities) {
            adapter = CitiesAdapter { }.also {
                it.items = listOf(City("Poznań"), City("Warszawa"))
            }
            layoutManager = LinearLayoutManager(requireContext())
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}