package com.avit.apnamzp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.accessibilityservice.GestureDescription;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.avit.apnamzp.localdb.Cart;
import com.avit.apnamzp.localdb.User;
import com.avit.apnamzp.models.network.NetworkResponse;
import com.avit.apnamzp.network.NetworkApi;
import com.avit.apnamzp.network.RetrofitClient;
import com.avit.apnamzp.utils.DisplayMessage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.firebase.messaging.FirebaseMessaging;
import com.razorpay.PaymentResultListener;

import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeActivity extends AppCompatActivity  implements PaymentResultListener{

    private String TAG = "HomeActivityToken";
    private BroadcastReceiver receiver;
    private IntentFilter intentFilter;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);

        navController = Navigation.findNavController(this,R.id.fragmentContainerView);
        NavigationUI.setupWithNavController(bottomNavigationView,navController);

        Log.i(TAG, "onCreate: " + User.getPhoneNumber(getApplicationContext()));

        FirebaseCrashlytics.getInstance().setUserId(User.getPhoneNumber(getApplicationContext()));
        FirebaseMessaging.getInstance().subscribeToTopic("apnamzp_user");


        String orderId = getIntent().getStringExtra("orderId");
        if(orderId != null){
            Bundle bundle = new Bundle();
            bundle.putString("orderId",orderId);
            navController.navigate(R.id.orderDetailsFragment,bundle);
        }

        if(getIntent() != null && getIntent().getBooleanExtra("open_orders",false)){
            navController.navigate(R.id.successFragment);
        }

        String shopId = getIntent().getStringExtra("shopId");

        if(shopId != null){
            Bundle bundle = new Bundle();
            bundle.putString("shopId", shopId);
            navController.navigate(R.id.shopDetailsFragment,bundle);
        }

        updateTheCartBatch();

        // Firebase Token
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        Log.i(TAG, "onComplete: " + token);

                        if(true){
                            User.setFCMToken(getApplicationContext(),token);
                            updateFCMToken(token);
                        }
                    }
                });

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle bundle = new Bundle();
                bundle.putString("orderId",intent.getStringExtra("orderId"));
                navController.navigate(R.id.orderDetailsFragment,bundle);
            }
        };

        intentFilter = new IntentFilter();
        intentFilter.addAction("com.avit.apnamzp.ORDER_STATUS_UPDATE");

    }

    private void updateFCMToken(String fcmToken){
        Retrofit retrofit = RetrofitClient.getInstance();
        NetworkApi networkApi = retrofit.create(NetworkApi.class);

        com.avit.apnamzp.models.User user = new com.avit.apnamzp.models.User(User.getPhoneNumber(getApplicationContext()),fcmToken);

        Call<NetworkResponse> call = networkApi.updateFCMToken(user,"user");
        call.enqueue(new Callback<NetworkResponse>() {
            @Override
            public void onResponse(Call<NetworkResponse> call, Response<NetworkResponse> response) {
                if(response.isSuccessful()){
                    Log.i(TAG, "onResponse: token updated successfully");
                }
                else {
                    NetworkResponse errorResponse = response.body();
                    DisplayMessage.errorMessage(getApplicationContext(),errorResponse.getDesc(),Toasty.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<NetworkResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: token not saved", t);
                DisplayMessage.errorMessage(getApplicationContext(),"onFailure: token not saved",Toasty.LENGTH_SHORT);
            }
        });

    }

    private void updateTheCartBatch(){

        Cart cart = Cart.getInstance(getApplicationContext());
        if(cart == null) return;

        int cartSize = cart.getCartSize();
        if(cartSize == 0) return;

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        BadgeDrawable badge = bottomNavigationView.getOrCreateBadge(R.id.cartFragment);

        badge.setNumber(cartSize);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver,intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
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
        Log.i(TAG, "onPaymentError: " + s + i);
        DisplayMessage.errorMessage(getApplicationContext(),"Payment Failed",Toasty.LENGTH_SHORT);
    }
}