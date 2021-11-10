package com.avit.apnamzp.ui.orders;

import android.os.Bundle;

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

import com.avit.apnamzp.R;
import com.avit.apnamzp.databinding.FragmentOrdersBinding;
import com.avit.apnamzp.localdb.User;
import com.avit.apnamzp.models.order.OrderItem;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class OrdersFragment extends Fragment {

    private FragmentOrdersBinding binding;
    private OrdersViewModel viewModel;
    private String TAG = "OrdersFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentOrdersBinding.inflate(inflater,container,false);
        viewModel = new ViewModelProvider(this).get(OrdersViewModel.class);

        viewModel.getDataFromServer(getContext(), User.getPhoneNumber(getContext()));

        Gson gson = new Gson();

        binding.ordersList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false));
        OrdersAdapter adapter = new OrdersAdapter(getContext(), new ArrayList<>(), new OrdersAdapter.openOrderDetailsInterface() {
            @Override
            public void openOrderDetails(OrderItem orderItem) {

                Bundle bundle = new Bundle();
                String orderItemString = gson.toJson(orderItem,OrderItem.class);
                bundle.putString("orderItem",orderItemString);

                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_ordersFragment_to_orderDetailsFragment,bundle);
            }
        });
        binding.ordersList.setAdapter(adapter);

        viewModel.getMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<OrderItem>>() {
            @Override
            public void onChanged(List<OrderItem> orderItems) {
                adapter.replaceItems(orderItems);
            }
        });

        return binding.getRoot();
    }
}