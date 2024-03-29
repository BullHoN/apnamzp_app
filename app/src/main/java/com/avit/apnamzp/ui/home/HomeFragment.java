package com.avit.apnamzp.ui.home;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.avit.apnamzp.R;
import com.avit.apnamzp.databinding.FragmentHomeBinding;
import com.avit.apnamzp.localdb.Cart;
import com.avit.apnamzp.localdb.SharedPrefNames;
import com.avit.apnamzp.localdb.User;
import com.avit.apnamzp.models.BannerData;
import com.avit.apnamzp.models.ServiceStatus;
import com.avit.apnamzp.models.network.NetworkResponse;
import com.avit.apnamzp.network.NetworkApi;
import com.avit.apnamzp.network.RetrofitClient;
import com.avit.apnamzp.utils.CheckNetwork;
import com.avit.apnamzp.utils.DisplayMessage;
import com.avit.apnamzp.utils.ErrorUtils;
import com.avit.apnamzp.utils.HomeDisplayAnimation;
import com.avit.apnamzp.utils.InfoConstats;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.datatransport.runtime.retries.Retries;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.imageview.ShapeableImageView;
import com.jama.carouselview.CarouselView;
import com.jama.carouselview.CarouselViewListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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

        CheckNetwork.isConnectedToInternet(getContext());
        setUpTrendingSearches();
        setUpBannerImages();
        getServiceStatus();
        setUpAnimation();

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

        binding.offersBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(binding.getRoot()).navigate(R.id.allOffersFragment);
            }
        });

        return root;
    }

    private void setUpTrendingSearches(){
        List<Pair<String,Integer>>  trendingSearches = new ArrayList<>();
        trendingSearches.add(new Pair<>("Burger",R.drawable.ic_burger));
        trendingSearches.add(new Pair<>("Pizza",R.drawable.ic_pizza));
        trendingSearches.add(new Pair<>("Dosa",R.drawable.ic_dosa));
        trendingSearches.add(new Pair<>("Chaat",R.drawable.ic_chaat));
        trendingSearches.add(new Pair<>("Chowmein",R.drawable.ic_chowmein));
        trendingSearches.add(new Pair<>("Thali",R.drawable.ic_thali));
        trendingSearches.add(new Pair<>("Biryani",R.drawable.ic_biryani));
        trendingSearches.add(new Pair<>("Paneer",R.drawable.ic_paneer));
        trendingSearches.add(new Pair<>("Cake",R.drawable.ic_cake));
        trendingSearches.add(new Pair<>("Sweets",R.drawable.ic_sweets_trendign));


        for(Pair<String,Integer> search : trendingSearches){
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_trending_search,null,false);
            TextView textView = view.findViewById(R.id.filter);
            ImageView imageView = view.findViewById(R.id.icon);

            textView.setText(search.first);
            imageView.setImageResource(search.second);

            binding.trendingSearchesContainer.addView(view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle shopCategoryBundle = new Bundle();
                    shopCategoryBundle.putString("searchKey", search.first);

                    Navigation.findNavController(binding.getRoot()).navigate(R.id.action_homeFragment_to_allItemsSearchFragment,shopCategoryBundle);
                }
            });
        }

    }

    private void setUpAnimation(){
        if(!HomeDisplayAnimation.isDisplayed()){
            String url = NetworkApi.SERVER_URL + "user/banner-animation";

            Glide.with(getContext())
                    .load("https://apna-mzp-assests.s3.ap-south-1.amazonaws.com/home_banner_animation1.gif")
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(binding.animation1);

            Glide.with(getContext())
                    .load("https://apna-mzp-assests.s3.ap-south-1.amazonaws.com/home_banner_animation1.gif")
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(binding.animation2);

            Glide.with(getContext())
                    .load("https://apna-mzp-assests.s3.ap-south-1.amazonaws.com/home_banner_animation1.gif")
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(binding.animation3);

            new CountDownTimer(4000,2000){

                @Override
                public void onTick(long l) {

                }

                @Override
                public void onFinish() {
                    binding.animation1.setVisibility(View.GONE);
                    binding.animation2.setVisibility(View.GONE);
                    binding.animation3.setVisibility(View.GONE);
                }
            }.start();

        }
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
                bundle.putString("categoryName","Veg & Non veg Resturants");
                bundle.putString("shopType","Non veg Resturants");

                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_homeFragment_to_searchFragment,bundle);
            }
        });

        // Street Food
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
//        binding.groceries.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Bundle bundle = new Bundle();
//                bundle.putString("categoryName","Groceries");
//                bundle.putString("shopType","Groceries");
//
//                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_homeFragment_to_searchFragment,bundle);
//            }
//        });

