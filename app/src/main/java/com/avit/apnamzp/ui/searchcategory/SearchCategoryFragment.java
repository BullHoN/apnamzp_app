package com.avit.apnamzp.ui.searchcategory;

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

import com.avit.apnamzp.R;
import com.avit.apnamzp.databinding.FragmentSearchBinding;
import com.avit.apnamzp.models.shop.ShopData;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class SearchCategoryFragment extends Fragment {

    private String TAG = "SearchCategoryFragment";
    private FragmentSearchBinding binding;
    private SearchCategoryViewModel viewModel;
    private Gson gson;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSearchBinding.inflate(inflater,container,false);
        View root = binding.getRoot();

        viewModel = new ViewModelProvider(this).get(SearchCategoryViewModel.class);
        gson = new Gson();

        Bundle bundle = getArguments();
        String categoryName = bundle.getString("categoryName",null);
        String shopType = bundle.getString("shopType",null);

        viewModel.getDataFromServer(shopType);

        binding.categoryName.setText(categoryName + " Near You");

        // Views Config
        binding.searchBar.setSubmitButtonEnabled(true);
        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(root).popBackStack();
            }
        });


        binding.shopsList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false));
        SearchCategoryAdapter adapter = new SearchCategoryAdapter(getContext(), new ArrayList<>(), new SearchCategoryAdapter.openShopDetails() {
            @Override
            public void openShopDetails(ShopData shopData) {
                Bundle shopDetailsBundle = new Bundle();
                String jsonString  = gson.toJson(shopData,ShopData.class);
                shopDetailsBundle.putString("shopData",jsonString);

                Navigation.findNavController(root).navigate(R.id.action_searchFragment_to_shopDetailsFragment,shopDetailsBundle);

            }
        });
        binding.shopsList.setAdapter(adapter);

        // TODO: Load Initial Data

        // Observe Change in Shops Data
        viewModel.getShopsData().observe(getViewLifecycleOwner(), new Observer<List<ShopData>>() {
            @Override
            public void onChanged(List<ShopData> shopData) {
                // Initial Load, Search
                if(shopData.size() > 0){
                    binding.progessBar.setVisibility(View.GONE);
                }
                adapter.replaceList(shopData);
            }
        });


        // SearchBar
        binding.searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.i(TAG, "onQueryTextSubmit: " + query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return root;
    }
}