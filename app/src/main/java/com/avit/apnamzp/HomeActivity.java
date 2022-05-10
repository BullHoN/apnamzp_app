package com.avit.apnamzp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.accessibilityservice.GestureDescription;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.avit.apnamzp.localdb.Cart;
import com.avit.apnamzp.localdb.User;
import com.avit.apnamzp.network.NetworkApi;
import com.avit.apnamzp.network.RetrofitClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeActivity extends AppCompatActivity {

    private String TAG = "HomeActivityToken";
    private BroadcastReceiver receiver;
    private IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);

        NavController navController = Navigation.findNavController(this,R.id.fragmentContainerView);
        NavigationUI.setupWithNavController(bottomNavigationView,navController);

        String orderId = getIntent().getStringExtra("orderId");
        if(orderId != null){
            Bundle bundle = new Bundle();
            bundle.putString("orderId",orderId);
            navController.navigate(R.id.orderDetailsFragment,bundle);
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

                        String prevFcmToken = User.getFCMToken(getApplicationContext());

                        if(prevFcmToken == "" || token != prevFcmToken){
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

        Call<ResponseBody> call = networkApi.updateFCMToken(user,"user");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i(TAG, "onResponse: fcm token updated");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "onFailure: token not saved", t);
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
}