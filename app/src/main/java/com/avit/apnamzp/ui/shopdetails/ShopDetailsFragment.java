package com.avit.apnamzp.ui.shopdetails;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avit.apnamzp.R;
import com.avit.apnamzp.databinding.FragmentShopDetailsBinding;
import com.avit.apnamzp.localdb.Cart;
import com.avit.apnamzp.models.shop.ShopCategoryData;
import com.avit.apnamzp.models.shop.ShopData;
import com.avit.apnamzp.models.shop.ShopItemData;
import com.avit.apnamzp.models.shop.ShopPricingData;
import com.bumptech.glide.Glide;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class ShopDetailsFragment extends Fragment implements ShopDetailsAdapter.onItemAdded{

    private FragmentShopDetailsBinding binding;
    private ShopDetailsAdapter shopDetailsAdapter;
    private ShopDetailsViewModel viewModel;
    private Gson gson;
    private String TAG = "ShopDetailsFragment";
    private ShopData shopData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentShopDetailsBinding.inflate(inflater,container,false);
        View root = binding.getRoot();

        viewModel = new ViewModelProvider(this).get(ShopDetailsViewModel.class);
        gson = new Gson();

        String jsonString = getArguments().getString("shopData");
        shopData = gson.fromJson(jsonString,ShopData.class);

        binding.shopName.setText(shopData.getShopName());
        binding.tagLine.setText(shopData.getTagLine());
        binding.minOrder.setText("Min Order - " + "₹" + shopData.getPricingDetails().getMinOrderPrice());

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
        shopDetailsAdapter = new ShopDetailsAdapter(new ArrayList<>(),getContext(),this);
        binding.menuList.setAdapter(shopDetailsAdapter);

        viewModel.getMutableLiveData().observe(getViewLifecycleOwner(), new Observer<ArrayList<ShopCategoryData>>() {
            @Override
            public void onChanged(ArrayList<ShopCategoryData> shopCategoryData) {
                shopDetailsAdapter.setItems(shopCategoryData);
            }
        });

        // Search Query
        binding.searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterCategories(newText);
                return false;
            }
        });



        return root;
    }

    private void filterCategories(String query){
        if(query.length() == 0){
            shopDetailsAdapter.setItems(viewModel.shopCategoryDataArrayList);
            return;
        }

        ArrayList<ShopCategoryData> filteredList = new ArrayList<>();
        for(ShopCategoryData shopCategoryData : viewModel.shopCategoryDataArrayList){
            for(ShopItemData shopItemData : shopCategoryData.getShopItemDataList()){
                if(shopItemData.getName().toLowerCase().contains(query)){
                    filteredList.add(shopCategoryData);
                    break;
                }
            }
        }

        shopDetailsAdapter.setItems(filteredList);
    }

    private void updateTheBatch(int value){
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_nav);
        BadgeDrawable badge = bottomNavigationView.getOrCreateBadge(R.id.cartFragment);
        if(value != 0) {
            badge.setVisible(true);
            badge.setNumber(value);
        }
        else {
            badge.setVisible(false);
        }
    }

    private void updateTheCartView() {
        Cart cart = Cart.getInstance(getContext());
        if(cart == null){
            binding.cartView.setVisibility(View.GONE);
            return;
        }

        int cartSize = cart.getCartSize();
        if(cartSize == 0){
            binding.cartView.setVisibility(View.GONE);
            return;
        }

        binding.cartItems.setText(cartSize + " Item In Cart");
        binding.cartView.setVisibility(View.VISIBLE);
    }
    @Override
    public Boolean showDialog(ShopItemData shopItemData,int posOfShopItem) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_choosedishtype);

        TextView titleView =  bottomSheetDialog.findViewById(R.id.itemName);
        titleView.setText("Choose The Type For " + shopItemData.getName());

        RecyclerView itemPricingType = bottomSheetDialog.findViewById(R.id.pricingType);
        itemPricingType.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        ShopItemsTypeDialogAdapter adapter = new ShopItemsTypeDialogAdapter(getContext(), shopItemData.getPricings(), new ShopItemsTypeDialogAdapter.onPriceSelectedInterface() {
            @Override
            public void selectedPrice(ShopPricingData shopPricingData) {
                ArrayList<ShopPricingData> selectedPrice = new ArrayList<>();
                selectedPrice.add(shopPricingData);

                ShopItemData cartItem = shopItemData;
                cartItem.setPricings(selectedPrice);
                cartItem.setQuantity(cartItem.getQuantity()+1);

                // Now Add This To Cart
                Cart cart = Cart.getInstance(getContext());

                if(cart == null){
                    List<ShopItemData> cartItems = new ArrayList<>();
                    cartItems.add(cartItem);
                    cart = new Cart(getContext(),shopData.getShopName(), shopData.get_id(),cartItems);
                }

                if(!cart.insertToCart(getContext(), shopData.get_id(), cartItem)){
                    // TODO: ADD A DIALOG BOX TO REPLACE THE ITEMS
                    Toasty.error(getContext(),"Please Add Items Of Same Shop Or Clear Cart",Toasty.LENGTH_SHORT)
                            .show();
                }

                updateTheBatch(cart.getCartSize());
                shopDetailsAdapter.notifyItemChanged(posOfShopItem);
                bottomSheetDialog.dismiss();
            }
        });
        itemPricingType.setAdapter(adapter);

        bottomSheetDialog.show();

        return true;
    }

    @Override
    public void removeTheBatch() {
        Cart cart = Cart.getInstance(getContext());
        int cartSize = cart.getCartSize();
        updateTheBatch(cartSize);
    }
}