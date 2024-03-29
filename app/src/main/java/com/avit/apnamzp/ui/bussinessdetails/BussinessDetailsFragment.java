package com.avit.apnamzp.ui.bussinessdetails;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avit.apnamzp.R;
import com.avit.apnamzp.databinding.FragmentBussinessDetailsBinding;

public class BussinessDetailsFragment extends Fragment {

    private FragmentBussinessDetailsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBussinessDetailsBinding.inflate(inflater,container,false);

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(binding.getRoot()).popBackStack();
            }
        });

        Bundle bundle = getArguments();

        binding.headerName.setText(bundle.getString("title"));
        binding.bodyText.setText(bundle.getInt("string_id"));

        return binding.getRoot();
    }
}