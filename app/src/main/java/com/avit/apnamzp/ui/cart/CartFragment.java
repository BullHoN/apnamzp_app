package com.avit.apnamzp.ui.cart;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avit.apnamzp.R;
import com.avit.apnamzp.databinding.FragmentCartBinding;
import com.avit.apnamzp.localdb.Cart;
import com.avit.apnamzp.localdb.User;
import com.avit.apnamzp.models.cart.CartMetaData;
import com.avit.apnamzp.models.network.NetworkResponse;
import com.avit.apnamzp.models.order.BillingDetails;
import com.avit.apnamzp.models.order.OrderItem;
import com.avit.apnamzp.models.order.ProcessingFee;
import com.avit.apnamzp.models.payment.OnlinePaymentOrderIdPostData;
import com.avit.apnamzp.models.payment.PaymentMetadata;
import com.avit.apnamzp.models.shop.ShopItemData;
import com.avit.apnamzp.models.shop.ShopPricingData;
import com.avit.apnamzp.network.NetworkApi;
import com.avit.apnamzp.network.RetrofitClient;
import com.avit.apnamzp.ui.payment.OnlinePaymentActivity;
import com.avit.apnamzp.ui.shopdetails.ShopItemsTypeDialogAdapter;
import com.avit.apnamzp.utils.ErrorUtils;
import com.avit.apnamzp.utils.PrettyStrings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.nio.file.ClosedFileSystemException;
import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CartFragment extends Fragment implements CartItemsAdapter.updateBadge {

    private FragmentCartBinding binding;
    private CartItemsAdapter cartItemsAdapter;
    private String TAG = "CartFragment";
    private Boolean isServiceTypeDelivery = true;
    private OrderItem orderItem;
    private CartItemsOnTheWayAdapter cartItemsOnTheWayAdapter;
    private CartMetaData cartMetaData;
    private Cart cart;
    private Gson gson;
    private String orderPaymentId;
    private GetDistanceResponse distanceResponse;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentCartBinding.inflate(inflater,container,false);
        View root = binding.getRoot();

        cart = Cart.getInstance(getContext());
        orderItem = new OrderItem();
        gson = new Gson();

        if(cart == null || cart.getCartSize() == 0){
            return root;
        }

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("shopId",cart.getShopID());
                Navigation.findNavController(binding.getRoot()).navigate(R.id.shopDetailsFragment,bundle);
            }
        });

        binding.loading.setAnimation(R.raw.cart_loading_animation);
        binding.loading.playAnimation();

        orderItem.setShopData(cart.getShopData());

        getCartMetaData();

        binding.emptyCartView.setVisibility(View.GONE);

        Log.i(TAG, "onCreateView: " + cart.getShopName());

        binding.cartItems.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false));
        cartItemsAdapter = new CartItemsAdapter(getContext(),cart.getCartItems(),this);
        binding.cartItems.setAdapter(cartItemsAdapter);

        binding.houseNo.setText(User.getHomeDetails(getContext()));
        binding.rawAddress.setText(User.getGoogleMapStreetAddress(getContext()));
        binding.shopAddress.setText(cart.getShopData().getAddressData().getMainAddress());

        binding.changeLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_cartFragment_to_getLocationFragment);
            }
        });

        binding.offerShimmerContainer.startShimmer();
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

        updateItemTotal();

        int totalDiscount = cart.getTotalDiscount();
        if(totalDiscount > 0){
            binding.extraDiscountContainer.setVisibility(View.VISIBLE);
            binding.totalDiscount.setText(PrettyStrings.getPriceInRupees(totalDiscount));
        }
        else {
            binding.extraDiscountContainer.setVisibility(View.GONE);
        }
        binding.savedView.setText(PrettyStrings.getPriceInRupees(totalDiscount + calculateSavedAmount()));
        orderItem.setTotalDiscount(totalDiscount);

        int totalTaxesAndPackingCharge = cart.getTotalPackingCharge() + cart.getTotalTaxDeduction();

        if(totalTaxesAndPackingCharge > 0){
            binding.taxesContainer.setVisibility(View.VISIBLE);
            binding.taxes.setText(PrettyStrings.getPriceInRupees(totalTaxesAndPackingCharge));
        }
        else {
            binding.taxesContainer.setVisibility(View.GONE);
        }

        orderItem.setTotalTaxesAndPackingCharge(totalTaxesAndPackingCharge);

        updateTotalItemsOnTheWayCost();

        binding.minPriceForFreeDelivery.setText(PrettyStrings.getPriceInRupees(orderItem.getShopData().getPricingDetails().getMinFreeDeliveryPrice()));


        orderItem.setDeliveryService(true);

        binding.deliveryServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isServiceTypeDelivery = true;
                orderItem.setDeliveryService(true);

                binding.deliveryAddressView.setVisibility(View.VISIBLE);
                binding.showRouteView.setVisibility(View.GONE);
                binding.itemsOnTheWayContainer.setVisibility(View.VISIBLE);

                binding.deliveryServiceButton.setBackgroundResource(R.drawable.selected_back);
                binding.deliveryServiceText.setTextColor(getResources().getColor(R.color.white));

                binding.selfPickUpServiceButton.setBackgroundResource(R.drawable.unselected_back);
                binding.selfPickUpServiceText.setTextColor(getResources().getColor(R.color.primaryColor));

                updateTheTotalPay();
                binding.deliveryChargeView.setVisibility(View.VISIBLE);

            }
        });


        if(cart.getShopData().isAdminShopService() || !cart.getShopData().isAllowSelfPickup()){
            binding.selfPickUpServiceButton.setVisibility(View.GONE);
        }

        binding.selfPickUpServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isServiceTypeDelivery = false;
                orderItem.setDeliveryService(false);

                binding.deliveryAddressView.setVisibility(View.GONE);
                binding.showRouteView.setVisibility(View.VISIBLE);
                binding.itemsOnTheWayContainer.setVisibility(View.GONE);

                binding.deliveryServiceButton.setBackgroundResource(R.drawable.unselected_back);
                binding.deliveryServiceText.setTextColor(getResources().getColor(R.color.primaryColor));

                binding.selfPickUpServiceButton.setBackgroundResource(R.drawable.selected_back);
                binding.selfPickUpServiceText.setTextColor(getResources().getColor(R.color.white));

                updateTheTotalPay();

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

                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_cartFragment_to_offersFragment,bundle);
            }
        });

        binding.removeOfferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cart.removeAppliedOffer(getContext());
                orderItem.setOfferDiscountedAmount(0);
                orderItem.setOfferCode(null);

                if(cart.getShopData().getAvailableOffers() != 0){
                    binding.availableOffers.setText(cart.getShopData().getAvailableOffers() + " OFFERS AVAILABLE");
                }

                updateExtraDiscountView();
                binding.savedView.setText(PrettyStrings.getPriceInRupees(orderItem.getDiscountWithOffer() + calculateSavedAmount()));

                updateTheTotalPay();

                binding.offerBodyView.setVisibility(View.GONE);
                binding.addCouponView.setVisibility(View.VISIBLE);

            }
        });

        binding.paymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFinalConformationDialog();
