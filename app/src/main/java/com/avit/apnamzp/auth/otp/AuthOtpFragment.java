package com.avit.apnamzp.auth.otp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avit.apnamzp.R;
import com.avit.apnamzp.databinding.FragmentAuthOtpBinding;
import com.avit.apnamzp.network.NetworkApi;
import com.avit.apnamzp.network.RetrofitClient;
import com.mukesh.OnOtpCompletionListener;

import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AuthOtpFragment extends Fragment {

    private FragmentAuthOtpBinding binding;
    private String TAG = "AuthActivity";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAuthOtpBinding.inflate(inflater,container,false);

        Bundle bundle = getArguments();
        String phoneNo = bundle.getString("phoneNo");

        binding.otpView.setOtpCompletionListener(new OnOtpCompletionListener() {
            @Override
            public void onOtpCompleted(String otp) {
                verifyOtpFromServer(phoneNo,otp);
            }
        });

        binding.resendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendOtp(phoneNo);
            }
        });

        return binding.getRoot();
    }

    void sendOtp(String phoneNo){
        Retrofit retrofit = RetrofitClient.getInstance();
        NetworkApi networkApi = retrofit.create(NetworkApi.class);

        Call<ResponseBody> call = networkApi.sendOtp(phoneNo);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toasty.success(getContext(),"Otp Successfully send",Toasty.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toasty.error(getContext(),t.getMessage(),Toasty.LENGTH_SHORT)
                        .show();
            }
        });
    }

    void verifyOtpFromServer(String phoneNo,String otp){
        Retrofit retrofit = RetrofitClient.getInstance();
        NetworkApi networkApi = retrofit.create(NetworkApi.class);

        Call<Boolean> call = networkApi.verifyOtp(phoneNo,otp);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.body()){
                    Toasty.success(getContext(),"Successfully Verified",Toasty.LENGTH_SHORT)
                            .show();

                    Navigation.findNavController(binding.getRoot()).navigate(R.id.action_authOtpFragment_to_authProfileFragment,getArguments());
                }
                else {
                    Toasty.error(getContext(),"Incorrect OTP",Toasty.LENGTH_SHORT)
                        .show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toasty.error(getContext(),t.getMessage(),Toasty.LENGTH_SHORT)
                        .show();
            }
        });
    }

}