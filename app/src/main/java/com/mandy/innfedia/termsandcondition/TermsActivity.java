package com.mandy.innfedia.termsandcondition;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.widget.TextView;

import com.mandy.innfedia.controller.Controller;
import com.mandy.innfedia.R;
import com.mandy.innfedia.utils.ProgressBarClass;
import com.mandy.innfedia.utils.SharedToken;
import com.mandy.innfedia.utils.Snack;

import retrofit2.Response;

public class TermsActivity extends AppCompatActivity implements Controller.getTermsandCondition {

    Toolbar toolbar;
    TextView textView, txterms;
    Controller controller;
    SharedToken sharedToken;
    Dialog dialog;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        controller = new Controller(this);
        sharedToken = new SharedToken(this);
        controller.setGetTermsandCondition("Bearer " + sharedToken.getShared());

        dialog = ProgressBarClass.showProgressDialog(this);

        init();

        type = getIntent().getStringExtra("T");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        if (type.equalsIgnoreCase("T")) {
            textView.setText("Privacy policy");
        } else {
            textView.setText("Terms & Condition");
        }


    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.tooolbar);
        textView = (TextView) findViewById(R.id.textView);
        txterms = (TextView) findViewById(R.id.textTerms);
    }


    @Override
    public void onSucess(Response<TermsConditionApi> response) {
        dialog.dismiss();
        if (response.body().getStatus() == 200) {
            for (int i = 0; i < response.body().getData().size(); i++) {
                TermsConditionApi.Datum datum = response.body().getData().get(i);
                if (type.equalsIgnoreCase("T")) {
                    txterms.setText(Html.fromHtml(datum.getTermsAndConditions()));
                } else {
                    txterms.setText(Html.fromHtml(datum.getPrivecyPolicy()));
                }
            }

        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void error(String error) {
        dialog.dismiss();
        Snack.snackbar(TermsActivity.this, error);
    }
}
