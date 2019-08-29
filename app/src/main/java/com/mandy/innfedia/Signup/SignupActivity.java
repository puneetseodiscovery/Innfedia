package com.mandy.innfedia.Signup;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mandy.innfedia.Retrofit.ApiInterface;
import com.mandy.innfedia.ApiModel.SignUpApi;
import com.mandy.innfedia.Utils.CheckInternet;
import com.mandy.innfedia.Utils.ProgressBarClass;
import com.mandy.innfedia.R;
import com.mandy.innfedia.Retrofit.ServiceGenerator;
import com.mandy.innfedia.Utils.Snack;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    Button btnSignUp;
    TextView txtLogin;
    EditText edtName, edtPhone, edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // here find all id
        init();

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), OtpActivity.class));
                finish();

            }
        });


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(edtName.getText().toString())) {
                    edtName.requestFocus();
                    edtName.setError(getResources().getString(R.string.invalid));
                } else if (TextUtils.isEmpty(edtPhone.getText().toString())) {
                    edtPhone.requestFocus();
                    edtPhone.setError(getResources().getString(R.string.invalid));

                } else if (edtPhone.getText().length() < 10) {
                    edtPhone.requestFocus();
                    edtPhone.setError("Enter 10 digit phone number");

                } else if (TextUtils.isEmpty(edtPassword.getText().toString())) {
                    edtPassword.requestFocus();
                    edtPassword.setError(getResources().getString(R.string.invalid));

                } else if (edtPassword.getText().length() < 8) {
                    edtPassword.requestFocus();
                    edtPassword.setError("Password should be minimun 8 character");
                } else {

                    if (CheckInternet.isInternetAvailable(SignupActivity.this)) {
                        SignUp();
                    } else {
                        Snack.snackbar(SignupActivity.this, getResources().getString(R.string.noInternet));
                    }
                }
            }
        });


    }

    private void init() {
        txtLogin = (TextView) findViewById(R.id.txtLogin);
        btnSignUp = (Button) findViewById(R.id.btnSignup);
        edtName = (EditText) findViewById(R.id.edtName);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
    }


    //hit the api for signup
    private void SignUp() {
        String name = edtName.getText().toString();
        String phone = edtPhone.getText().toString();
        String passwrod = edtPassword.getText().toString();


        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<SignUpApi> call = apiInterface.signupApi(name, phone, passwrod);

        final Dialog dialog = ProgressBarClass.showProgressDialog(SignupActivity.this);
        dialog.show();

        call.enqueue(new Callback<SignUpApi>() {
            @Override
            public void onResponse(Call<SignUpApi> call, Response<SignUpApi> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals(200)) {
                        Toast.makeText(SignupActivity.this, "" + response.body().getData().getOtp(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignupActivity.this, OtpActivity.class);
                        intent.putExtra("phone", response.body().getData().getPhone().toString());
                        intent.putExtra("id", response.body().getData().getId().toString());
                        startActivity(intent);
                        finish();

                    } else {
                        Toast.makeText(SignupActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SignupActivity.this, "" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SignUpApi> call, Throwable t) {
                Toast.makeText(SignupActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }
}
