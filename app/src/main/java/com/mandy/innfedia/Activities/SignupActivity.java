package com.mandy.innfedia.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mandy.innfedia.MainActivity;
import com.mandy.innfedia.R;

public class SignupActivity extends AppCompatActivity {

    Button btnSignUp;
    TextView txtLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // here find all id
        init();

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), OtpActivity.class));
                finish();
            }
        });
    }

    private void init() {
        txtLogin = (TextView) findViewById(R.id.txtLogin);
        btnSignUp = (Button) findViewById(R.id.btnSignup);
    }
}
