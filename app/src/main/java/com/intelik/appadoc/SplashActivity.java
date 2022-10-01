package com.intelik.appadoc;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.intelik.appadoc.utils.DBHelper;

public class SplashActivity extends AppCompatActivity {

    private Long duration = 3000L;
    private ImageView logo;

    Context context;


    private FirebaseAuth mAuth;

    private String TAG ="SPLASHActivity";

    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mAuth = FirebaseAuth.getInstance();

        logo = (ImageView) findViewById(R.id.adoc_logo);

        Log.e(TAG, "Iniciando Activity SPLASH");

        Bundle bundle = getIntent().getExtras();

        if (bundle == null){

            Log.e(TAG, "Bundle is null");
        }
        else {
            Log.e(TAG, "Bundle has data");
        }
        if (bundle != null) {
                /*
            for (String key : bundle.keySet()) {
                Log.e(TAG, key + " : " + (bundle.get(key) != null ? bundle.get(key) : "NULL"));
            }
        */

            if (getIntent().hasExtra("pushnotification")) {

                if (checkUser()) {
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra("pushnotification", "yes");
                    startActivity(intent);
                    finish();
                }

            }

            String title = "";
            String desc = "";
            String  image = "";

            if (getIntent().hasExtra("Title")) {
               title =  getIntent().getExtras().get("Title").toString();
            }

            if (getIntent().hasExtra("Message")) {
                desc =  getIntent().getExtras().get("Message").toString();
            }

            if (getIntent().hasExtra("Image")) {
                image =  getIntent().getExtras().get("Image").toString();
            }

            db = new DBHelper(this);
            db.insertNotification(title,desc,image);
            db.close();
            finish();

        }


        scheduleSplashScreen();

    }

    private void scheduleSplashScreen()
    {

        AlphaAnimation animation1 = new AlphaAnimation(0.2f, 1.0f);
        animation1.setDuration(2500);
        animation1.setStartOffset(10);
        animation1.setFillAfter(true);
        logo.startAnimation(animation1);


        DisplayMetrics metrics = getResources().getDisplayMetrics();

        ObjectAnimator translationY = ObjectAnimator.ofFloat(logo, "y", metrics.heightPixels / 4 - logo.getHeight() / 2); // metrics.heightPixels or root.getHeight()
        translationY.setDuration(2500);
        translationY.start();


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (!checkUser()) {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }

                else
                {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }





            }
        }, duration); //3000 L = 3 detik


    }

    private boolean checkUser()
    {

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null ) return true;
        else return  false;
    }




}