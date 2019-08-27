package com.mandy.innfedia.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.RandomTransitionGenerator;
import com.mandy.innfedia.MainActivity;
import com.mandy.innfedia.R;
import com.mandy.innfedia.Utils.SharedToken;

public class SplashScreen extends AppCompatActivity {

    ImageView imageView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.text);


        final SharedToken sharedToken = new SharedToken(SplashScreen.this);


//         animation for image logo
//        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim);
//        animation.setInterpolator((new AccelerateDecelerateInterpolator()));
//        animation.setFillAfter(true);
//        imageView.setAnimation(animation);


//        //animation for text

//        Animation animtext = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim2);
//        animtext.setInterpolator((new AccelerateDecelerateInterpolator()));
//        animtext.setFillAfter(true);
//        textView.setAnimation(animtext);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (TextUtils.isEmpty(sharedToken.getShared())) {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }

            }
        }, 3000);

    }
}
