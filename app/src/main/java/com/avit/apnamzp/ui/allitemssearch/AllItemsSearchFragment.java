package com.avit.apnamzp.ui.allitemssearch;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avit.apnamzp.R;
import com.avit.apnamzp.databinding.FragmentAllItemsSearchBinding;
import com.avit.apnamzp.models.shop.ShopData;
import com.avit.apnamzp.models.shop.ShopItemData;
import com.avit.apnamzp.ui.searchcategory.SearchCategoryAdapter;
import com.avit.apnamzp.ui.shopdetails.ShopDetailsAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class AllItemsSearchFragment extends Fragment {

    private FragmentAllItemsSearchBinding binding;
    private String TAG = "AllItemsSearchFragment";
    private AllItemsSearchViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAllItemsSearchBinding.inflate(inflater,container,false);
        viewModel = new ViewModelProvider(this).get(AllItemsSearchViewModel.class);

//        binding.searchBar.setBackgroundColor(getResources().getColor(R.color.secondaryTextColor));

        Gson gson = new Gson();
        setUpTrendingSearches();

        if(getArguments() != null && getArguments().containsKey("searchKey")){
            String searchKey = getArguments().getString("searchKey");
            binding.progressBar.setVisibility(View.VISIBLE);
            viewModel.getSearchDataFromServer(getContext(),searchKey.toLowerCase());
        }
        
        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(binding.getRoot()).popBackStack();
            }
        });

        binding.shopsList.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        SearchCategoryAdapter adapter = new SearchCategoryAdapter(getContext(), new ArrayList<>(), new SearchCategoryAdapter.openShopDetails() {
            @Override
            public void openShopDetails(ShopData shopData) {
                Bundle shopDetailsBundle = new Bundle();
                String jsonString  = gson.toJson(shopData,ShopData.class);
                shopDetailsBundle.putString("shopData",jsonString);

                Navigation.findNavController(binding.getRoot()).navigate(R.id.shopDetailsFragment,shopDetailsBundle);
            }
        });
        binding.shopsList.setAdapter(adapter);

        binding.openApnaSathi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_allItemsSearchFragment_to_pickUpAndDropFragment);
            }
        });

        viewModel.getMutableLiveShopData().observe(getViewLifecycleOwner(), new Observer<List<ShopData>>() {
            @Override
            public void onChanged(List<ShopData> shopData) {
                adapter.replaceList(shopData);
                binding.progressBar.setVisibility(View.GONE);
                if(shopData.size() == 0){
                    binding.emptyLayout.setVisibility(View.VISIBLE);
                }else {
                    binding.emptyLayout.setVisibility(View.GONE);
                }
            }
        });

        binding.searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                binding.progressBar.setVisibility(View.VISIBLE);
                viewModel.getSearchDataFromServer(getContext(),query.toLowerCase());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });



        return binding.getRoot();
    }

    private void setUpTrendingSearches(){
        List<Pair<String,Integer>>  trendingSearches = new ArrayList<>();
        trendingSearches.add(new Pair<>("Burger",R.drawable.ic_burger));
        trendingSearches.add(new Pair<>("Pizza",R.drawable.ic_pizza));
        trendingSearches.add(new Pair<>("Dosa",R.drawable.ic_dosa));
        trendingSearches.add(new Pair<>("Chaat",R.drawable.ic_chaat));
        trendingSearches.add(new Pair<>("Chowmein",R.drawable.ic_chowmein));
        trendingSearches.add(new Pair<>("Thali",R.drawable.ic_thali));
        trendingSearches.add(new Pair<>("Biryani",R.drawable.ic_biryani));
        trendingSearches.add(new Pair<>("Paneer",R.drawable.ic_paneer));
        trendingSearches.add(new Pair<>("Cake",R.drawable.ic_cake));
        trendingSearches.add(new Pair<>("Sweets",R.drawable.ic_sweets_trendign));

        for(Pair<String,Integer> search : trendingSearches){
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_trending_search,null,false);
            TextView textView = view.findViewById(R.id.filter);
            ImageView imageView = view.findViewById(R.id.icon);

            textView.setText(search.first);
            imageView.setImageResource(search.second);

            binding.trendingSearchesContainer.addView(view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    binding.progressBar.setVisibility(View.VISIBLE);
                    viewModel.getSearchDataFromServer(getContext(),search.first.toLowerCase());
                }
            });
        }

    }

}