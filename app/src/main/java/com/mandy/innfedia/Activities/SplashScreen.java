package com.mandy.innfedia.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.RandomTransitionGenerator;
import com.mandy.innfedia.R;

public class SplashScreen extends AppCompatActivity {

    KenBurnsView kenBurnsView;
    ImageView imageView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        kenBurnsView = findViewById(R.id.image);
        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.text);


        // animation for image logo
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim);
        animation.setInterpolator((new AccelerateDecelerateInterpolator()));
        animation.setFillAfter(true);
        imageView.setAnimation(animation);


        //animation for text

        Animation animtext = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim2);
        animtext.setInterpolator((new AccelerateDecelerateInterpolator()));
        animtext.setFillAfter(true);
        textView.setAnimation(animtext);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();

            }
        }, 4000);

    }
}
