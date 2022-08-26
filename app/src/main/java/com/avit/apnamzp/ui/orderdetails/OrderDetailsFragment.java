package com.avit.apnamzp.ui.orderdetails;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avit.apnamzp.R;
import com.avit.apnamzp.databinding.FragmentOrderDetailsBinding;
import com.avit.apnamzp.models.order.OrderItem;
import com.avit.apnamzp.utils.PrettyStrings;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class OrderDetailsFragment extends Fragment {

    private FragmentOrderDetailsBinding binding;
    private OrderItem orderItem;
    private String TAG = "OrderDetailsFragment";
    private OrderDetailsViewModel orderDetailsViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrderDetailsBinding.inflate(inflater,container,false);
        orderDetailsViewModel = ViewModelProviders.of(this).get(OrderDetailsViewModel.class);

        Gson gson = new Gson();
        Bundle bundle = getArguments();
        String orderItemsString = bundle.getString("orderItem");
        String action = bundle.getString("action");

        if(orderItemsString == null){
            orderDetailsViewModel.getOrderDataFromServer(getContext(),bundle.getString("orderId"));
        }
        else {
            OrderItem temp = gson.fromJson(orderItemsString,OrderItem.class);
            orderDetailsViewModel.setOrderItem(temp);
        }

        orderDetailsViewModel.getOrderItemMutableLiveData().observe(getViewLifecycleOwner(), new Observer<OrderItem>() {
            @Override
            public void onChanged(OrderItem orderItem1) {
                orderItem = orderItem1;


                if(!orderItem.isUserFeedBack() && orderItem.getOrderStatus() == 6){

                    Bundle bundle1 = new Bundle();
                    bundle1.putString("shopId",orderItem.getShopID());
                    bundle1.putString("orderId",orderItem.get_id());

                    Navigation.findNavController(binding.getRoot()).navigate(R.id.action_orderDetailsFragment_to_feedbackFragment,bundle1);

                }

                binding.shopName.setText(orderItem.getShopData().getShopName());
                binding.shopAddress.setText(orderItem.getShopData().getAddressData().getMainAddress());


                binding.orderItems.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                OrderItemsAdapter adapter = new OrderItemsAdapter(getContext(), orderItem.getOrderItems());
                binding.orderItems.setAdapter(adapter);

//                if(orderItemsString == null && orderItem.getOrderStatus() == 6){
//                    Bundle bundle1 = new Bundle();
//                    bundle1.putString("shopId", orderItem.getShopID());
//                    bundle1.putString("orderId", orderItem.get_id());
//                    Navigation.findNavController(binding.getRoot()).navigate(R.id.action_orderDetailsFragment_to_feedbackFragment,bundle1);
//                }

                setUpUI();

                // Vertical StepView
                Log.i(TAG, "onChanged: " + orderItem.getCancelReason());
                binding.orderStatus.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                OrderStatusAdapter orderStatusAdapter = new OrderStatusAdapter(getContext(), orderItem.getOrderStatus(),orderItem.getCancelReason(),orderItem.getBillingDetails().getDeliveryService(),orderItem.getAssignedDeliveryBoy(),orderItem.getPaid());
                binding.orderStatus.setAdapter(orderStatusAdapter);

                if(orderItem.getBillingDetails().getDeliveryService() && orderItem.getAssignedDeliveryBoy() != null && orderItem.getAssignedDeliveryBoy().length() > 0){
                    binding.deliveryBoyDetailsView.setVisibility(View.VISIBLE);
                    binding.deliverySathiPhoneNo.setText("+91 " + orderItem.getAssignedDeliveryBoy());

                    binding.deliveryBoyDetailsView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String phoneNo = orderItem.getAssignedDeliveryBoy();
                            Intent callingIntent = new Intent();
                            callingIntent.setAction(Intent.ACTION_DIAL);
                            callingIntent.setData(Uri.parse("tel: " + phoneNo));
                            startActivity(callingIntent);
                        }
                    });

                }

            }
        });

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(binding.getRoot()).popBackStack();
            }
        });

        return binding.getRoot();
    }

    private void setUpUI(){

        // items on the way
        if(orderItem.getItemsOnTheWay() != null && orderItem.getItemsOnTheWay().size() > 0){
            binding.itemsOnTheWayContainer.setVisibility(View.VISIBLE);

            if(orderItem.isItemsOnTheWayCancelled()){
                binding.itemsOnTheWayCancelledMessage.setVisibility(View.VISIBLE);
            }
            else {
                binding.itemsOnTheWayRecyclerview.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
                ItemsOnTheWayOrderDetailsAdapter itemsOnTheWayOrderDetailsAdapter = new ItemsOnTheWayOrderDetailsAdapter(orderItem.getItemsOnTheWay(),getContext());
                binding.itemsOnTheWayRecyclerview.setAdapter(itemsOnTheWayOrderDetailsAdapter);
            }
        }

        // Billing Details
        binding.itemsTotal.setText("₹" + orderItem.getBillingDetails().getItemTotal() + ".00");
        binding.taxAndPackagingCharge.setText("₹" + orderItem.getBillingDetails().getTotalTaxesAndPackingCharge() + ".00");
        binding.deliveryCharge.setText("₹" + orderItem.getBillingDetails().getDeliveryCharge() + ".00");
        binding.onTheWayItemsTotalCost.setText(PrettyStrings.getPriceInRupees(orderItem.getBillingDetails().getItemsOnTheWayTotalCost()));
        binding.onTheWayItemsTotalActualCost.setText(PrettyStrings.getPriceInRupees(orderItem.getBillingDetails().getItemsOnTheWayActualCost()));
        binding.totalDiscount.setText("₹" + (orderItem.getBillingDetails().getTotalDiscount() + orderItem.getBillingDetails().getOfferDiscountedAmount()) + ".00");
        binding.totalPriceToPay.setText("₹" + orderItem.getBillingDetails().getTotalPay() + ".00");

        if(orderItem.getBillingDetails().getDeliveryCharge() == 0){
            binding.deliveryChargeContainer.setVisibility(View.GONE);
        }

        if(orderItem.getBillingDetails().getItemsOnTheWayTotalCost() == 0){
            binding.itemsOnTheWayDeliveryChargeContainer.setVisibility(View.GONE);
        }

        if(orderItem.getBillingDetails().getItemsOnTheWayActualCost() == 0){
            binding.itemsOnTheWayActualCostContainer.setVisibility(View.GONE);
        }

        if((orderItem.getBillingDetails().getTotalDiscount() + orderItem.getBillingDetails().getOfferDiscountedAmount()) == 0){
            binding.discountContainer.setVisibility(View.GONE);
        }

        // Order Details
        binding.orderNo.setText(orderItem.get_id());
        binding.deliveryAddressView.setText(orderItem.getDeliveryAddress().getRawAddress());

        if(orderItem.getPaid()){
            binding.paymentMethod.setText("Online");
        }
        else {
            binding.paymentMethod.setText("Cash On Delivery");
        }

        if(orderItem.getBillingDetails().getDeliveryService()){
            binding.orderType.setText("Delivery");
        }
        else {
            binding.orderType.setText("Pick & Drop");
        }

        binding.orderAt.setText(orderItem.getCreatedAt().toLocaleString());

    }

}