//        binding.medicines.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Bundle bundle = new Bundle();
//                bundle.putString("categoryName","Medicines");
//                bundle.putString("shopType","Medicines");
//
//                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_homeFragment_to_searchFragment,bundle);
//            }
//        });

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
                        ShapeableImageView imageView = view.findViewById(R.id.imageView);
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
//                setUpFireWorks();

            }
        });


    }

    private void getServiceStatus(){
        Retrofit retrofit = RetrofitClient.getInstance();
        NetworkApi networkApi = retrofit.create(NetworkApi.class);

        Call<ServiceStatus> call = networkApi.getServiceStatus();
        call.enqueue(new Callback<ServiceStatus>() {
            @Override
            public void onResponse(Call<ServiceStatus> call, Response<ServiceStatus> response) {
                if(!response.isSuccessful()){
                    NetworkResponse errorResponse = ErrorUtils.parseErrorResponse(response);
                    DisplayMessage.errorMessage(getContext(),errorResponse.getDesc(),Toasty.LENGTH_SHORT);
                    return;
                }

                ServiceStatus serviceStatus = response.body();
                if(!serviceStatus.isServiceOpen()){
                    showServiceClosedDialog(serviceStatus.getMessage(),serviceStatus.getType());
                }

            }

            @Override
            public void onFailure(Call<ServiceStatus> call, Throwable t) {
                DisplayMessage.errorMessage(getContext(),t.getMessage(),Toasty.LENGTH_SHORT);
            }
        });

    }

    private void showServiceClosedDialog(String message, String type){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_service_closed,null,false);
        TextView textView =  view.findViewById(R.id.service_closed_message);
        textView.setText(message);

        LottieAnimationView animationView = view.findViewById(R.id.status_animation);

        if(type.equals("rain")){
            animationView.setVisibility(View.VISIBLE);
            animationView.setAnimation(R.raw.raining_animation);
            animationView.playAnimation();
        }
        else if(type.equals("occasion")){
            animationView.setVisibility(View.VISIBLE);
            animationView.setAnimation(R.raw.occasion_animation);
            animationView.playAnimation();
        }
        else {
            view.findViewById(R.id.status_image).setVisibility(View.VISIBLE);
        }

        builder.setView(view);

        builder.setPositiveButton("Okay",null);
        builder.show();
    }

    private void showTheLocationDialog(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SharedPrefNames.SHAREDDB_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if(!sharedPreferences.getBoolean(SharedPrefNames.LOCATION_DIALOG_MESSAGE,true)){
            return;
        }
        editor.putBoolean(SharedPrefNames.LOCATION_DIALOG_MESSAGE,false);
        editor.apply();

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//        builder.setCancelable(false);

        builder.setTitle("Please Select Your Location In Order To Use This App");
        builder.setPositiveButton("Select Location", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_homeFragment_to_getLocationFragment);
            }
        });

        builder.setNegativeButton("Skip", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.show();
    }

    @Override
    public void onResume() {
        super.onResume();

        String address = User.getGoogleMapStreetAddress(getContext());


        if(address.length() == 0){
            showTheLocationDialog();
        }
        else {
            binding.addressText.setText(address);
        }
    }
}