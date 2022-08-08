package com.avit.apnamzp.ui.pickupanddrop;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.avit.apnamzp.R;
import com.avit.apnamzp.databinding.FragmentPickUpAndDropBinding;
import com.avit.apnamzp.models.cart.CartItemData;
import com.avit.apnamzp.models.shop.ShopItemData;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class PickUpAndDropFragment extends Fragment {

    private String TAG = "PickUpAndDropFragment";
    private FragmentPickUpAndDropBinding binding;
    private CartItemData currItem;
    private int selectedOption;
    private PickAndDropAdapter pickAndDropAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPickUpAndDropBinding.inflate(inflater,container,false);
        View root = binding.getRoot();

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(root).popBackStack();
            }
        });

        String categories[] = {"Any Store In Town","Send | Collect Packages"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,categories);

        binding.categoryTextView.setAdapter(adapter);

        binding.categoryTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i(TAG, "onItemSelected: " + i);
                selectedOption = i;
                if(i == 0){
                    binding.shopWrapperView.setVisibility(View.VISIBLE);
                    binding.dropOffLocationWrapperView.setVisibility(View.GONE);
                }
                else {
                    binding.shopWrapperView.setVisibility(View.GONE);
                    binding.dropOffLocationWrapperView.setVisibility(View.VISIBLE);
                }
            }
        });

        // Recycler View
        binding.pickanddropItemsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false));

        pickAndDropAdapter = new PickAndDropAdapter(getContext());
        binding.pickanddropItemsRecyclerView.setAdapter(pickAndDropAdapter);

        // Item List
        currItem = new CartItemData();
        binding.menuItemInput.setOnEditorActionListener(new TextView.OnEditorActionListener(){

            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_DONE){
                    currItem.setName(textView.getText().toString());
                    pickAndDropAdapter.addItem(currItem);

                    // Clear Everything
                    currItem = new CartItemData();
                    textView.setText("");
                    binding.quantityText.setText(String.valueOf(1));


                }
                return false;
            }
        });

        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int newQuantity = currItem.getQuantity() + 1;
                binding.quantityText.setText(String.valueOf(newQuantity));
                currItem.setQuantity(newQuantity);
            }
        });

        binding.subtractButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int newQuantity = currItem.getQuantity() - 1;
                if(newQuantity < 1) return;

                binding.quantityText.setText(String.valueOf(newQuantity));
                currItem.setQuantity(newQuantity);
            }
        });

        binding.placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String shopName = binding.storeNameView.getText().toString();
                String pickUpLocation = binding.pickUpLocation.getText().toString();
                String specialInstructions = binding.specialInstruction.getText().toString();
                String dropOffLocation = binding.dropOffLocation.getText().toString();

                openWhatsapp(shopName,pickUpLocation,specialInstructions,dropOffLocation);
            }
        });

        return root;
    }

    private void openWhatsapp(String shopName,String pickUpLocation,String specialInstruction,String dropOffLocation){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String mobileNumber = "9565820009";
        String message;

        StringBuilder itemsDetails = new StringBuilder();
        int i=1;
        for(CartItemData cartItemData : pickAndDropAdapter.getAllItems()){
            itemsDetails.append(i + ") Item:" + cartItemData.getName() + ", Quantity: " + cartItemData.getQuantity() + "\n");
            i++;
        }

        if(selectedOption == 0){
            message = "Shop Name: " + shopName + "\n" + "Pick Up Location: " + pickUpLocation + "\n"
                    + "Special Instruction: " + specialInstruction + "\n" + "---------------" + "\n" + "Items " + itemsDetails.toString();
        }
        else {
            message = "Shop Name: " + shopName + "\n" + "Pick Up Location: " + pickUpLocation + "\n" + "Drop Location: " + dropOffLocation + "\n"
                    + "Special Instruction: " + specialInstruction + "\n" + "---------------" + "\n" + "Items " + itemsDetails.toString();
        }
        intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"+91"+mobileNumber + "&text="+ message));
        startActivity(intent);
    }

}