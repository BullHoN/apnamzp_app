package com.avit.apnamzp.auth.profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avit.apnamzp.HomeActivity;
import com.avit.apnamzp.R;
import com.avit.apnamzp.databinding.FragmentAuthProfileBinding;
import com.avit.apnamzp.dialogs.LoadingDialog;
import com.avit.apnamzp.models.User;
import com.avit.apnamzp.models.network.NetworkResponse;
import com.avit.apnamzp.network.NetworkApi;
import com.avit.apnamzp.network.RetrofitClient;
import com.avit.apnamzp.utils.DisplayMessage;
import com.avit.apnamzp.utils.ErrorUtils;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AuthProfileFragment extends Fragment {

    private FragmentAuthProfileBinding binding;
    private LoadingDialog loadingDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAuthProfileBinding.inflate(inflater,container,false);
        loadingDialog = new LoadingDialog(getActivity());

        Bundle bundle = getArguments();
        String phoneNo = bundle.getString("phoneNo");

        binding.phoneNoView.setText(phoneNo);

        binding.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.nameView.getText().toString();
//                String password = binding.passwordView.getText().toString();
//                String conformPassword = binding.conformPasswordView.getText().toString();

                if(name.length() <= 1){
                    DisplayMessage.errorMessage(getContext(),"Enter Valid Name",Toasty.LENGTH_SHORT);

                    binding.nameView.setError("Enter Valid Name");
                    return;
                }

//                if(password.length() < 5){
//                    DisplayMessage.errorMessage(getContext(),"Too Short Password",Toasty.LENGTH_SHORT);
//                    binding.passwordView.setError("Too Short Message");
//                    return;
//                }
//
//                if(!password.equals(conformPassword)){
//                    DisplayMessage.errorMessage(getContext(),"Passwords Do Not Match",Toasty.LENGTH_SHORT);
//                    binding.conformPasswordView.setError("Passwords Do Not Match");
//                    return;
//                }

                registerUser(new User(name,phoneNo,null));

            }
        });

        return binding.getRoot();
    }

    private void registerUser(User user){
        loadingDialog.startLoadingDialog();
        Retrofit retrofit = RetrofitClient.getInstance();
        NetworkApi networkApi = retrofit.create(NetworkApi.class);

        Call<NetworkResponse> call = networkApi.registerUser(user);
        call.enqueue(new Callback<NetworkResponse>() {
            @Override
            public void onResponse(Call<NetworkResponse> call, Response<NetworkResponse> response) {
                loadingDialog.dismissDialog();

                if(response.isSuccessful()){
                    Intent intent = new Intent(getContext(),HomeActivity.class);
                    startActivity(intent);
                    getActivity().finish();

                    com.avit.apnamzp.localdb.User.setUsername(getContext(),user.getName());
                    com.avit.apnamzp.localdb.User.setPhoneNumber(getContext(),user.getPhoneNo());
                    com.avit.apnamzp.localdb.User.setIsVerified(getContext(),true);
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