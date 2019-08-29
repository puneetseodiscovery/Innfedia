package com.mandy.innfedia.Login;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mandy.innfedia.Signup.SignupActivity;
import com.mandy.innfedia.ForgotPassword.ForgotPasswordActivity;
import com.mandy.innfedia.Retrofit.ApiInterface;
import com.mandy.innfedia.MainActivity;
import com.mandy.innfedia.Utils.CheckInternet;
import com.mandy.innfedia.Utils.ProgressBarClass;
import com.mandy.innfedia.R;
import com.mandy.innfedia.Retrofit.ServiceGenerator;
import com.mandy.innfedia.Utils.SharedToken;
import com.mandy.innfedia.Utils.Snack;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText edtPhone, edtPassword;
    Button btnLogin;
    TextView txtSignup;
    CheckBox checkBox;
    TextView textForgot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //here find all id
        init();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(edtPhone.getText().toString())) {
                    edtPhone.requestFocus();
                    edtPhone.setError(getResources().getString(R.string.invalid));
                } else if (edtPhone.getText().length() != 10) {
                    edtPhone.requestFocus();
                    edtPhone.setError("Enter 10 digit phone number");
                } else if (TextUtils.isEmpty(edtPassword.getText().toString())) {
                    edtPassword.requestFocus();
                    edtPassword.setError(getResources().getString(R.string.invalid));
                } else {
                    if (CheckInternet.isInternetAvailable(LoginActivity.this)) {
                        LoginAPi();
                    } else {
                        Snack.snackbar(LoginActivity.this, getResources().getString(R.string.noInternet));
                    }
                }


            }
        });


        //forgot password here
        textForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });


        txtSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignupActivity.class));
                finish();
            }
        });


        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    edtPassword.setTransformationMethod(null);
                } else {
                    edtPassword.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });
    }


    // method for get all id's
    private void init() {
        btnLogin = (Button) findViewById(R.id.btnLogin);
        txtSignup = (TextView) findViewById(R.id.txtSignup);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        checkBox = (CheckBox) findViewById(R.id.passwordshow);
        textForgot = (TextView) findViewById(R.id.txtForgot);
    }

    //login api
    private void LoginAPi() {
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<LoginApi> call = apiInterface.loginApi(edtPhone.getText().toString(), edtPassword.getText().toString());

        final Dialog dialog = ProgressBarClass.showProgressDialog(LoginActivity.this);

        dialog.show();

        call.enqueue(new Callback<LoginApi>() {
            @Override
            public void onResponse(Call<LoginApi> call, Response<LoginApi> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals(200)) {
                        Toast.makeText(LoginActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        SharedToken sharedToken = new SharedToken(getApplicationContext());
                        sharedToken.setSharedata(response.body().getData().getToken(), response.body().getData().getId().toString());

                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginApi> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }
}
