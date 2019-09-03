package com.mandy.innfedia.commonActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import android.support.v7.app.AppCompatActivity;


import com.mandy.innfedia.R;

public class CustmerActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView textView;
    TextView textEmail1, textPhone1, textPhone2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custmer);

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
}
