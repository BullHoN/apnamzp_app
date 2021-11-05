package com.avit.apnamzp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.avit.apnamzp.auth.AuthActivity;
import com.avit.apnamzp.localdb.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // CHECK FOR AUTENTICATION
        Intent intent;
        if(User.getIsVerified(getApplicationContext())){
            intent = new Intent(getApplicationContext(),HomeActivity.class);
        }
        else {
            intent = new Intent(getApplicationContext(), AuthActivity.class);
        }

        new CountDownTimer(1500,750){

            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                startActivity(intent);
                finish();
            }
        }.start();

    }
}