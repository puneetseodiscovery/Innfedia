package com.mandy.innfedia.Activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mandy.innfedia.R;
import com.mandy.innfedia.fragment.AddressFragment;

public class AddAddressActivity extends AppCompatActivity {

    EditText edtName, edtPhone, edtPostcode, edtCity, edtState, edtFlat, edtNear;
    Button button;
    Toolbar toolbar;
    TextView textView;
    FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address_activity);

        //find id here's
        init();


        manager = getSupportFragmentManager();

        init();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        textView.setText("Add Address");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.framelayout, new AddressFragment());
                transaction.commit();
            }
        });

    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.tooolbar);
        textView = (TextView) findViewById(R.id.textView);
        edtName = (EditText) findViewById(R.id.edtName);
        edtPhone = (EditText) findViewById(R.id.edtMobile);
        edtPostcode = (EditText) findViewById(R.id.edtPostcode);
        edtCity = (EditText) findViewById(R.id.edtTown);
        edtState = (EditText) findViewById(R.id.edtState);
        edtFlat = (EditText) findViewById(R.id.edtFlat);
        edtNear = (EditText) findViewById(R.id.edtNear);
        button = (Button) findViewById(R.id.btnAdd);

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
