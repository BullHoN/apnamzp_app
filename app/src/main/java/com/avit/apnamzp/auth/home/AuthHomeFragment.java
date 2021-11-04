package com.avit.apnamzp.auth.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avit.apnamzp.R;
import com.avit.apnamzp.databinding.FragmentAuthHomeBinding;
import com.avit.apnamzp.network.NetworkApi;
import com.avit.apnamzp.network.RetrofitClient;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AuthHomeFragment extends Fragment {

    private FragmentAuthHomeBinding binding;
    private String TAG = "AuthActivity";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAuthHomeBinding.inflate(inflater,container,false);

//        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_authHomeFragment_to_authProfileFragment);

        binding.continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNo = binding.phoneNo.getText().toString();
                if(!isPhoneNoValid(phoneNo)){
                    Toasty.error(getContext(),"Invalid Phone Number",Toasty.LENGTH_SHORT)
                            .show();
                    return;
                }

                authorizeUser(phoneNo);

            }
        });

        return binding.getRoot();
    }

    private void authorizeUser(String phoneNo){
        Retrofit retrofit = RetrofitClient.getInstance();
        NetworkApi networkApi = retrofit.create(NetworkApi.class);

        Call<Boolean> call = networkApi.doesUsersExists(phoneNo);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                Log.i(TAG, "onResponse: " + response.body());

                if(!response.body()){
                    Bundle bundle = new Bundle();
                    bundle.putString("phoneNo",phoneNo);

                    Navigation.findNavController(binding.getRoot()).navigate(R.id.action_authHomeFragment_to_authOtpFragment,bundle);
                }
                else {
                    // Show The Enter Password Editbox
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toasty.error(getContext(),t.getMessage(),Toasty.LENGTH_SHORT)
                        .show();
            }
        });

    }

    private boolean isPhoneNoValid(String phoneNo){
        if(phoneNo.length() != 10) return false;
        for(int i=0;i<phoneNo.length();i++){
            if(!"0123456789".contains("" + phoneNo.charAt(i))){
                return false;
            }
        }

        return true;
    }

}