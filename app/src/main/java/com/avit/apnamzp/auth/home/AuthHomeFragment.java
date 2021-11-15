package com.avit.apnamzp.auth.home;

import android.content.Intent;
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
import com.avit.apnamzp.network.NetworkApi;
import com.avit.apnamzp.network.RetrofitClient;

import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AuthHomeFragment extends Fragment {

    private FragmentAuthHomeBinding binding;
    private String TAG = "AuthActivity";
    private LoadingDialog loadingDialog;

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

        return binding.getRoot();
    }

    private void sendOtp(String phoneNo){
        Retrofit retrofit = RetrofitClient.getInstance();
        NetworkApi networkApi = retrofit.create(NetworkApi.class);

        Call<ResponseBody> call = networkApi.sendOtp(phoneNo);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toasty.error(getContext(),t.getMessage(),Toasty.LENGTH_SHORT)
                        .show();
            }
        });
    }

    private void login(String phoneNo,String password){
        Retrofit retrofit = RetrofitClient.getInstance();
        NetworkApi networkApi  = retrofit.create(NetworkApi.class);

        Call<Boolean> call = networkApi.login(phoneNo,password);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.body()){
                    Toasty.success(getContext(),"Login Successfull",Toasty.LENGTH_SHORT)
                            .show();
                    loadingDialog.dismissDialog();

                    com.avit.apnamzp.localdb.User.setPhoneNumber(getContext(),phoneNo);
                    com.avit.apnamzp.localdb.User.setIsVerified(getContext(),true);

                    Intent intent = new Intent(getContext(),HomeActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
                else {
                    loadingDialog.dismissDialog();
                    Toasty.error(getContext(),"Wrong password",Toasty.LENGTH_SHORT)
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

    private void authorizeUser(String phoneNo){
        Retrofit retrofit = RetrofitClient.getInstance();
        NetworkApi networkApi = retrofit.create(NetworkApi.class);

        Call<Boolean> call = networkApi.doesUsersExists(phoneNo);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                Log.i(TAG, "onResponse: " + response.body());
                loadingDialog.dismissDialog();
                if(!response.body()){
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

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
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