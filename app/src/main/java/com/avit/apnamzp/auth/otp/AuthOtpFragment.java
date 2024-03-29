package com.avit.apnamzp.auth.otp;

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
import com.avit.apnamzp.databinding.FragmentAuthOtpBinding;
import com.avit.apnamzp.dialogs.LoadingDialog;
import com.avit.apnamzp.localdb.User;
import com.avit.apnamzp.models.network.NetworkResponse;
import com.avit.apnamzp.network.NetworkApi;
import com.avit.apnamzp.network.RetrofitClient;
import com.avit.apnamzp.utils.DisplayMessage;
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

        binding.registedMobileNo.setText("Enter the OTP received on the registered mobile number. " + phoneNo);

        binding.changeNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(binding.getRoot()).popBackStack();
            }
        });

        binding.otpView.setTextColor(getResources().getColor(R.color.black));
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

    void sendOtp(String phoneNo){
        Retrofit retrofit = RetrofitClient.getInstance();
        NetworkApi networkApi = retrofit.create(NetworkApi.class);

        Call<NetworkResponse> call = networkApi.sendOtp(phoneNo);
        call.enqueue(new Callback<NetworkResponse>() {
            @Override
            public void onResponse(Call<NetworkResponse> call, Response<NetworkResponse> response) {
                loadingDialog.dismissDialog();

                if(response.isSuccessful()){
                    DisplayMessage.successMessage(getContext(),"Otp Successfully Send",Toasty.LENGTH_SHORT);
                }
                else {
                    NetworkResponse errorResponse = ErrorUtils.parseErrorResponse(response);
                    DisplayMessage.errorMessage(getContext(), errorResponse.getDesc(), Toasty.LENGTH_SHORT);
                }

            }

            @Override
            public void onFailure(Call<NetworkResponse> call, Throwable t) {
                loadingDialog.dismissDialog();
                DisplayMessage.errorMessage(getContext(),t.getMessage(),Toasty.LENGTH_SHORT);
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

                        DisplayMessage.successMessage(getContext(),"Successfully Verified",Toasty.LENGTH_SHORT);
//                        Log.i(TAG, "onResponse: " + successResponse.getDesc() + " " + successResponse.getData());
                        if(successResponse.getDesc() != null && successResponse.getDesc().contains("verfied")){

                            User.setUsername(getContext(),successResponse.getData());
                            User.setPhoneNumber(getContext(),phoneNo);
                            User.setIsVerified(getContext(),true);

                            Intent intent = new Intent(getContext(), HomeActivity.class);
                            startActivity(intent);
                            getActivity().finish();

                        }
                        else {
                            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_authOtpFragment_to_authProfileFragment,getArguments());
                        }
                    }
                    else {
                        DisplayMessage.errorMessage(getContext(),"Incorrect OTP",Toasty.LENGTH_SHORT);
                    }
                }
                else {
                    NetworkResponse errorResponse = ErrorUtils.parseErrorResponse(response);
                    DisplayMessage.errorMessage(getContext(), errorResponse.getDesc(), Toasty.LENGTH_SHORT);
                }


            }

            @Override
            public void onFailure(Call<NetworkResponse> call, Throwable t) {
                loadingDialog.dismissDialog();
                DisplayMessage.errorMessage(getContext(),t.getMessage(),Toasty.LENGTH_SHORT);
            }
        });
    }

}