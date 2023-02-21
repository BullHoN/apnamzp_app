package com.avit.apnamzp.ui.payment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;

import com.avit.apnamzp.HomeActivity;
import com.avit.apnamzp.R;
import com.avit.apnamzp.localdb.User;
import com.avit.apnamzp.models.network.NetworkResponse;
import com.avit.apnamzp.models.order.OrderItem;
import com.avit.apnamzp.models.payment.OnlinePaymentOrderIdPostData;
import com.avit.apnamzp.models.payment.PaymentMetadata;
import com.avit.apnamzp.network.NetworkApi;
import com.avit.apnamzp.network.RetrofitClient;
import com.avit.apnamzp.utils.DisplayMessage;
import com.avit.apnamzp.utils.ErrorUtils;
import com.google.gson.Gson;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OnlinePaymentActivity extends AppCompatActivity implements PaymentResultListener {

    private String orderPaymentId;
    private OrderItem orderItem;
    private Gson gson;
    private String TAG = "OnlinePaymentActivitys";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_payment);

        Checkout.preload(getApplicationContext());
        gson = new Gson();

        orderPaymentId = getIntent().getStringExtra("orderPaymentId");

//        Log.i(TAG, "onCreate: " + orderPaymentId);

        String orderItemString = getIntent().getStringExtra("orderItem");
        orderItem = gson.fromJson(orderItemString,OrderItem.class);

        payOnline();
    }

    private void payOnline(){
        Checkout checkout = new Checkout();
        checkout.setImage(R.drawable.main_icon);

        try{
            JSONObject options = new JSONObject();
            options.put("name", "Apna MZP");
            options.put("description", "Reference No. " + orderPaymentId);
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("order_id", orderPaymentId);//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", String.valueOf(orderItem.getTotalPay() * 100));//pass amount in currency subunits
            options.put("prefill.contact",User.getPhoneNumber(getApplicationContext()));
            options.put("prefill.email","");
            options.put("readonly.email",true);

            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(this,options);
        }
        catch(Exception e){
            DisplayMessage.errorMessage(this,"Error in pyament " + e.getMessage(),Toasty.LENGTH_SHORT);
        }

    }

    @Override
    public void onPaymentSuccess(String s) {
        DisplayMessage.successMessage(getApplicationContext(),"Payment is Successfull",Toasty.LENGTH_SHORT);
        Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        intent.putExtra("open_orders",true);

        startActivity(intent);
    }

    @Override
    public void onPaymentError(int i, String s) {
        DisplayMessage.errorMessage(getApplicationContext(),"Payment Failed",Toasty.LENGTH_SHORT);
        finish();
    }

    private void checkout(String paymentResponse){
        Retrofit retrofit = RetrofitClient.getInstance();
        NetworkApi networkApi = retrofit.create(NetworkApi.class);

        orderItem.setPaymentId(paymentResponse);
        Call<NetworkResponse> call = networkApi.checkout(orderItem);
        call.enqueue(new Callback<NetworkResponse>() {
            @Override
            public void onResponse(Call<NetworkResponse> call, Response<NetworkResponse> response) {

                DisplayMessage.successMessage(getApplicationContext(),"Checkout Successfull",Toasty.LENGTH_SHORT);

                Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                intent.putExtra("open_orders",true);

                startActivity(intent);

            }

            @Override
            public void onFailure(Call<NetworkResponse> call, Throwable t) {
                DisplayMessage.errorMessage(getApplicationContext(),t.getMessage(),Toasty.LENGTH_SHORT);
            }
        });

    }

}