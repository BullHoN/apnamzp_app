package com.avit.apnamzp.ui.viewsandaddress;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avit.apnamzp.R;
import com.avit.apnamzp.databinding.FragmentViewsAndAddressBinding;
import com.avit.apnamzp.models.ReviewData;

import java.util.ArrayList;
import java.util.List;

public class ViewsAndAddressFragment extends Fragment {

    private FragmentViewsAndAddressBinding binding;
    private ViewsAndAddressViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentViewsAndAddressBinding.inflate(inflater,container,false);
        viewModel = new ViewModelProvider(this).get(ViewsAndAddressViewModel.class);

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(binding.getRoot()).popBackStack();
            }
        });

        Bundle bundle = getArguments();
        String shopName = bundle.getString("shopName");
        String shopAddress = bundle.getString("shopAddress");
        String latitude = bundle.getString("latitude");
        String longitude = bundle.getString("longitude");

        binding.shopAddress.setText(shopAddress);
        binding.openGoogleMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGoogleMaps(latitude,longitude);
            }
        });

        viewModel.getReviewsFromServer(getContext(),shopName);

        binding.reviewsList.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        ReviewsAdapter adapter = new ReviewsAdapter(getContext(),new ArrayList<>());
        binding.reviewsList.setAdapter(adapter);

        viewModel.getMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<ReviewData>>() {
            @Override
            public void onChanged(List<ReviewData> reviewData) {
                adapter.changeData(reviewData);
                binding.progressBar.setVisibility(View.GONE);
            }
        });

        return binding.getRoot();
    }

    private void openGoogleMaps(String latitude,String longitude){
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + latitude+","+longitude);

        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");

        startActivity(mapIntent);
    }

}