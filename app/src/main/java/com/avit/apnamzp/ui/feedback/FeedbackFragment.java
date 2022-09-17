package com.avit.apnamzp.ui.feedback;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avit.apnamzp.R;
import com.avit.apnamzp.databinding.FragmentFeedbackBinding;
import com.avit.apnamzp.localdb.User;
import com.avit.apnamzp.models.ReviewData;
import com.avit.apnamzp.models.feedback.Feedback;
import com.avit.apnamzp.models.network.NetworkResponse;
import com.avit.apnamzp.network.NetworkApi;
import com.avit.apnamzp.network.RetrofitClient;
import com.avit.apnamzp.utils.ErrorUtils;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class FeedbackFragment extends Fragment {

    private FragmentFeedbackBinding binding;
    private String TAG = "FeedbackFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFeedbackBinding.inflate(inflater,container,false);

        Bundle bundle = getArguments();

        String shopName = bundle.getString("shopId");
        String orderId = bundle.getString("orderId");

        binding.submitReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String foodReview = binding.foodReview.getText().toString();
                String foodRating = (String.valueOf(Math.round(binding.foodRating.getRating())));

                String deliverySathiRating = (String.valueOf(Math.round(binding.deliverySathiRating.getRating())));
                String deliverySathiRevew = binding.deliverySathiReview.getText().toString();

                String apnaRating = (String.valueOf(Math.round(binding.apnaRating.getRating())));
                String apnaRatingReview = binding.apnaReview.getText().toString();

                String userName = User.getUsername(getContext());
                Log.i(TAG, "onClick: " + userName);

                ReviewData foodFeedBack = null;
                if(!foodRating.equals("0") || foodReview.length() != 0){
                    foodFeedBack = new ReviewData(userName,foodRating,
                            foodReview,"shop",shopName,orderId);
                }

                ReviewData deliverySathiFeedback = null;
                if(!deliverySathiRating.equals("0") || deliverySathiRevew.length() != 0){
                    deliverySathiFeedback = new ReviewData(userName,deliverySathiRating,
                            deliverySathiRevew,"sathi",shopName,orderId);
                }

                ReviewData apnaFeedback = null;
                if(!apnaRating.equals("0") || apnaRatingReview.length() != 0){
                    apnaFeedback = new ReviewData(userName,apnaRating,
                            apnaRatingReview,"apna",shopName,orderId);
                }

                Feedback finalFeedback = new Feedback(foodFeedBack,deliverySathiFeedback,apnaFeedback);
                sendFeedbackToServer(finalFeedback);
            }
        });

        return binding.getRoot();
    }

    private void sendFeedbackToServer(Feedback finalFeedback){
        Retrofit retrofit = RetrofitClient.getInstance();
        NetworkApi networkApi = retrofit.create(NetworkApi.class);

        Call<NetworkResponse> call = networkApi.postFeedback(finalFeedback);
        call.enqueue(new Callback<NetworkResponse>() {
            @Override
            public void onResponse(Call<NetworkResponse> call, Response<NetworkResponse> response) {
                if(!response.isSuccessful()){
                    NetworkResponse errorResponse = ErrorUtils.parseErrorResponse(response);
                    Toasty.error(getContext(),errorResponse.getDesc(),Toasty.LENGTH_SHORT)
                            .show();
                    return;
                }

                Toasty.success(getContext(),"Thanks for providing feedback",Toasty.LENGTH_SHORT)
                        .show();
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_feedbackFragment_to_ordersFragment);
            }

            @Override
            public void onFailure(Call<NetworkResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });


    }


}