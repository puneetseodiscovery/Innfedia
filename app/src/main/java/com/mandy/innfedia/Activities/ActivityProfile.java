package com.mandy.innfedia.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.mandy.innfedia.R;

public class ActivityProfile extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        init();






    }

    private void init() {
        button = (Button) findViewById(R.id.btnProfile);
    }
}
