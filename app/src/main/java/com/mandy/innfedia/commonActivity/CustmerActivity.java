package com.mandy.innfedia.commonActivity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mandy.innfedia.GetMeesageApi;
import com.mandy.innfedia.R;
import com.mandy.innfedia.controller.Controller;
import com.mandy.innfedia.utils.CheckInternet;
import com.mandy.innfedia.utils.ProgressBarClass;
import com.mandy.innfedia.utils.SharedToken;
import com.mandy.innfedia.utils.Snack;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;

public class CustmerActivity extends AppCompatActivity implements Controller.Support {

    Toolbar toolbar;
    TextView textView;
    TextView textEmail1, textPhone1, textPhone2;
    SharedToken sharedToken;
    Dialog dialog;
    Controller controller;
    @BindView(R.id.edtComment)
    EditText editText;
    @BindView(R.id.btnMail)
    Button btnMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custmer);
        ButterKnife.bind(this);
        controller = new Controller(this);
        sharedToken = new SharedToken(this);
        dialog = ProgressBarClass.showProgressDialog(this);


        init();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        textView.setText("Customer Support");

        textPhone1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + textPhone1.getText().toString()));
                startActivity(intent);
            }
        });


        textPhone2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + textPhone2.getText().toString()));
                startActivity(intent);
            }
        });


    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.tooolbar);
        textView = (TextView) findViewById(R.id.textView);
        textPhone1 = (TextView) findViewById(R.id.txtPhone1);
        textPhone2 = (TextView) findViewById(R.id.txtPhone2);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onSucess(Response<GetMeesageApi> response) {
        dialog.dismiss();

        if (response.isSuccessful()) {
            if (response.body().getStatus() == 200) {
                Snack.snackbar(CustmerActivity.this, response.body().getMessage());
            } else {
                Snack.snackbar(CustmerActivity.this, response.body().getMessage());
            }
        }
    }

    @Override
    public void error(String error) {
        dialog.dismiss();
        Snack.snackbar(CustmerActivity.this, error);
    }

    @OnClick(R.id.btnMail)
    public void onViewClicked() {
        if (TextUtils.isEmpty(editText.getText().toString())) {
            editText.setError("Empty field");
        } else {
            if (CheckInternet.isInternetAvailable(this)) {
                dialog.show();
                controller.setSupport("Bearer " + sharedToken.getShared(), editText.getText().toString());
            } else {
                startActivity(new Intent(this, NoInternetActivity.class));
            }
        }
    }
}
