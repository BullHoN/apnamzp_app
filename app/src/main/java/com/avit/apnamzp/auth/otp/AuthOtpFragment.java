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
import com.avit.apnamzp.dialogs.LoadingDialog;
import com.avit.apnamzp.models.network.NetworkResponse;
import com.avit.apnamzp.network.NetworkApi;
import com.avit.apnamzp.network.RetrofitClient;
import com.avit.apnamzp.utils.ErrorUtils;
import com.goodiebag.pinview.Pinview;
import com.mukesh.OnOtpCompletionListener;

import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AuthOtpFragment extends Fragment {

    private FragmentAuthOtpBinding binding;
    private String TAG = "AuthActivitys";
    private LoadingDialog loadingDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAuthOtpBinding.inflate(inflater,container,false);
        loadingDialog = new LoadingDialog(getActivity());

        Bundle bundle = getArguments();
        String phoneNo = bundle.getString("phoneNo");

        binding.otpView.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean fromUser) {
                Log.i(TAG, "onDataEntered: " + pinview.getValue());

                loadingDialog.startLoadingDialog();
                verifyOtpFromServer(phoneNo,pinview.getValue());

            }
        });

        binding.resendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingDialog.startLoadingDialog();
                sendOtp(phoneNo);
            }
        });

        return binding.getRoot();
    }

    void sendOtp(String phoneNo){
        Retrofit retrofit = RetrofitClient.getInstance();
        NetworkApi networkApi = retrofit.create(NetworkApi.class);

        Call<NetworkResponse> call = networkApi.sendOtp(phoneNo);
        call.enqueue(new Callback<NetworkResponse>() {
            @Override
            public void onResponse(Call<NetworkResponse> call, Response<NetworkResponse> response) {
                loadingDialog.dismissDialog();

                if(response.isSuccessful()){
                    Toasty.success(getContext(),"Otp Successfully send",Toasty.LENGTH_SHORT)
                            .show();
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

    void verifyOtpFromServer(String phoneNo,String otp){
        Retrofit retrofit = RetrofitClient.getInstance();
        NetworkApi networkApi = retrofit.create(NetworkApi.class);

        Call<NetworkResponse> call = networkApi.verifyOtp(phoneNo,otp);
        call.enqueue(new Callback<NetworkResponse>() {
            @Override
            public void onResponse(Call<NetworkResponse> call, Response<NetworkResponse> response) {
                loadingDialog.dismissDialog();

                if(response.isSuccessful()){
                    NetworkResponse successResponse = response.body();
                    if(successResponse.isSuccess()){
                        Toasty.success(getContext(),"Successfully Verified",Toasty.LENGTH_SHORT)
                                .show();

                        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_authOtpFragment_to_authProfileFragment,getArguments());
                    }
                    else {
                        Toasty.error(getContext(),"Incorrect OTP",Toasty.LENGTH_SHORT)
                                .show();
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

}