package com.avit.apnamzp.ui.shopdetails;

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
import com.avit.apnamzp.databinding.FragmentShopDetailsBinding;
import com.avit.apnamzp.models.shop.ShopCategoryData;
import com.avit.apnamzp.models.shop.ShopData;
import com.avit.apnamzp.models.shop.ShopItemData;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ShopDetailsFragment extends Fragment {

    private FragmentShopDetailsBinding binding;
    private ShopDetailsAdapter adapter;
    private ShopDetailsViewModel viewModel;
    private Gson gson;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentShopDetailsBinding.inflate(inflater,container,false);
        View root = binding.getRoot();

        viewModel = new ViewModelProvider(this).get(ShopDetailsViewModel.class);
        gson = new Gson();

        String jsonString = getArguments().getString("shopData");
        ShopData shopData = gson.fromJson(jsonString,ShopData.class);

        Glide.with(getContext())
                .load(shopData.getBannerImage())
                .into(binding.backImage);

        viewModel.getDataFromServer(shopData.getMenuItemsID());

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(root).popBackStack();
            }
        });

        binding.menuList.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        adapter = new ShopDetailsAdapter(new ArrayList<>(),getContext());
        binding.menuList.setAdapter(adapter);

        viewModel.getMutableLiveData().observe(this, new Observer<ArrayList<ShopCategoryData>>() {
            @Override
            public void onChanged(ArrayList<ShopCategoryData> shopCategoryData) {
                adapter.setItems(shopCategoryData);
            }
        });


        return root;
    }
}