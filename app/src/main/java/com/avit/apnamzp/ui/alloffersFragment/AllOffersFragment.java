package com.avit.apnamzp.ui.alloffersFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.L;
import com.avit.apnamzp.R;
import com.avit.apnamzp.databinding.FragmentAllOffersBinding;
import com.avit.apnamzp.models.offer.OfferItem;
import com.avit.apnamzp.ui.offers.OffersAdapter;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class AllOffersFragment extends Fragment {

    private FragmentAllOffersBinding binding;
    private OffersAdapter allOffersAdapter;
    private OfferFreeDeliveryAdapter offerFreeDeliveryAdapter;
    private AllOffersViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAllOffersBinding.inflate(inflater);
        viewModel = new ViewModelProvider(this).get(AllOffersViewModel.class);
        viewModel.getDataFromServer(getContext(),false,"",true);
        viewModel.getFreeDeliveryOffers(getContext());

        setupUI();



        return binding.getRoot();
    }

    void setupUI(){
        Glide.with(getContext())
                .load("https://apna-mzp-assests.s3.ap-south-1.amazonaws.com/offers_banner.png")
                .into(binding.offersHeader);

        allOffersAdapter = new OffersAdapter(getContext(), false, new ArrayList<>(), new OffersAdapter.applyOfferInterface() {
            @Override
            public void applyOffer(OfferItem offerItem) {

            }

            @Override
            public void openShop(String shopId) {
                if (shopId == null) return;

                Bundle bundle = new Bundle();
                bundle.putString("shopId",shopId);

                Navigation.findNavController(binding.getRoot()).navigate(R.id.shopDetailsFragment,bundle);
            }
        });

        binding.allOffersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false));
        binding.allOffersRecyclerView.setAdapter(allOffersAdapter);

        offerFreeDeliveryAdapter = new OfferFreeDeliveryAdapter(new ArrayList<>(), getContext(), new OfferFreeDeliveryAdapter.applyOfferInterface() {
            @Override
            public void openShop(String shopId) {
                if (shopId == null) return;

                Bundle bundle = new Bundle();
                bundle.putString("shopId",shopId);

                Navigation.findNavController(binding.getRoot()).navigate(R.id.shopDetailsFragment,bundle);
            }
        });
        binding.freeDeliveryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        binding.freeDeliveryRecyclerView.setAdapter(offerFreeDeliveryAdapter);


        viewModel.getAllOffers().observe(getViewLifecycleOwner(), new Observer<List<OfferItem>>() {
            @Override
            public void onChanged(List<OfferItem> offerItemList) {
                allOffersAdapter.changeValues(offerItemList);
            }
        });

        viewModel.getFreeDeliveryShops().observe(getViewLifecycleOwner(), new Observer<List<OfferItem>>() {
            @Override
            public void onChanged(List<OfferItem> offerItemList) {
                offerFreeDeliveryAdapter.replaceList(offerItemList);
            }
        });

    }

}