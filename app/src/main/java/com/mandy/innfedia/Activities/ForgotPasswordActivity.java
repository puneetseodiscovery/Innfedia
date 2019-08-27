package com.mandy.innfedia.Activities;

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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.mandy.innfedia.ApiInterface;
import com.mandy.innfedia.ApiModel.ForgotPasswordApi;
import com.mandy.innfedia.Utils.CheckInternet;
import com.mandy.innfedia.Utils.ProgressBarClass;
import com.mandy.innfedia.R;
import com.mandy.innfedia.ServiceGenerator;
import com.mandy.innfedia.Utils.Snack;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {

    Button button;
    EditText edtPhone, edtOtp, edtPassword, edtCPassword;
    CheckBox checkBox;
    RelativeLayout relativeLayout;
    TextView txtLogin;
    String phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        init();

// go the login activity
        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckInternet.isInternetAvailable(ForgotPasswordActivity.this)) {
                    if (button.getText().toString().equals("Send otp")) {
                        getOtp();
                    }
                    if (button.getText().toString().equals("Enter your otp")) {
                        confirmOtp();
                    }
                    if (button.getText().toString().equals("Set Password")) {
                        setPassword();
                    }
                } else {
                    Snack.snackbar(ForgotPasswordActivity.this, getResources().getString(R.string.noInternet));
                }
            }
        });


        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    edtPassword.setTransformationMethod(null);
                    edtCPassword.setTransformationMethod(null);
                } else {
                    edtPassword.setTransformationMethod(new PasswordTransformationMethod());
                    edtCPassword.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });

    }


    private void init() {
        button = (Button) findViewById(R.id.btnLogin);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtCPassword = (EditText) findViewById(R.id.edtCPassword);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtOtp = (EditText) findViewById(R.id.edtOTp);
        checkBox = (CheckBox) findViewById(R.id.passwordshow);
        relativeLayout = (RelativeLayout) findViewById(R.id.relative);
        txtLogin = (TextView) findViewById(R.id.txtLogin);

    }

    //apis for get the otp in phone number
    private void getOtp() {
        if (TextUtils.isEmpty(edtPhone.getText().toString())) {
            edtPhone.setError("Enter your phone number");
            edtPhone.requestFocus();
        } else if (edtPhone.getText().length() != 10) {
            edtPhone.setError("Enter a valid phone number");
            edtPhone.requestFocus();
        } else {
            ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
            Call<ForgotPasswordApi> call = apiInterface.getOtp(edtPhone.getText().toString());
            final Dialog dialog = ProgressBarClass.showProgressDialog(ForgotPasswordActivity.this);
            dialog.show();

            call.enqueue(new Callback<ForgotPasswordApi>() {
                @Override
                public void onResponse(Call<ForgotPasswordApi> call, Response<ForgotPasswordApi> response) {
                    dialog.dismiss();
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {
                            Toast.makeText(ForgotPasswordActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            button.setText("Enter your otp");
                            edtOtp.setVisibility(View.VISIBLE);
                            edtPhone.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(ForgotPasswordActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ForgotPasswordApi> call, Throwable t) {
                    dialog.dismiss();
                    Toast.makeText(ForgotPasswordActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    //send otp for confirmation
    private void confirmOtp() {
        Toast.makeText(this, "" + edtPhone.getText().toString(), Toast.LENGTH_SHORT).show();
        if (TextUtils.isEmpty(edtOtp.getText().toString())) {
            edtOtp.setError("Enter your OTP");
            edtOtp.requestFocus();
        } else {
            ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
            Call<ForgotPasswordApi> call = apiInterface.sendOtp(edtOtp.getText().toString(), edtPhone.getText().toString());
            final Dialog dialog = ProgressBarClass.showProgressDialog(ForgotPasswordActivity.this);
            dialog.show();

            call.enqueue(new Callback<ForgotPasswordApi>() {
                @Override
                public void onResponse(Call<ForgotPasswordApi> call, Response<ForgotPasswordApi> response) {
                    dialog.dismiss();
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {
                            button.setText("Set Password");
                            edtOtp.setVisibility(View.GONE);
                            relativeLayout.setVisibility(View.VISIBLE);
                            edtCPassword.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(ForgotPasswordActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ForgotPasswordApi> call, Throwable t) {
                    dialog.dismiss();
                    Toast.makeText(ForgotPasswordActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    private void setPassword() {

        if (TextUtils.isEmpty(edtPassword.getText().toString())) {
            edtPassword.setError("Enter your password");
            edtPassword.requestFocus();
        } else if (TextUtils.isEmpty(edtCPassword.getText().toString())) {
            edtCPassword.setError("Enter your confirm password");
            edtCPassword.requestFocus();
        } else if (edtPassword.getText().length() < 8) {
            edtPassword.setError("Password should be minmum 8 character");
        } else if (!edtCPassword.getText().toString().equals(edtPassword.getText().toString())) {
            edtCPassword.setError("Password does not match");
            edtCPassword.requestFocus();
        } else {
            ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
            Call<ForgotPasswordApi> call = apiInterface.resetPassword(edtPassword.getText().toString(), edtPhone.getText().toString());
            final Dialog dialog = ProgressBarClass.showProgressDialog(ForgotPasswordActivity.this);
            dialog.show();

            call.enqueue(new Callback<ForgotPasswordApi>() {
                @Override
                public void onResponse(Call<ForgotPasswordApi> call, Response<ForgotPasswordApi> response) {
                    dialog.dismiss();
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(ForgotPasswordActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ForgotPasswordApi> call, Throwable t) {
                    dialog.dismiss();
                }
            });
        }
    }
}
