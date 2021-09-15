package com.avit.apnamzp.ui.shopdetails;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avit.apnamzp.R;
import com.avit.apnamzp.databinding.FragmentShopDetailsBinding;
import com.avit.apnamzp.models.shop.ShopCategoryData;
import com.avit.apnamzp.models.shop.ShopItemData;

import java.util.ArrayList;
import java.util.List;

public class ShopDetailsFragment extends Fragment {

    private FragmentShopDetailsBinding binding;
    private ShopDetailsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentShopDetailsBinding.inflate(inflater,container,false);
        View root = binding.getRoot();

        List<ShopCategoryData> shopCategoryDataList = new ArrayList<>();
        List<ShopItemData> shopItemData = new ArrayList<>();

        shopItemData.add(new ShopItemData("Cake1","$100.00"));
        shopItemData.add(new ShopItemData("Cake2","$110.00"));
        shopItemData.add(new ShopItemData("Cake3","$140.00"));
        shopItemData.add(new ShopItemData("Cake4","$10.00"));

        shopCategoryDataList.add(new ShopCategoryData("Cakes",shopItemData));
        shopCategoryDataList.add(new ShopCategoryData("Burgers",shopItemData));
        shopCategoryDataList.add(new ShopCategoryData("Bakerys",shopItemData));

        binding.menuList.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        adapter = new ShopDetailsAdapter(shopCategoryDataList,getContext());
        binding.menuList.setAdapter(adapter);



        return root;
    }
}