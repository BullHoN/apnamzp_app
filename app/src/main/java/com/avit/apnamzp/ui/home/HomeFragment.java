package com.avit.apnamzp.ui.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
import com.avit.apnamzp.utils.InfoConstats;
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
                String phoneNo = InfoConstats.CALLING_NUMBER;
                Intent callingIntent = new Intent();
                callingIntent.setAction(Intent.ACTION_DIAL);
                callingIntent.setData(Uri.parse("tel: " + phoneNo));
                startActivity(callingIntent);
            }
        });

        binding.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareApp();
            }
        });

        binding.viewAllStores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("categoryName","All Shops");
                bundle.putString("shopType","all");

                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_homeFragment_to_searchFragment,bundle);
            }
        });

        return root;
    }

    private void shareApp(){
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT,"Download Apna MZP App, Your Own City Official App \n One Stop Solution For All Your Delivery Needs \n https://play.google.com/store/apps/details?id=" + getActivity().getPackageName());
        startActivity(Intent.createChooser(shareIntent,"Choose One"));
    }

    private void setUpCategories(){

        // Sweets
        binding.bakery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("categoryName","Sweets & Resturants");
                bundle.putString("shopType","Sweets");

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
                bundle.putString("shopType","Non veg Resturants");

                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_homeFragment_to_searchFragment,bundle);
            }
        });

        // Fruits
        binding.thelas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("categoryName","Street Food");
                bundle.putString("shopType","Street Food");

                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_homeFragment_to_searchFragment,bundle);
            }
        });

        // Home Chefs
        binding.dairyproducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("categoryName","Home Chefs / Bakers");
                bundle.putString("shopType","Home Chefs");

                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_homeFragment_to_searchFragment,bundle);
            }
        });

        // Grocieries
        binding.groceries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("categoryName","Groceries");
                bundle.putString("shopType","Groceries");

                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_homeFragment_to_searchFragment,bundle);
            }
        });

        binding.medicines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("categoryName","Medicines");
                bundle.putString("shopType","Medicines");

                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_homeFragment_to_searchFragment,bundle);
            }
        });

        binding.parcels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_homeFragment_to_pickUpAndDropFragment);
            }
        });

    }

    private void setUpBannerImages(){
        homeViewModel.bannerImages.observe(getViewLifecycleOwner(), new Observer<List<BannerData>>() {
            @Override
            public void onChanged(List<BannerData> bannerData) {
                carouselView = binding.bannerCarousel;
                carouselView.setSize(bannerData.size());

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
                                BannerData currData = bannerData.get(position);
                                if(currData.getAction() != null && currData.getAction().equals("open_shop")){
                                    Bundle shopIdBundle = new Bundle();
                                    shopIdBundle.putString("shopId", currData.getShopId());

                                    Navigation.findNavController(binding.getRoot()).navigate(R.id.action_homeFragment_to_shopDetailsFragment,shopIdBundle);

                                }
                                else if(currData.getAction() != null && currData.getAction().equals("open_search")){
                                    Bundle shopCategoryBundle = new Bundle();
                                    shopCategoryBundle.putString("searchKey", currData.getShopId());

                                    Navigation.findNavController(binding.getRoot()).navigate(R.id.action_homeFragment_to_allItemsSearchFragment,shopCategoryBundle);
                                }
                            }
                        });

                    }
                });

                carouselView.show();

            }
        });


        showServiceClosedDialog();
    }

    private void showServiceClosedDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_service_closed,null,false);
        builder.setView(view);

        builder.setPositiveButton("Okay",null);
        builder.show();
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