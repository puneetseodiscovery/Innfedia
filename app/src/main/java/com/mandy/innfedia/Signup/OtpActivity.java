package com.mandy.innfedia.Signup;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class OtpActivity extends AppCompatActivity {

    Button button;
    EditText edtOtp;
    String phone;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        button = findViewById(R.id.btnVerify);
        edtOtp = findViewById(R.id.edtOtp);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtOtp.getText().toString())) {
                    edtOtp.setError("Invalid OTP");
                    edtOtp.requestFocus();
                } else {
                    if (CheckInternet.isInternetAvailable(OtpActivity.this)) {
                        OtpApi();
                    } else {
                        Snack.snackbar(OtpActivity.this, getResources().getString(R.string.noInternet));
                    }
                }
            }
        });

    }


    //send otp for verification
    private void OtpApi() {
        phone = getIntent().getStringExtra("phone");
        id = getIntent().getStringExtra("id");

        Log.d("hellao", phone + "\n" + id);
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<VerificationOtpApi> call = apiInterface.userVerification(edtOtp.getText().toString(), phone, id);
        final Dialog dialog = ProgressBarClass.showProgressDialog(OtpActivity.this);
        dialog.show();

        call.enqueue(new Callback<VerificationOtpApi>() {
            @Override
            public void onResponse(Call<VerificationOtpApi> call, Response<VerificationOtpApi> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals(200)) {
                        SharedToken sharedToken = new SharedToken(OtpActivity.this);
                        sharedToken.setSharedata(response.body().getData().getToken(), response.body().getData().getId().toString());

                        startActivity(new Intent(OtpActivity.this, MainActivity.class));
                        finish();
                        Toast.makeText(OtpActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(OtpActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(OtpActivity.this, "" + response.message(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<VerificationOtpApi> call, Throwable t) {
                Toast.makeText(OtpActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }
}
