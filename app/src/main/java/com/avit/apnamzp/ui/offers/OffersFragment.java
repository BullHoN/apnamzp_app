package com.avit.apnamzp.ui.offers;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avit.apnamzp.databinding.FragmentOffersBinding;
import com.avit.apnamzp.localdb.Cart;
import com.avit.apnamzp.models.offer.OfferItem;

import java.util.ArrayList;
import java.util.List;


public class OffersFragment extends Fragment implements OffersAdapter.applyOfferInterface{

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

        Bundle bundle = getArguments();
        if(bundle == null){
            viewModel.getDataFromServer(getContext(),true,"");
            offersAdapter = new OffersAdapter(getContext(),false,new ArrayList<>(),this);
            binding.offersList.setAdapter(offersAdapter);
        }
        else {
            viewModel.getDataFromServer(getContext(),false,bundle.getString("shopName"));
            offersAdapter = new OffersAdapter(getContext(),true,new ArrayList<>(),this);
            binding.offersList.setAdapter(offersAdapter);
        }


        viewModel.getOffersListMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<OfferItem>>() {
            @Override
            public void onChanged(List<OfferItem> offerItems) {
                offersAdapter.changeValues(offerItems);
            }
        });

        return root;
    }

    @Override
    public void applyOffer(OfferItem offerItem) {
        Cart cart = Cart.getInstance(getContext());
        cart.setAppliedOffer(getContext(),offerItem);
        Navigation.findNavController(binding.getRoot()).popBackStack();
    }
}