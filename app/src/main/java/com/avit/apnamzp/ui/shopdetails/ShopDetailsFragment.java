package com.avit.apnamzp.ui.shopdetails;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AlertDialogLayout;
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
import com.avit.apnamzp.dialogs.LoadingDialog;
import com.avit.apnamzp.localdb.Cart;
import com.avit.apnamzp.models.offer.OfferItem;
import com.avit.apnamzp.models.shop.ShopCategoryData;
import com.avit.apnamzp.models.shop.ShopData;
import com.avit.apnamzp.models.shop.ShopItemData;
import com.avit.apnamzp.models.shop.ShopPricingData;
import com.avit.apnamzp.utils.DisplayMessage;
import com.avit.apnamzp.utils.StallDelayShow;
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
    private LoadingDialog loadingDialog;
    private MiniReviewsAdapter miniReviewsAdapter;
    private List<OfferItem> currOffers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentShopDetailsBinding.inflate(inflater,container,false);
        View root = binding.getRoot();
        loadingDialog = new LoadingDialog(getActivity());
//        loadingDialog.startLoadingDialog();

        viewModel = new ViewModelProvider(this).get(ShopDetailsViewModel.class);
        gson = new Gson();

//        binding.searchBar.setBackgroundColor(getResources().getColor(R.color.secondaryTextColor));

        String jsonString = getArguments().getString("shopData");

        if(jsonString != null){
            shopData = gson.fromJson(jsonString,ShopData.class);
            viewModel.setShopData(shopData);
            viewModel.getOffersOfShop(getContext(),shopData.get_id());
        }else{
            String shopId = getArguments().getString("shopId");
            viewModel.getShopData(getContext(),shopId);
            viewModel.getOffersOfShop(getContext(),shopId);
        }

        viewModel.getMutableShopLiveData().observe(getViewLifecycleOwner(), new Observer<ShopData>() {
            @Override
            public void onChanged(ShopData currShopData) {
                shopData = currShopData;

                Log.i(TAG, "onChanged: " + shopData.isAllowSelfPickup());
                if(shopData.isAdminShopService() && !StallDelayShow.isDisplayed(currShopData.getShopName())){
                    shopStallServiceDialog();
                }

                binding.shopName.setText(shopData.getShopName());
                binding.tagLine.setText(shopData.getTagLine());
                binding.minOrder.setText("Min Order - " + "₹" + shopData.getPricingDetails().getMinOrderPrice());

                if(shopData.getPricingDetails().getMinOrderPrice().equals("0")){
                    binding.minOrder.setVisibility(View.GONE);
                }

                if(shopData.getOpen()){
                    binding.notAcceptingOrdersContainer.setVisibility(View.GONE);
                }

                Glide.with(getContext())
                        .load(shopData.getBannerImage())
                        .into(binding.backImage);

                viewModel.getDataFromServer(getContext(),shopData.getMenuItemsID());

                binding.backButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Navigation.findNavController(root).popBackStack();
                    }
                });

                binding.menuList.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
                float increasedPercentage = shopData.getIncreaseDisplayPricePercentage() * 0.01f;
                shopDetailsAdapter = new ShopDetailsAdapter(new ArrayList<>(),getContext(),ShopDetailsFragment.this,(shopData.getOpen() && shopData.isAllowCheckout()),increasedPercentage);
                binding.menuList.setAdapter(shopDetailsAdapter);

                viewModel.getMutableLiveData().observe(getViewLifecycleOwner(), new Observer<ArrayList<ShopCategoryData>>() {
                    @Override
                    public void onChanged(ArrayList<ShopCategoryData> shopCategoryData) {
                        shopDetailsAdapter.setItems(shopCategoryData);
                        binding.progressBar.setVisibility(View.GONE);
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
                        filterCategories(newText.toLowerCase());
                        return false;
                    }
                });

                binding.openReviews.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putString("shopId", shopData.get_id());
                        bundle.putString("shopName", shopData.getShopName());
                        bundle.putString("shopAddress",shopData.getAddressData().getMainAddress());
                        bundle.putString("latitude",shopData.getAddressData().getLatitude());
                        bundle.putString("longitude",shopData.getAddressData().getLongitude());

                        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_shopDetailsFragment_to_viewsAndAddressFragment,bundle);
                    }
                });

                if(shopData.getFssaiCode() != null && shopData.getFssaiCode().length() != 0){

                    binding.fssaiContainer.setVisibility(View.VISIBLE);
                    if(shopData.getFssaiCode().contains("muncipal")){
                        binding.licenceLogo.setImageResource(R.drawable.ic_muncipal);
                        binding.fssaiCode.setText("Lic. No. " + shopData.getFssaiCode().split("-")[1]);
                    }
                    else {
                        binding.fssaiCode.setText("Lic. No. " + shopData.getFssaiCode());
                    }
                }

                List<OfferItem> initialOfferItems = new ArrayList<>();
                initialOfferItems.add(OfferItem.getDiscountOffer("100",
                        shopData.getPricingDetails().getMinFreeDeliveryPrice(),
                        shopData.getShopName().toUpperCase().replace(" ","")
                        ));

                miniReviewsAdapter.replaceItems(initialOfferItems);

                if(currOffers != null) shopData.setAvailableOffers(currOffers.size());

            }
        });

        binding.miniOffersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        miniReviewsAdapter = new MiniReviewsAdapter(new ArrayList<>(),getContext());
        binding.miniOffersRecyclerView.setAdapter(miniReviewsAdapter);

        viewModel.getOffersMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<OfferItem>>() {
            @Override
            public void onChanged(List<OfferItem> offerItems) {
                currOffers = offerItems;
                if(shopData != null) shopData.setAvailableOffers(offerItems.size());
                miniReviewsAdapter.replaceItems(offerItems);
            }
        });

        return root;
    }

    private void shopStallServiceDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_stall_shop_delay,null,false);
        builder.setView(view);

        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.show();
    }

    private void filterCategories(String query){
        if(query.length() == 0){
            shopDetailsAdapter.setItems(viewModel.shopCategoryDataArrayList);
            return;
        }

//        new AsyncTask<List<ShopCategoryData>,Void,List<ShopCategoryData>>(){
//
//            @Override
//            protected List<ShopCategoryData> doInBackground(List<ShopCategoryData>... lists) {
//                List<ShopCategoryData> curr = lists[0];
//
//            }
//
//            @Override
//            protected void onPostExecute(List<ShopCategoryData> shopCategoryData) {
//                super.onPostExecute(shopCategoryData);
//            }
//        }.execute(viewModel.shopCategoryDataArrayList);

        ArrayList<ShopCategoryData> filteredList = new ArrayList<>();
        for(ShopCategoryData shopCategoryData : viewModel.shopCategoryDataArrayList){
            List<ShopItemData> filteredItems = new ArrayList<>();

            for(ShopItemData shopItemData : shopCategoryData.getShopItemDataList()){
                if(shopItemData.getName().toLowerCase().contains(query)){
                    filteredItems.add(shopItemData);
                }
            }

            if(filteredItems.size() > 0){
                ShopCategoryData curr = new ShopCategoryData(shopCategoryData.getCategoryName(),filteredItems,true);
                filteredList.add(curr);
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

        if(!shopData.getOpen()){
            DisplayMessage.warningMessage(getContext(),"Shop is Currently Closed!!",Toasty.LENGTH_SHORT);
            return false;
        }

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

                ShopItemData cartItem = new ShopItemData(shopItemData);
                cartItem.setAllPricings(cartItem.getPricings());
                cartItem.setPricings(selectedPrice);
                cartItem.setQuantity(cartItem.getQuantity()+1);

                // Now Add This To Cart
                Cart cart = Cart.getInstance(getContext());

                if(cart == null){
                    List<ShopItemData> cartItems = new ArrayList<>();
                    cartItems.add(cartItem);
                    cart = new Cart(getContext(),shopData.getShopName(),shopData.get_id(),shopData,cartItems);
                    DisplayMessage.successMessage(getContext(),cartItem.getName() + " is added to cart",Toasty.LENGTH_SHORT);
                }
                else if(!cart.insertToCart(getContext(), shopData.get_id(), cartItem)){

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                    builder.setTitle("You Cannot Add Items Of Different Shops In The Same Cart");
                    builder.setPositiveButton("Replace Cart", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Cart cart1 = Cart.getInstance(getContext());
                            List<ShopItemData> cartItems = new ArrayList<>();
                            cartItems.add(cartItem);
                            cart1.replaceCart(getContext(),shopData.getShopName(),shopData.get_id(),shopData,cartItems);
                            updateTheBatch(cart1.getCartSize());
                            shopDetailsAdapter.notifyItemChanged(posOfShopItem);

                            DisplayMessage.successMessage(getContext(),cartItem.getName() + " is added to cart",Toasty.LENGTH_SHORT);
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });

                    builder.show();
                }
                else {
                    cart.replaceCart(getContext(), shopData.getShopName(),shopData.get_id(),shopData);
                    DisplayMessage.successMessage(getContext(),cartItem.getName() + " is added to cart",Toasty.LENGTH_SHORT);
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