//                chooseAddressDialog();
            }
        });

        return root;
    }

    private void chooseAddressDialog(){
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
        bottomSheetDialog.setCancelable(false);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_choose_address,null,false);

        TextView mainAddressHouseNo, mainAddressrawAddress;
        MaterialButton mainAddressSelectButton, mainAddressEditButton;

        mainAddressHouseNo = view.findViewById(R.id.main_address_houseNo);
        mainAddressrawAddress = view.findViewById(R.id.main_address_raw_address);
        mainAddressSelectButton = view.findViewById(R.id.main_address_select_button);
        mainAddressEditButton = view.findViewById(R.id.main_address_edit_button);

        mainAddressHouseNo.setText(User.getHomeDetails(getContext()));
        mainAddressrawAddress.setText(User.getGoogleMapStreetAddress(getContext()));

        mainAddressSelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderItem.setDeliveryAddress(User.getDeliveryAddress(getContext()));

                String originAddress = cart.getShopData().getAddressData().getLatitude() + "%2C" + cart.getShopData().getAddressData().getLongitude();
                LatLng userLatLang = User.getLatLng(getContext());
                String destinationAddress = String.valueOf(userLatLang.latitude) + "%2C" + String.valueOf(userLatLang.longitude);

                calculateDistance(originAddress, destinationAddress);

                bottomSheetDialog.dismiss();
            }
        });

        mainAddressEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_cartFragment_to_getLocationFragment);
                bottomSheetDialog.dismiss();
            }
        });

        if(User.getSecondaryLatLang(getContext()) != null){
            view.findViewById(R.id.add_address_button).setVisibility(View.GONE);
            CardView secondAddressCardView = view.findViewById(R.id.second_address_view);
            secondAddressCardView.setVisibility(View.VISIBLE);

            TextView secondaryAddressHouseNo, secondaryAddressrawAddress;
            MaterialButton secondaryAddressSelectButton, secondaryAddressEditButton;

            secondaryAddressHouseNo = view.findViewById(R.id.second_address_houseNo);
            secondaryAddressrawAddress = view.findViewById(R.id.second_address_rawAddress);

            secondaryAddressEditButton = view.findViewById(R.id.second_address_edit_button);
            secondaryAddressSelectButton = view.findViewById(R.id.second_address_select_button);

            secondaryAddressHouseNo.setText(User.getSecondaryHomeDetails(getContext()));
            secondaryAddressrawAddress.setText(User.getSecondaryGoogleMapStreetAddress(getContext()));

            secondaryAddressEditButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("second_address",true);
                    Navigation.findNavController(binding.getRoot()).navigate(R.id.action_cartFragment_to_getLocationFragment,bundle);
                    bottomSheetDialog.dismiss();
                }
            });

            secondaryAddressSelectButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    orderItem.setDeliveryAddress(User.getSecondaryDeliveryAddress(getContext()));

                    String originAddress = cart.getShopData().getAddressData().getLatitude() + "%2C" + cart.getShopData().getAddressData().getLongitude();
                    LatLng userLatLang = User.getSecondaryLatLang(getContext());
                    String destinationAddress = String.valueOf(userLatLang.latitude) + "%2C" + String.valueOf(userLatLang.longitude);

                    calculateDistance(originAddress, destinationAddress);

                    bottomSheetDialog.dismiss();
                }
            });

        }
        else {
            LinearLayout addAddressButton = view.findViewById(R.id.add_address_button);
            addAddressButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("second_address",true);
                    Navigation.findNavController(binding.getRoot()).navigate(R.id.action_cartFragment_to_getLocationFragment,bundle);
                    bottomSheetDialog.dismiss();
                }
            });
        }


        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();
    }

    private void showFinalConformationDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("are you sure, you want to place the order ?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                choosePaymentType();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toasty.error(getContext(),"Order Not Placed",Toasty.LENGTH_LONG)
                .show();
            }
        });

        builder.show();

    }

    private void openYouSavedDialog(int amount,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_you_saved,binding.getRoot(),false);

        TextView youSavedText = view.findViewById(R.id.you_saved_text);
        youSavedText.setText("You Saved " + PrettyStrings.getPriceInRupees(amount));

        TextView offerMessage = view.findViewById(R.id.offer_message);
        offerMessage.setText(message);

        builder.setView(view);

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private int calculateSavedAmount(){
        float increasePercentage = cart.getShopData().getIncreaseDisplayPricePercentage() * 0.01f;
        int total_saved = 0;

        for(ShopItemData shopItemData : cart.getCartItems()){
            int total_items = shopItemData.getQuantity();
            total_saved += Math.round(Integer.parseInt(shopItemData.getPricings().get(0).getPrice()) * total_items * increasePercentage);
        }
        return  total_saved;
    }

    private void loadTheUI(){

        binding.itemsOnTheWayHeading.setText("Add Item from each shop in a single line, every shop will have a " + PrettyStrings.getPriceInRupees(orderItem.getItemOnTheWaySingleCost()) + " Increment in the total price");

        binding.itemsOnTheWay.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        orderItem.setItemsOnTheWay(cart.getItemsOnTheWay());

        updateTotalItemsOnTheWayCost();

        cartItemsOnTheWayAdapter = new CartItemsOnTheWayAdapter(getContext(), cart.getItemsOnTheWay(), new CartItemsOnTheWayAdapter.ActionsOnTheWayInterface() {
            @Override
            public void removeItemOnTheWay(String text) {
                List<String> newItemsOnTheWay = cart.getItemsOnTheWay();
                newItemsOnTheWay.remove(text);

                cart.addItemsOnTheWay(getContext(),newItemsOnTheWay);
                cartItemsOnTheWayAdapter.updateItemsOnTheWay(newItemsOnTheWay);
                orderItem.setItemsOnTheWay(newItemsOnTheWay);
                updateTheTotalPay();

                updateTotalItemsOnTheWayCost();

            }
        });

        binding.itemsOnTheWay.setAdapter(cartItemsOnTheWayAdapter);

        binding.submitItemsOnTheWay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: validate items
                String itemOnTheWay = binding.addItemsOnTheWay.getText().toString();
                if(itemOnTheWay.length() < 3){
                    Toasty.error(getContext(),"Enter Valid Items",Toasty.LENGTH_SHORT).show();
                    return;
                }

                // add items in the list
                List<String> newItemsOnTheWay = cart.getItemsOnTheWay();
                newItemsOnTheWay.add(itemOnTheWay);

                cart.addItemsOnTheWay(getContext(),newItemsOnTheWay);

                cartItemsOnTheWayAdapter.updateItemsOnTheWay(newItemsOnTheWay);
                orderItem.setItemsOnTheWay(newItemsOnTheWay);

                binding.addItemsOnTheWay.setText("");
                updateTotalItemsOnTheWayCost();
                // hide the keyboard

                updateTheTotalPay();

            }
        });

        binding.itemsOnTheWayChoices.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {

                String itemOnTheWay = "";
                if(checkedId == R.id.cold_drink_choice){
                    itemOnTheWay = "Cold Drink";
                    addItemOnTheWay(itemOnTheWay);
                }
                else if(checkedId == R.id.disposals_choice){
                    itemOnTheWay = "Disposals";
                    addItemOnTheWay(itemOnTheWay);
                }
                else if(checkedId == R.id.water_bottle_choice){
                    itemOnTheWay = "Water Bottle";
                    addItemOnTheWay(itemOnTheWay);
                }
                else if(checkedId == R.id.chips_choice){
                    itemOnTheWay = "Chips";
                    addItemOnTheWay(itemOnTheWay);
                }
                else {
                    addCustomItemOnWay();
                }

            }
        });

    }

    private void addCustomItemOnWay(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add Custom Item On Way");

        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_custom_item_on_way,null,false);
        TextInputEditText editText = view.findViewById(R.id.custom_item_on_way);
        builder.setView(view);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String newItemOnWay = editText.getText().toString();
                addItemOnTheWay(newItemOnWay);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.show();

    }

    private void addItemOnTheWay(String newItemOnWay){
        // add items in the list
        List<String> newItemsOnTheWay = cart.getItemsOnTheWay();
        newItemsOnTheWay.add(newItemOnWay);

        cart.addItemsOnTheWay(getContext(),newItemsOnTheWay);

        cartItemsOnTheWayAdapter.updateItemsOnTheWay(newItemsOnTheWay);
        orderItem.setItemsOnTheWay(newItemsOnTheWay);

        binding.addItemsOnTheWay.setText("");
        updateTotalItemsOnTheWayCost();
        // hide the keyboard

        updateTheTotalPay();
    }

    private void updateTotalItemsOnTheWayCost(){
        if(orderItem.getTotalFromItemsOnTheWay() > 0){
            binding.totalItemsOnTheWayCostContainer.setVisibility(View.VISIBLE);
            binding.totalItemsToPickCost.setText(PrettyStrings.getPriceInRupees(orderItem.getTotalFromItemsOnTheWay()));
        }
        else {
            binding.totalItemsOnTheWayCostContainer.setVisibility(View.GONE);
        }
    }

    private void updateTheTotalPay(){
        binding.processingFee.setText(PrettyStrings.getPriceInRupees(orderItem.getTotalProcessingFee()));

        if(orderItem.getDeliveryCharge() <= 0){
            binding.deliveryCharge.setText("FREE DELIVERY");
        }
        else {
            binding.deliveryCharge.setText(PrettyStrings.getPriceInRupees(orderItem.getDeliveryCharge()));
        }

        if(orderItem.isEdgeLocation()){
            binding.freeDeliveryChargeText.setText("Free delivery offer not available at your location");
        }
        else if(orderItem.getDeliveryCharge() != 0){
            binding.freeDeliveryChargeText.setVisibility(View.VISIBLE);

            int min_items_for_free_delivery = Integer.parseInt(cart.getShopData().getPricingDetails().getMinFreeDeliveryPrice());
            int diff = min_items_for_free_delivery - orderItem.getItemTotal();

            if(diff > 0){
                binding.freeDeliveryChargeText.setText("Add " + PrettyStrings.getPriceInRupees(diff) + " more to get FREE delivery");
            }

        }
        else {
            binding.freeDeliveryChargeText.setVisibility(View.GONE);
        }

        binding.totalPriceToPay.setText("₹" + orderItem.calculateTotalPrice() + ".00");
        binding.paymentButton.setText("Proceed to pay ₹" + orderItem.calculateTotalPrice() + ".00");
    }

    private void choosePaymentType(){
        BottomSheetDialog dialog = new BottomSheetDialog(getContext());
        dialog.setContentView(R.layout.dialog_paymenttype);

        if(!cart.getShopData().isAllowSelfPickupCOD() && !orderItem.getDeliveryService()){
            dialog.findViewById(R.id.cashOnDeliveryButton).setVisibility(View.GONE);
        }
        else if(!cart.getShopData().isAllowCOD() && orderItem.getDeliveryService()){
            dialog.findViewById(R.id.cashOnDeliveryButton).setVisibility(View.GONE);
        }
        else if(orderItem.isEdgeLocation()){
            dialog.findViewById(R.id.cashOnDeliveryButton).setVisibility(View.GONE);
        }
        else {
            dialog.findViewById(R.id.cashOnDeliveryButton).setVisibility(View.VISIBLE);
        }


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

//                Toasty.warning(getContext(),"Online Payment Method Not Available",Toasty.LENGTH_LONG)
//                        .show();

                //  Price will increase dialog
//
                Toasty.warning(getContext(),"Extra Charges Upto 2% Will Be Applied If Paid Online",Toasty.LENGTH_LONG)
                        .show();

                if(orderItem.getItemsOnTheWay().size() > 0){
                    Toasty.error(getContext(),"Online Payment Method Is Not Available In Items On The Way",Toasty.LENGTH_SHORT)
                            .show();
                    return;
                }
                getOrderPaymentId();
            }
        });

        dialog.show();
    }



    private void getOrderPaymentId(){
        Retrofit retrofit = RetrofitClient.getInstance();
        NetworkApi networkApi = retrofit.create(NetworkApi.class);

        OnlinePaymentOrderIdPostData postData = new OnlinePaymentOrderIdPostData(orderItem.getTotalPay() * 100,
                User.getPhoneNumber(getContext()));
        Call<PaymentMetadata> call = networkApi.getOrderPaymentId(postData,cart.getShopData().get_id());
        call.enqueue(new Callback<PaymentMetadata>() {
            @Override
            public void onResponse(Call<PaymentMetadata> call, Response<PaymentMetadata> response) {
                if(!response.isSuccessful()){
                    NetworkResponse errorResponse = ErrorUtils.parseErrorResponse(response);
                    Toasty.error(getContext(),errorResponse.getDesc(),Toasty.LENGTH_SHORT)
                            .show();
                    return;
                }

//                payOnline(response.body().getPaymentId());
                orderPaymentId = response.body().getPaymentId();
//                Log.i(TAG, "onResponse: " + orderPaymentId);
                checkout(true);

            }

            @Override
            public void onFailure(Call<PaymentMetadata> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });

    }

    private void getCartMetaData(){
        Retrofit retrofit = RetrofitClient.getInstance();
        NetworkApi networkApi = retrofit.create(NetworkApi.class);

        Call<CartMetaData> call = networkApi.getCartMetaData();
        call.enqueue(new Callback<CartMetaData>() {
            @Override
            public void onResponse(Call<CartMetaData> call, Response<CartMetaData> response) {
                cartMetaData = response.body();
                orderItem.setItemOnTheWaySingleCost(cartMetaData.getItemsOnTheWayCost());

                if(cartMetaData.getSlurgeCharges() > 0){
                    binding.slurgeChargesContainer.setVisibility(View.VISIBLE);
                    binding.slurgeReason.setText(cartMetaData.getSlurgeReason());
                    binding.deliveryChargeText.setText("Delivery Charge & Surge Charges");
                }else {
                    binding.slurgeChargesContainer.setVisibility(View.GONE);
                }

                if(cart.getShopData().getProcessingFees() == null){
                    orderItem.setProcessingFee(response.body().getProcessingFee());
                }
                else {
                    orderItem.setProcessingFee(cart.getShopData().getProcessingFees());
                }
                loadTheUI();
            }

            @Override
            public void onFailure(Call<CartMetaData> call, Throwable t) {
                Toasty.error(getContext(),"Some Error Occurred",Toasty.LENGTH_SHORT)
                        .show();
                // TODO: do not allow to add items on the way
            }
        });

    }

    private void checkout(Boolean isPaid){
        Retrofit retrofit = RetrofitClient.getInstance();
        NetworkApi networkApi = retrofit.create(NetworkApi.class);

        String specialInstructions = binding.specialInstruction.getText().toString();

        Cart cart = Cart.getInstance(getContext());
        orderItem.setOrderItems(cart.getCartItems());
        orderItem.setShopID(cart.getShopID());
        orderItem.setShopCategory(cart.getShopData().getShopType());
        orderItem.setUserId(User.getPhoneNumber(getContext()));
        orderItem.setPaid(isPaid);
        orderItem.setBillingDetails(cart.getShopData().getTaxPercentage());
        orderItem.setOrderType(0);
        orderItem.setOrderStatus(0);
        orderItem.setSpecialInstructions(specialInstructions);
        orderItem.setAdminShopService(cart.getShopData().isAdminShopService());

        if(isPaid){
            Intent onlinePaymentActivityIntent = new Intent(getContext(), OnlinePaymentActivity.class);
            onlinePaymentActivityIntent.putExtra("orderItem",gson.toJson(orderItem));
            onlinePaymentActivityIntent.putExtra("orderPaymentId", orderPaymentId);

            startActivity(onlinePaymentActivityIntent);
            return;
        }

        Call<NetworkResponse> call = networkApi.checkout(orderItem);
        call.enqueue(new Callback<NetworkResponse>() {
            @Override
            public void onResponse(Call<NetworkResponse> call, Response<NetworkResponse> response) {

                if(response.isSuccessful()){
                    Toasty.success(getContext(),"Order Successfull",Toasty.LENGTH_SHORT)
                            .show();
//                      change this
                    updateBadge(0);

                    Navigation.findNavController(binding.getRoot()).navigate(R.id.action_cartFragment_to_successFragment);
                }
                else {
                    NetworkResponse errorResponse = ErrorUtils.parseErrorResponse(response);
                    Toasty.error(getContext(),errorResponse.getDesc(),Toasty.LENGTH_SHORT)
                            .show();
                }

            }

            @Override
            public void onFailure(Call<NetworkResponse> call, Throwable t) {
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

        Call<GetDistanceResponse> call = networkApi.getDistance(destinationAddress,originAddress,"AIzaSyCjGoldXj1rERZHuTyT9iebSRFc_O3YHX4",cart.getShopData().getDeliveryPricings());
        call.enqueue(new Callback<GetDistanceResponse>() {
            @Override
            public void onResponse(Call<GetDistanceResponse> call, Response<GetDistanceResponse> response) {

                if(!response.isSuccessful()){
                    NetworkResponse errorResponse = ErrorUtils.parseErrorResponse(response);
                    Toasty.error(getContext(),errorResponse.getDesc(),Toasty.LENGTH_SHORT)
                            .show();
                    return;
                }

                distanceResponse = response.body();

                int deliveryCharge = Integer.parseInt(distanceResponse.getDistance());

                if(deliveryCharge == -1){
                    Toasty.error(getContext(),"Service Not Available At This Long Distance",Toasty.LENGTH_SHORT)
                            .show();
                    binding.paymentButton.setEnabled(false);
                }

                if(cartMetaData.getSlurgeCharges() == 0){
                    orderItem.setDeliveryCharge(deliveryCharge);
                }
                else {
                    orderItem.setDeliveryCharge(deliveryCharge + cartMetaData.getSlurgeCharges());
                }

                orderItem.setEdgeLocation(distanceResponse.isEdgeLocation());

                orderItem.setActualDistance(distanceResponse.getActualDistance());

                binding.deliveryCharge.setText(PrettyStrings.getPriceInRupees(orderItem.getDeliveryCharge()));

                binding.totalPriceToPay.setText("₹" + orderItem.calculateTotalPrice() + ".00");
                binding.paymentButton.setText("Proceed to pay ₹" + orderItem.calculateTotalPrice() + ".00");

                binding.loading.setVisibility(View.GONE);
                binding.cartBody.setVisibility(View.VISIBLE);

                if(cart.getAppliedOffer() == null){
                    openYouSavedDialog(calculateSavedAmount(),"0% Commission Coupon \n (Only Pay Menu Price)");
                }
                else {
                    openYouSavedDialog(calculateSavedAmount() + orderItem.getOfferDiscountedAmount(),"2 Offers Applied 🎉🎉 \n 1) 0% Commission (Menu Item Price) \n 2) " + orderItem.getOfferCode());
                }

                if(cart.getShopData().isAllowProcessingFees()){
                    binding.processingFeeContainer.setVisibility(View.VISIBLE);
                    binding.processingFee.setText(PrettyStrings.getPriceInRupees(orderItem.getTotalProcessingFee()));
                }
                else {
                    orderItem.setProcessingFee(new ProcessingFee(0,0,1));
                    binding.processingFeeContainer.setVisibility(View.GONE);
                }

                binding.houseNo.setText(orderItem.getDeliveryAddress().getHouseNo());
                binding.rawAddress.setText(orderItem.getDeliveryAddress().getRawAddress());
                binding.shopAddress.setText(cart.getShopData().getAddressData().getMainAddress());

                updateTheTotalPay();

            }

            @Override
            public void onFailure(Call<GetDistanceResponse> call, Throwable t) {
                Toasty.error(getContext(),t.getMessage(),Toasty.LENGTH_SHORT)
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
        cartItemsAdapter.updateCartItems();

        if(value != 0) {
            badge.setVisible(true);
            badge.setNumber(value);

            updateItemTotal();


            int totalDiscount = cart.getTotalDiscount();
            orderItem.setTotalDiscount(totalDiscount);


            int totalTaxesAndPackingCharge = cart.getTotalPackingCharge() + cart.getTotalTaxDeduction();
            orderItem.setTotalTaxesAndPackingCharge(totalTaxesAndPackingCharge);

            if(totalTaxesAndPackingCharge > 0){
                binding.taxesContainer.setVisibility(View.VISIBLE);
                binding.taxes.setText(PrettyStrings.getPriceInRupees(orderItem.getTotalTaxesAndPackingCharge()));
            }
            else {
                binding.taxesContainer.setVisibility(View.GONE);
            }


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

            updateExtraDiscountView();
            binding.savedView.setText(PrettyStrings.getPriceInRupees(orderItem.getDiscountWithOffer() + calculateSavedAmount()));

        }
        else {
            cart.clearCart(getContext());
            binding.cartBody.setVisibility(View.GONE);
            binding.emptyCartView.setVisibility(View.VISIBLE);
            badge.setVisible(false);
        }

        updateTheTotalPay();

    }

    @Override
    public void insertNewItem(ShopItemData shopItemData) {
        if(shopItemData.getAllPricings().size() == 1){
            cart.updateCartItem(getContext(),shopItemData,shopItemData.getPricings().get(0),1);
            updateBadge(cart.getCartSize());
            cartItemsAdapter.notifyDataSetChanged();
            return;
        }

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_choosedishtype);

        TextView titleView =  bottomSheetDialog.findViewById(R.id.itemName);
        titleView.setText("Choose The Type For " + shopItemData.getName());


        RecyclerView itemPricingType = bottomSheetDialog.findViewById(R.id.pricingType);
        itemPricingType.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        ShopItemsTypeDialogAdapter adapter = new ShopItemsTypeDialogAdapter(getContext(), shopItemData.getAllPricings(), new ShopItemsTypeDialogAdapter.onPriceSelectedInterface() {
            @Override
            public void selectedPrice(ShopPricingData shopPricingData) {
                cart.updateCartItem(getContext(),shopItemData,shopPricingData,1);
                updateBadge(cart.getCartSize());
                cartItemsAdapter.notifyDataSetChanged();
                bottomSheetDialog.dismiss();
            }
        });

        itemPricingType.setAdapter(adapter);
        bottomSheetDialog.show();

    }

    private void updateItemTotal(){
        int itemTotal = cart.getTotalOfItems();
        int increasedTotal = cart.getTotalItemsIncreasedPrice();

        orderItem.setItemTotal(itemTotal);
        binding.itemsTotal.setText(PrettyStrings.getPriceInRupees(orderItem.getItemTotal()));
        binding.increasedItemsTotal.setText(PrettyStrings.getPriceInRupees(increasedTotal));
        binding.increasedItemsTotal.setPaintFlags(binding.increasedItemsTotal.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

    }

    private void updateExtraDiscountView(){
        if(orderItem.getDiscountWithOffer() > 0){
            binding.extraDiscountContainer.setVisibility(View.VISIBLE);
            binding.totalDiscount.setText(PrettyStrings.getPriceInRupees(orderItem.getDiscountWithOffer()));
        }
        else {
            binding.extraDiscountContainer.setVisibility(View.GONE);
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
            if(cart.getShopData().getAvailableOffers() != 0){
                binding.availableOffers.setText(cart.getShopData().getAvailableOffers() + " OFFERS AVAILABLE");
            }
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

        updateExtraDiscountView();
        binding.savedView.setText(PrettyStrings.getPriceInRupees(orderItem.getDiscountWithOffer() + calculateSavedAmount()));

        if(isServiceTypeDelivery) {
            // Distance API
//            String originAddress = cart.getShopData().getAddressData().getLatitude() + "%2C" + cart.getShopData().getAddressData().getLongitude();
//            LatLng userLatLang = User.getLatLng(getContext());
//            String destinationAddress = String.valueOf(userLatLang.latitude) + "%2C" + String.valueOf(userLatLang.longitude);
//
//            calculateDistance(originAddress, destinationAddress);

            chooseAddressDialog();

        }
        else {
            updateTheTotalPay();
//            binding.totalPriceToPay.setText("₹" + orderItem.calculateTotalPrice() + ".00");
//            binding.paymentButton.setText("Proceed to pay ₹" + orderItem.calculateTotalPrice() + ".00");
        }

    }


}