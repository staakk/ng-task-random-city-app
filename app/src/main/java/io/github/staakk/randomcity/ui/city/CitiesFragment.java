package io.github.staakk.randomcity.ui.city;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import io.github.staakk.randomcity.R;
import io.github.staakk.randomcity.databinding.FragmentCitiesBinding;
import kotlin.Lazy;
import kotlin.LazyKt;

public class CitiesFragment extends DaggerFragment {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @NonNull
    private final Lazy<CitiesViewModel> viewModel = LazyKt.lazy(() ->
            new ViewModelProvider(requireActivity(), viewModelFactory)
                    .get(CitiesViewModel.class)
    );

    @Nullable
    private FragmentCitiesBinding _binding = null;

    private final CitiesAdapter citiesAdapter = new CitiesAdapter(city -> {
        getViewModel().selectCity(city);

        if (!getResources().getBoolean(R.bool.isTabletLandscape)) {
            getParentFragmentManager().beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.fragment_container, new CityDetailsFragment())
                    .commit();
        }
    });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        _binding = FragmentCitiesBinding.inflate(inflater, container, false);

        final RecyclerView citiesList = _binding.cities;
        citiesList.setAdapter(citiesAdapter);
        citiesList.setLayoutManager(new LinearLayoutManager(requireContext()));

        return getBinding().getRoot();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getLifecycle().addObserver(getViewModel());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getViewModel().getCities()
                .observe(getViewLifecycleOwner(), citiesAdapter::setItems);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        _binding = null;
        citiesAdapter.setItems(Collections.emptyList());
    }

    @NonNull
    private FragmentCitiesBinding getBinding() {
        if (_binding == null) {
            throw new  IllegalStateException("_binding is not initialised!");
        }
        return _binding;
    }

    @NonNull
    private CitiesViewModel getViewModel() {
        return viewModel.getValue();
    }
}
