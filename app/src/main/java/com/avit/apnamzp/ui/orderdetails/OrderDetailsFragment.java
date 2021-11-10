package com.avit.apnamzp.ui.orderdetails;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avit.apnamzp.R;
import com.avit.apnamzp.databinding.FragmentOrderDetailsBinding;
import com.avit.apnamzp.models.order.OrderItem;
import com.google.gson.Gson;

public class OrderDetailsFragment extends Fragment {

    private FragmentOrderDetailsBinding binding;
    private OrderItem orderItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrderDetailsBinding.inflate(inflater,container,false);

        Gson gson = new Gson();
        Bundle bundle = getArguments();
        String orderItemsString = bundle.getString("orderItem");
        orderItem = gson.fromJson(orderItemsString,OrderItem.class);

        binding.orderItems.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        OrderItemsAdapter adapter = new OrderItemsAdapter(getContext(),orderItem.getOrderItems());
        binding.orderItems.setAdapter(adapter);


        return binding.getRoot();
    }
}