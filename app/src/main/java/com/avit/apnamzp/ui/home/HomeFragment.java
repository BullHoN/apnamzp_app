package com.avit.apnamzp.ui.home;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.avit.apnamzp.R;
import com.avit.apnamzp.databinding.FragmentHomeBinding;
import com.avit.apnamzp.localdb.Cart;
import com.avit.apnamzp.localdb.User;
import com.avit.apnamzp.models.BannerData;
import com.bumptech.glide.Glide;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jama.carouselview.CarouselView;
import com.jama.carouselview.CarouselViewListener;

import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private String TAG = "HomeFragment";
    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;
    private CarouselView carouselView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.getImages(getContext());

        View root = binding.getRoot();

        setUpBannerImages();

        // Location Button
        binding.changeLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(root).navigate(R.id.action_homeFragment_to_getLocationFragment);
            }
        });

        // Search Button
        binding.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(root).navigate(R.id.action_homeFragment_to_allItemsSearchFragment);
            }
        });

        // PickUp And Drop
        binding.pickanddrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(root).navigate(R.id.action_homeFragment_to_pickUpAndDropFragment);
            }
        });

        setUpCategories();

        binding.helpline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        binding.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        binding.viewAllStores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return root;
    }

    private void setUpCategories(){

        // Backery
        binding.bakery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("categoryName","Backery & Snacks");

                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_homeFragment_to_searchFragment,bundle);
            }
        });

        // Pure veg
        binding.vegFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("categoryName","Pure Veg Resturants");
                bundle.putString("shopType","Pure Vegetarian Resturants");

                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_homeFragment_to_searchFragment,bundle);
            }
        });

        // Non Veg
        binding.nonveg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("categoryName","Non veg Resturants");

                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_homeFragment_to_searchFragment,bundle);
            }
        });

        // Fruits
        binding.fruitsandvegetables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("categoryName","Fruits & Vegatables");

                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_homeFragment_to_searchFragment,bundle);
            }
        });

        // Dairy Products
        binding.dairyproducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("categoryName","Dairy Products");

                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_homeFragment_to_searchFragment,bundle);
            }
        });

        // Grocieries
        binding.groceries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("categoryName","Groceries");

                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_homeFragment_to_searchFragment,bundle);
            }
        });

    }

    private void setUpBannerImages(){
        homeViewModel.bannerImages.observe(getViewLifecycleOwner(), new Observer<List<BannerData>>() {
            @Override
            public void onChanged(List<BannerData> bannerData) {
                carouselView = binding.bannerCarousel;
                carouselView.setSize(bannerData.size());

                Log.i(TAG, "onChanged: " + bannerData.size());

                carouselView.setCarouselViewListener(new CarouselViewListener() {
                    @Override
                    public void onBindView(View view, int position) {
                        ImageView imageView = view.findViewById(R.id.imageView);
                        Glide.with(getContext())
                                .load(bannerData.get(position).getImageURL())
                                .into(imageView);

                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Log.i(TAG, "onClick: " + position);
                            }
                        });

                    }
                });

                carouselView.show();

            }
        });
    }

    private void showTheLocationDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(false);

        builder.setTitle("Please Select Your Location In Order To Use This App");
        builder.setPositiveButton("Select Location", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_homeFragment_to_getLocationFragment);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getActivity().finish();
            }
        });

        builder.show();
    }

    @Override
    public void onResume() {
        super.onResume();

        String address = User.getGoogleMapStreetAddress(getContext());
        String houseNo = User.getHomeDetails(getContext());

        if(address.length() == 0 || houseNo.length() == 0){
            showTheLocationDialog();
        }
        else {
            binding.addressText.setText(address);
        }
    }
}