package com.avit.apnamzp.ui.offers;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avit.apnamzp.R;
import com.avit.apnamzp.databinding.FragmentOffersBinding;
import com.avit.apnamzp.models.offer.OfferItem;
import com.avit.apnamzp.models.offer.OfferViewModel;
import com.avit.apnamzp.models.offer.OffersAdapter;

import java.util.ArrayList;
import java.util.List;


public class OffersFragment extends Fragment {

    private FragmentOffersBinding binding;
    private OffersAdapter offersAdapter;
    private OfferViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentOffersBinding.inflate(inflater,container,false);
        viewModel = ViewModelProviders.of(this).get(OfferViewModel.class);
        View root = binding.getRoot();

        binding.offersList.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        offersAdapter = new OffersAdapter(getContext(),new ArrayList<>());
        binding.offersList.setAdapter(offersAdapter);

        viewModel.getOffersListMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<OfferItem>>() {
            @Override
            public void onChanged(List<OfferItem> offerItems) {
                offersAdapter.changeValues(offerItems);
            }
        });

        return root;
    }
}