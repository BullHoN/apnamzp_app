package com.avit.apnamzp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;

import com.avit.apnamzp.auth.AuthActivity;
import com.avit.apnamzp.localdb.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {

    private int APP_UPDATE_CODE = 18;
    private AppUpdateManager appUpdateManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appUpdateManager = AppUpdateManagerFactory.create(this);

        appUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(@NonNull AppUpdateInfo appUpdateInfo) {
                if(appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                        && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)){
                    try {
                        appUpdateManager.startUpdateFlowForResult(appUpdateInfo,AppUpdateType.IMMEDIATE,MainActivity.this,APP_UPDATE_CODE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                        Toasty.error(getApplicationContext(),"Some Error Occurred While Updating Your App",Toasty.LENGTH_SHORT)
                                .show();
                    }
                }
                else {
                    moveToNextActivity();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                moveToNextActivity();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        if(appUpdateManager == null) return;

        appUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(@NonNull AppUpdateInfo appUpdateInfo) {
                if(appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS){
                    try {
                        appUpdateManager.startUpdateFlowForResult(appUpdateInfo,AppUpdateType.IMMEDIATE,MainActivity.this,APP_UPDATE_CODE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                        Toasty.error(getApplicationContext(),"Some Error Occurred While Updating Your App",Toasty.LENGTH_SHORT)
                                .show();
                    }
                }
            }
        });

    }

    private void moveToNextActivity(){
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == APP_UPDATE_CODE){
            if(resultCode != RESULT_OK){
                Toasty.error(getApplicationContext(),"Update Failed Please Try Again",Toasty.LENGTH_SHORT)
                        .show();
            }
            else if(requestCode == RESULT_OK){
                Toasty.success(getApplicationContext(),"Update Was Successfull, Please Restart The App",Toasty.LENGTH_SHORT)
                        .show();
                moveToNextActivity();

            }
        }

    }

}