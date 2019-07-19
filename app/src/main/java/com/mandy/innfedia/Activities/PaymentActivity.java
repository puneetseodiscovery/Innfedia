package com.mandy.innfedia.Activities;

import android.graphics.Color;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mandy.innfedia.R;
import com.mandy.innfedia.SpacesItemDecoration;
import com.mandy.innfedia.adapter.PaymentItemsAdapter;

public class PaymentActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView textView;
    RecyclerView recyclerView;
    TextView txtPrivicy;
    NestedScrollView nestedScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        //all ids find here
        init();


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        textView.setText("Payment");

        SpannableString ss = new SpannableString(getResources().getString(R.string.policy));

        //click for privicy
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(PaymentActivity.this, "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
            }
        };


        // click for condition
        ClickableSpan privacy = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                Toast.makeText(PaymentActivity.this, "Condiation", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
            }
        };

        ss.setSpan(clickableSpan, 45, 59, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(privacy, 64, 81, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtPrivicy.setHighlightColor(Color.TRANSPARENT);
        txtPrivicy.setText(ss, TextView.BufferType.SPANNABLE);
        txtPrivicy.setText(ss);
        txtPrivicy.setMovementMethod(LinkMovementMethod.getInstance());


        //set data into reyclerview
        setData();
    }


    private void init() {
        txtPrivicy = (TextView) findViewById(R.id.textPolicy);
        recyclerView = (RecyclerView) findViewById(R.id.reyclerPayment);
        toolbar = (Toolbar) findViewById(R.id.tooolbar);
        textView = (TextView) findViewById(R.id.textView);
        nestedScrollView = (NestedScrollView) findViewById(R.id.scrool_view);
    }


    private void setData() {
        PaymentItemsAdapter adapter = new PaymentItemsAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SpacesItemDecoration(15));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}


