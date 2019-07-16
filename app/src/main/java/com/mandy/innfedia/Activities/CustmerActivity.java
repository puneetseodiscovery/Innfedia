package com.mandy.innfedia.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mandy.innfedia.R;

public class CustmerActivity extends AppCompatActivity {

    TextView textEmail1, textEmail2, textPhone1, textPhone2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custmer);

        init();

        textPhone1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+textPhone1.getText().toString()));
                startActivity(intent);
            }
        });


        textPhone2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+textPhone2.getText().toString()));
                startActivity(intent);
            }
        });

    }

    private void init() {
        textEmail1 = (TextView) findViewById(R.id.txtEmail1);
        textEmail2 = (TextView) findViewById(R.id.txtEmail2);
        textPhone1 = (TextView) findViewById(R.id.txtPhone1);
        textPhone2 = (TextView) findViewById(R.id.txtPhone2);
    }
}
