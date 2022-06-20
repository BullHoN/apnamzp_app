package com.avit.apnamzp.ui.cart;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avit.apnamzp.R;
import com.avit.apnamzp.databinding.FragmentCartBinding;
import com.avit.apnamzp.localdb.Cart;
import com.avit.apnamzp.localdb.User;
import com.avit.apnamzp.models.order.BillingDetails;
import com.avit.apnamzp.models.order.OrderItem;
import com.avit.apnamzp.network.NetworkApi;
import com.avit.apnamzp.network.RetrofitClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.nio.file.ClosedFileSystemException;

import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CartFragment extends Fragment implements CartItemsAdapter.updateBadge{

    private FragmentCartBinding binding;
    private CartItemsAdapter cartItemsAdapter;
    private String TAG = "CartFragment";
    private Boolean isServiceTypeDelivery = true;
    private OrderItem orderItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentCartBinding.inflate(inflater,container,false);
        View root = binding.getRoot();

        Cart cart = Cart.getInstance(getContext());
        orderItem = new OrderItem();

        if(cart == null || cart.getCartSize() == 0){
            return root;
        }

        binding.emptyCartView.setVisibility(View.GONE);
        binding.cartBody.setVisibility(View.VISIBLE);

        Log.i(TAG, "onCreateView: " + cart.getShopName());
        Log.i(TAG, "onCreateView: " + cart.getShopData().getTaxPercentage());

        binding.cartItems.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false));
        cartItemsAdapter = new CartItemsAdapter(getContext(),cart.getCartItems(),this);
        binding.cartItems.setAdapter(cartItemsAdapter);

        binding.houseNo.setText(User.getHomeDetails(getContext()));
        binding.rawAddress.setText(User.getGoogleMapStreetAddress(getContext()));
        binding.shopAddress.setText(cart.getShopData().getAddressData().getMainAddress());

        binding.changeLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(root).navigate(R.id.action_cartFragment_to_getLocationFragment);
            }
        });

        binding.openCouponFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        binding.openGoogleMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGoogleMaps(cart.getShopData().getAddressData().getLatitude(),cart.getShopData().getAddressData().getLongitude());
            }
        });

        // Set All The Bill Details

        int itemTotal = cart.getTotalOfItems();
        binding.itemsTotal.setText("₹" + itemTotal + ".00");
        orderItem.setItemTotal(itemTotal);

        int totalDiscount = cart.getTotalDiscount();
        binding.totalDiscount.setText("₹" + totalDiscount + ".00");
        orderItem.setTotalDiscount(totalDiscount);

        int totalTaxesAndPackingCharge = cart.getTotalPackingCharge() + cart.getTotalTaxDeduction();

        binding.taxes.setText("₹" + totalTaxesAndPackingCharge + ".00");
        orderItem.setTotalTaxesAndPackingCharge(totalTaxesAndPackingCharge);


        orderItem.setDeliveryService(true);
        binding.deliveryServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isServiceTypeDelivery = true;
                orderItem.setDeliveryService(true);

                binding.deliveryAddressView.setVisibility(View.VISIBLE);
                binding.showRouteView.setVisibility(View.GONE);

                binding.deliveryServiceButton.setBackgroundResource(R.drawable.selected_back);
                binding.deliveryServiceText.setTextColor(getResources().getColor(R.color.white));

                binding.selfPickUpServiceButton.setBackgroundResource(R.drawable.unselected_back);
                binding.selfPickUpServiceText.setTextColor(getResources().getColor(R.color.primaryColor));

                binding.totalPriceToPay.setText("₹" + orderItem.calculateTotalPrice() + ".00");
                binding.paymentButton.setText("Proceed to pay ₹" + orderItem.calculateTotalPrice() + ".00");
                binding.deliveryChargeView.setVisibility(View.VISIBLE);

            }
        });

        binding.selfPickUpServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isServiceTypeDelivery = false;
                orderItem.setDeliveryService(false);

                binding.deliveryAddressView.setVisibility(View.GONE);
                binding.showRouteView.setVisibility(View.VISIBLE);

                binding.deliveryServiceButton.setBackgroundResource(R.drawable.unselected_back);
                binding.deliveryServiceText.setTextColor(getResources().getColor(R.color.primaryColor));

                binding.selfPickUpServiceButton.setBackgroundResource(R.drawable.selected_back);
                binding.selfPickUpServiceText.setTextColor(getResources().getColor(R.color.white));

                binding.totalPriceToPay.setText("₹" + orderItem.calculateTotalPrice() + ".00");
                binding.paymentButton.setText("Proceed to pay ₹" + orderItem.calculateTotalPrice() + ".00");
                binding.deliveryChargeView.setVisibility(View.GONE);

            }
        });

        binding.clearCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cart.clearCart(getContext());
                binding.cartBody.setVisibility(View.GONE);
                binding.emptyCartView.setVisibility(View.VISIBLE);
                updateBadge(0);
            }
        });

        binding.openCouponFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("shopName", cart.getShopName());

                Navigation.findNavController(root).navigate(R.id.action_cartFragment_to_offersFragment,bundle);
            }
        });

        binding.removeOfferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cart.removeAppliedOffer(getContext());
                orderItem.setOfferDiscountedAmount(0);
                orderItem.setOfferCode(null);

                binding.totalDiscount.setText("₹" + orderItem.getDiscountWithOffer() + ".00");

                binding.totalPriceToPay.setText("₹" + orderItem.calculateTotalPrice() + ".00");
                binding.paymentButton.setText("Proceed to pay ₹" + orderItem.calculateTotalPrice() + ".00");

                binding.offerBodyView.setVisibility(View.GONE);
                binding.addCouponView.setVisibility(View.VISIBLE);

            }
        });

        binding.paymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePaymentType();
            }
        });

        return root;
    }

    private void choosePaymentType(){
        BottomSheetDialog dialog = new BottomSheetDialog(getContext());
        dialog.setContentView(R.layout.dialog_paymenttype);

        dialog.findViewById(R.id.cashOnDeliveryButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick: checkout button");
                dialog.dismiss();
                checkout(false);
            }
        });

        dialog.findViewById(R.id.onlinePaymentButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        dialog.show();
    }

    private void checkout(Boolean isPaid){
        Retrofit retrofit = RetrofitClient.getInstance();
        NetworkApi networkApi = retrofit.create(NetworkApi.class);

        Cart cart = Cart.getInstance(getContext());
        orderItem.setOrderItems(cart.getCartItems());
        orderItem.setShopID(cart.getShopID());
        orderItem.setShopCategory(cart.getShopData().getShopType());
        orderItem.setUserId(User.getPhoneNumber(getContext()));
        orderItem.setDeliveryAddress(User.getDeliveryAddress(getContext()));
        orderItem.setPaid(isPaid);
        orderItem.setBillingDetails();
        orderItem.setOrderType(0);
        orderItem.setOrderStatus(0);


        Call<Boolean> call = networkApi.checkout(orderItem);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Log.d(TAG, "onResponse: aaja bhai");
                Toasty.success(getContext(),"Order Successfull",Toasty.LENGTH_SHORT)
                        .show();
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_cartFragment_to_ordersFragment);
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toasty.error(getContext(),t.getMessage(),Toasty.LENGTH_SHORT)
                        .show();
                Log.i(TAG, "onFailure: " +  t.getMessage());
            }
        });
    }

    private void calculateDistance(String originAddress,String destinationAddress){

        Log.i(TAG, "calculateDistance: " + originAddress + " " + destinationAddress);

        Retrofit retrofit = RetrofitClient.getInstance();

        NetworkApi networkApi = retrofit.create(NetworkApi.class);
        Call<GetDistanceResponse> call = networkApi.getDistance(destinationAddress,originAddress,"AIzaSyCjGoldXj1rERZHuTyT9iebSRFc_O3YHX4");
        call.enqueue(new Callback<GetDistanceResponse>() {
            @Override
            public void onResponse(Call<GetDistanceResponse> call, Response<GetDistanceResponse> response) {
                GetDistanceResponse distanceResponse = response.body();

                int deliveryCharge = Integer.parseInt(distanceResponse.getDistance());
                orderItem.setDeliveryCharge(deliveryCharge);
                binding.deliveryCharge.setText("₹" + deliveryCharge + ".00");

                binding.totalPriceToPay.setText("₹" + orderItem.calculateTotalPrice() + ".00");
                binding.paymentButton.setText("Proceed to pay ₹" + orderItem.calculateTotalPrice() + ".00");
            }

            @Override
            public void onFailure(Call<GetDistanceResponse> call, Throwable t) {
                Toasty.error(getContext(),"Some Error Occured",Toasty.LENGTH_SHORT)
                        .show();
                Navigation.findNavController(binding.getRoot()).popBackStack();
            }
        });
    }

    private void openGoogleMaps(String latitude,String longitude){
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + latitude+","+longitude);

        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");

        startActivity(mapIntent);
    }

    @Override
    public void updateBadge(int value) {
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_nav);
        BadgeDrawable badge = bottomNavigationView.getOrCreateBadge(R.id.cartFragment);

        Cart cart = Cart.getInstance(getContext());

        if(value != 0) {
            badge.setVisible(true);
            badge.setNumber(value);

            int itemTotal = cart.getTotalOfItems();
            binding.itemsTotal.setText("₹" + itemTotal + ".00");
            orderItem.setItemTotal(itemTotal);

            int totalDiscount = cart.getTotalDiscount();
            orderItem.setTotalDiscount(totalDiscount);


            int totalTaxesAndPackingCharge = cart.getTotalPackingCharge() + cart.getTotalTaxDeduction();
            binding.taxes.setText("₹" + totalTaxesAndPackingCharge + ".00");
            orderItem.setTotalTaxesAndPackingCharge(totalTaxesAndPackingCharge);

            binding.totalPriceToPay.setText("₹" + orderItem.getTotalPay() + ".00");
            binding.paymentButton.setText("Proceed to pay ₹" + orderItem.calculateTotalPrice() + ".00");


            if(cart.getAppliedOffer() != null) {
                String offerType = cart.getAppliedOffer().getOfferType();
                if (offerType.equals("percent")) {
                    int discountAmountOn = orderItem.getItemTotal();
                    int discountedAmount = (discountAmountOn * Integer.parseInt(cart.getAppliedOffer().getDiscountPercentage())) / 100;

                    orderItem.setOfferDiscountedAmount(Math.max(Math.min(Integer.parseInt(cart.getAppliedOffer().getMaxDiscount()), discountedAmount), 0));
                    orderItem.setOfferCode(cart.getAppliedOffer().getCode());
                } else if (offerType.equals("flat")) {
                    orderItem.setOfferDiscountedAmount(Integer.parseInt(cart.getAppliedOffer().getDiscountAmount()));
                    orderItem.setOfferCode(cart.getAppliedOffer().getCode());
                }
            }

            binding.totalDiscount.setText("₹" + orderItem.getDiscountWithOffer() + ".00");

        }
        else {
            cart.clearCart(getContext());
            binding.cartBody.setVisibility(View.GONE);
            binding.emptyCartView.setVisibility(View.VISIBLE);
            badge.setVisible(false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        Cart cart = Cart.getInstance(getContext());

        if(cart == null || cart.getCartSize() == 0){
            return;
        }

        if(cart.getAppliedOffer() == null){
            binding.addCouponView.setVisibility(View.VISIBLE);
            binding.offerBodyView.setVisibility(View.GONE);
        }
        else {
            binding.addCouponView.setVisibility(View.GONE);
            binding.offerBodyView.setVisibility(View.VISIBLE);

            binding.code.setText(cart.getAppliedOffer().getCode());
            String offerType = cart.getAppliedOffer().getOfferType();
            if(offerType.equals("percent")){
                int discountAmountOn = orderItem.getItemTotal();
                int discountedAmount = (discountAmountOn * Integer.parseInt(cart.getAppliedOffer().getDiscountPercentage()))/100;

                orderItem.setOfferDiscountedAmount(Math.max(Math.min(Integer.parseInt(cart.getAppliedOffer().getMaxDiscount()),discountedAmount),0));
                orderItem.setOfferCode(cart.getAppliedOffer().getCode());
            }
            else if(offerType.equals("flat")){
                orderItem.setOfferDiscountedAmount(Integer.parseInt(cart.getAppliedOffer().getDiscountAmount()));
                orderItem.setOfferCode(cart.getAppliedOffer().getCode());
            }

        }

        binding.totalDiscount.setText("₹" + orderItem.getDiscountWithOffer() + ".00");

        if(isServiceTypeDelivery) {
            // Distance API
            String originAddress = cart.getShopData().getAddressData().getLatitude() + "%2C" + cart.getShopData().getAddressData().getLongitude();
            LatLng userLatLang = User.getLatLng(getContext());
            String destinationAddress = String.valueOf(userLatLang.latitude) + "%2C" + String.valueOf(userLatLang.longitude);

            calculateDistance(originAddress, destinationAddress);

        }
        else {
            binding.totalPriceToPay.setText("₹" + orderItem.calculateTotalPrice() + ".00");
            binding.paymentButton.setText("Proceed to pay ₹" + orderItem.calculateTotalPrice() + ".00");
        }

    }
}