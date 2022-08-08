package com.avit.apnamzp.auth.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avit.apnamzp.HomeActivity;
import com.avit.apnamzp.R;
import com.avit.apnamzp.databinding.FragmentAuthHomeBinding;
import com.avit.apnamzp.dialogs.LoadingDialog;
import com.avit.apnamzp.models.User;
import com.avit.apnamzp.models.network.NetworkResponse;
import com.avit.apnamzp.network.NetworkApi;
import com.avit.apnamzp.network.RetrofitClient;
import com.avit.apnamzp.utils.ErrorUtils;
import com.google.gson.Gson;

import java.io.IOException;

import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AuthHomeFragment extends Fragment {

    private FragmentAuthHomeBinding binding;
    private String TAG = "AuthActivitys";
    private LoadingDialog loadingDialog;
    private Gson gson;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAuthHomeBinding.inflate(inflater,container,false);
        loadingDialog = new LoadingDialog(getActivity());

        binding.continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNo = binding.phoneNo.getText().toString();
                String password = binding.passwordView.getText().toString();
                if(!isPhoneNoValid(phoneNo)){
                    Toasty.error(getContext(),"Invalid Phone Number",Toasty.LENGTH_SHORT)
                            .show();
                    return;
                }

                if(binding.passwordView.getVisibility() == View.VISIBLE){
                    if(password.length() == 0){
                        Toasty.error(getContext(),"Enter Password",Toasty.LENGTH_SHORT)
                                .show();
                        return;
                    }
                    loadingDialog.startLoadingDialog();
                    login(phoneNo,password);
                }
                else {
                    loadingDialog.startLoadingDialog();
                    authorizeUser(phoneNo);
                }

            }
        });

        binding.forgetPasswordView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNo = binding.phoneNo.getText().toString();
                if(!isPhoneNoValid(phoneNo)){
                    Toasty.error(getContext(),"Invalid Phone Number",Toasty.LENGTH_SHORT)
                            .show();
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("phoneNo",phoneNo);
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_authHomeFragment_to_authOtpFragment,bundle);
                sendOtp(phoneNo);
            }
        });

        binding.callHelplineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNo = "9565820009";
                Intent callingIntent = new Intent();
                callingIntent.setAction(Intent.ACTION_DIAL);
                callingIntent.setData(Uri.parse("tel: " + phoneNo));
                startActivity(callingIntent);
            }
        });

        return binding.getRoot();
    }

    private void sendOtp(String phoneNo){
        Retrofit retrofit = RetrofitClient.getInstance();
        NetworkApi networkApi = retrofit.create(NetworkApi.class);

        Call<NetworkResponse> call = networkApi.sendOtp(phoneNo);
        call.enqueue(new Callback<NetworkResponse>() {
            @Override
            public void onResponse(Call<NetworkResponse> call, Response<NetworkResponse> response) {
                if(response.isSuccessful()){
                    NetworkResponse successResponse = response.body();
                    if(successResponse.isSuccess()){
                        Toasty.normal(getContext(),"OTP Sended",Toasty.LENGTH_SHORT)
                                .show();
                    }
                }
                else {
                    NetworkResponse errorResponse = response.body();
                    Toasty.error(getContext(),errorResponse.getDesc(),Toasty.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onFailure(Call<NetworkResponse> call, Throwable t) {
                Toasty.error(getContext(),t.getMessage(),Toasty.LENGTH_SHORT)
                        .show();
            }
        });
    }

    private void login(String phoneNo,String password){
        Retrofit retrofit = RetrofitClient.getInstance();
        NetworkApi networkApi  = retrofit.create(NetworkApi.class);

        Call<NetworkResponse> call = networkApi.login(phoneNo,password);

        call.enqueue(new Callback<NetworkResponse>() {
            @Override
            public void onResponse(Call<NetworkResponse> call, Response<NetworkResponse> response) {

                if(response.isSuccessful()){
                    NetworkResponse networkResponse = response.body();

                    if(networkResponse == null){
                        Log.i(TAG, "onResponse: " + networkResponse);
                    }

                    if(networkResponse.isSuccess()){
                        Toasty.success(getContext(),"Login Successfull",Toasty.LENGTH_SHORT)
                                .show();
                        loadingDialog.dismissDialog();

                        String userName = networkResponse.getData();
//                        Log.i(TAG, "onResponse: " + userName);

                        com.avit.apnamzp.localdb.User.setUsername(getContext(),userName);
                        com.avit.apnamzp.localdb.User.setPhoneNumber(getContext(),phoneNo);
                        com.avit.apnamzp.localdb.User.setIsVerified(getContext(),true);

                        Intent intent = new Intent(getContext(),HomeActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                    else {
                        loadingDialog.dismissDialog();
                        Toasty.error(getContext(),networkResponse.getDesc(),Toasty.LENGTH_SHORT)
                                .show();
                    }
                }
                else {
                    NetworkResponse errorResponse = ErrorUtils.parseErrorResponse(response);
                    loadingDialog.dismissDialog();
                    Toasty.error(getContext(),errorResponse.getDesc(),Toasty.LENGTH_SHORT)
                            .show();
                }

            }

            @Override
            public void onFailure(Call<NetworkResponse> call, Throwable t) {
                Toasty.error(getContext(),t.getMessage(),Toasty.LENGTH_SHORT)
                        .show();
            }
        });

    }

    private void authorizeUser(String phoneNo){
        Retrofit retrofit = RetrofitClient.getInstance();
        NetworkApi networkApi = retrofit.create(NetworkApi.class);

        Call<NetworkResponse> call = networkApi.doesUsersExists(phoneNo);
        call.enqueue(new Callback<NetworkResponse>() {
            @Override
            public void onResponse(Call<NetworkResponse> call, Response<NetworkResponse> response) {

                if(response.isSuccessful()){
                    NetworkResponse successResponse = response.body();

                    loadingDialog.dismissDialog();
                    if(!successResponse.isSuccess()){
                        Bundle bundle = new Bundle();
                        bundle.putString("phoneNo",phoneNo);

                        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_authHomeFragment_to_authOtpFragment,bundle);
                    }
                    else {
                        // Show The Enter Password EditBox
                        binding.passwordView.setVisibility(View.VISIBLE);
                        binding.forgetPasswordView.setVisibility(View.VISIBLE);
                    }
                }
                else {
                    NetworkResponse errorResponse = ErrorUtils.parseErrorResponse(response);
                    Toasty.error(getContext(),errorResponse.getDesc(),Toasty.LENGTH_SHORT)
                            .show();
                }

            }

            @Override
            public void onFailure(Call<NetworkResponse> call, Throwable t) {
                loadingDialog.dismissDialog();
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