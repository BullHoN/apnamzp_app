package com.avit.apnamzp.ui.userdetails;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avit.apnamzp.R;
import com.avit.apnamzp.databinding.FragmentUserDetailsBinding;
import com.avit.apnamzp.localdb.User;

public class UserDetailsFragment extends Fragment {

    private FragmentUserDetailsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserDetailsBinding.inflate(inflater,container,false);

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(binding.getRoot()).popBackStack();
            }
        });

        binding.phoneNo.setText("+91 " + User.getPhoneNumber(getContext()));
        binding.username.setText(User.getUsername(getContext()));
        binding.addressText.setText(User.getGoogleMapStreetAddress(getContext()));

        return binding.getRoot();
    }
}