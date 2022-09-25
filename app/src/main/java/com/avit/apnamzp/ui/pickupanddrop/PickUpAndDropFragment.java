package com.avit.apnamzp.ui.pickupanddrop;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avit.apnamzp.R;
import com.avit.apnamzp.databinding.FragmentPickUpAndDropBinding;
import com.avit.apnamzp.models.BannerData;
import com.avit.apnamzp.models.cart.CartItemData;
import com.avit.apnamzp.models.pickanddrop.PickAndDropDetails;
import com.avit.apnamzp.models.pickanddrop.PickAndDropViewModel;
import com.avit.apnamzp.models.shop.ShopItemData;
import com.avit.apnamzp.utils.InfoConstats;
import com.avit.apnamzp.utils.Validation;
import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.jama.carouselview.CarouselView;
import com.jama.carouselview.CarouselViewListener;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class PickUpAndDropFragment extends Fragment {

    private String TAG = "PickUpAndDropFragment";
    private FragmentPickUpAndDropBinding binding;
    private CartItemData currItem;
    private int selectedOption;
    private PickAndDropAdapter pickAndDropAdapter;
    private PickAndDropViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPickUpAndDropBinding.inflate(inflater,container,false);
        View root = binding.getRoot();
        viewModel = new ViewModelProvider(this).get(PickAndDropViewModel.class);
        viewModel.getPickAndDropDetailsFromServer(getContext());

        viewModel.getMutableLivePickAndDropDetailsData().observe(getViewLifecycleOwner(), new Observer<PickAndDropDetails>() {
            @Override
            public void onChanged(PickAndDropDetails pickAndDropDetails) {
                binding.chargesMessage.setText(pickAndDropDetails.getPricings());
                setUpBannerImages(pickAndDropDetails.getCarriablesImage());
                binding.loading.setVisibility(View.GONE);
                binding.mainContent.setVisibility(View.VISIBLE);
            }
        });

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(root).popBackStack();
            }
        });

        binding.helpline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNo = InfoConstats.CALLING_NUMBER;
                Intent callingIntent = new Intent();
                callingIntent.setAction(Intent.ACTION_DIAL);
                callingIntent.setData(Uri.parse("tel: " + phoneNo));
                startActivity(callingIntent);
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
                    binding.dropOffLocationWrapperView.setVisibility(View.VISIBLE);
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

        binding.submitPickupItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currItem.setName(binding.menuItemInput.getText().toString());
                pickAndDropAdapter.addItem(currItem);

                // Clear Everything
                currItem = new CartItemData();
                binding.menuItemInput.setText("");
                binding.quantityText.setText(String.valueOf(1));
            }
        });

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
                String pickUpPhoneNo = binding.pickUpPhoneNo.getText().toString();
                String dropOffPhoneNo = binding.dropOffPhoneNo.getText().toString();

                if(!Validation.validateNormalData(pickUpLocation)){
                    binding.pickUpLocation.setError("Please Enter Valid Location");
                }

                if(!Validation.validateNormalData(dropOffLocation)){
                    binding.dropOffLocation.setError("Please Enter Location");
                }

                if(!Validation.validatePhoneNo(pickUpPhoneNo)){
                    binding.pickUpPhoneNo.setError("Please Enter Pick Up Phone Number");
                }

                if(!Validation.validatePhoneNo(dropOffPhoneNo)){
                    binding.dropOffPhoneNo.setError("Please Enter Drop Off Phone Number");
                }

                if(!(Validation.validateNormalData(pickUpLocation)
                && Validation.validateNormalData(dropOffLocation) && Validation.validatePhoneNo(pickUpPhoneNo)
                && Validation.validatePhoneNo(dropOffPhoneNo))){
                    Toasty.error(getContext(),"Please Enter All Data",Toasty.LENGTH_SHORT)
                            .show();
                    return;
                }

                if(selectedOption == 0 && !Validation.validateNormalData(shopName)){
                    Toasty.error(getContext(),"Please Enter Shop Name",Toasty.LENGTH_SHORT)
                            .show();
                    binding.storeNameView.setError("Please Enter Valid ShopName");
                    return;
                }

                if(pickAndDropAdapter.getAllItems().size() == 0){
                    Toasty.error(getContext(),"Please Enter At Least One Item",Toasty.LENGTH_SHORT)
                            .show();
                    binding.menuItemInput.setError("Please Add / Press Submit On Keyboard");
                    return;
                }

                openWhatsapp(shopName,pickUpLocation,specialInstructions,dropOffLocation,pickUpPhoneNo,dropOffPhoneNo);
            }
        });

        return root;
    }

    private void setUpBannerImages(List<BannerData> bannerData){
        CarouselView carouselView = binding.bannerCarousel;
        carouselView.setSize(bannerData.size());


        carouselView.setCarouselViewListener(new CarouselViewListener() {
            @Override
            public void onBindView(View view, int position) {
                ImageView imageView = view.findViewById(R.id.imageView);
                Glide.with(getContext())
                        .load(bannerData.get(position).getImageURL())
                        .into(imageView);
            }
        });

        carouselView.show();
    }

    private void openWhatsapp(String shopName,String pickUpLocation,String specialInstruction,String dropOffLocation,String pickUpPhoneNo,String dropOffPhoneNo){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String mobileNumber = InfoConstats.WHATSAPP_NUMBER;
        String message;

        StringBuilder itemsDetails = new StringBuilder();
        int i=1;
        for(CartItemData cartItemData : pickAndDropAdapter.getAllItems()){
            itemsDetails.append(i + ") Item:" + cartItemData.getName() + ", Quantity: " + cartItemData.getQuantity() + "\n");
            i++;
        }

        if(selectedOption == 0){
            message = "Shop Name: " + shopName + "\n" + "Pick Up Location: " + pickUpLocation  + "Pick Up Phone Number: " + pickUpPhoneNo + "\n"
                    + "Drop Location: " + dropOffLocation + "\n"  + "Drop Phone Number: " + dropOffPhoneNo + "\n"
                    + "Special Instruction: " + specialInstruction + "\n" + "---------------" + "\n" + "Items " + itemsDetails.toString();
        }
        else {
            message = "Pick Up Location: " + pickUpLocation + "\n" + "Pick Up Phone Number: " + pickUpPhoneNo + "\n"
                    + "Drop Location: " + dropOffLocation + "\n"  + "Drop Phone Number: " + dropOffPhoneNo + "\n"
                    + "Special Instruction: " + specialInstruction + "\n" + "---------------" + "\n" + "Items " + itemsDetails.toString();
        }
        intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"+91"+mobileNumber + "&text="+ message));
        startActivity(intent);
    }

}