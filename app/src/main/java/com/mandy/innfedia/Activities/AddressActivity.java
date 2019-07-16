package com.mandy.innfedia.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.mandy.innfedia.AddressAdapter;
import com.mandy.innfedia.R;
import com.mandy.innfedia.SpacesItemDecoration;

import java.util.ArrayList;

public class AddressActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    Button btnAddAddress, btnConfirm;
    AddressAdapter addressAdapter;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        // find all id
        init();

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Address");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);


        //set data into recylcer view
        setData();


        btnAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddAddressActivity.class));
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), PaymentActivity.class));
            }
        });

    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.tooolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_address);
        btnAddAddress = (Button) findViewById(R.id.add_address_btn);
        btnConfirm = (Button) findViewById(R.id.continue_btn);
    }


    //set data into recyclerView
    private void setData() {
        final ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("House no:40, Balongi,Mohali,Punjab" +
                "\n amitpanday51@gmail.com");
        arrayList.add("Mandy web design D-152, 140301 \n mohali ,pubjab");

        addressAdapter = new AddressAdapter(this, arrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(addressAdapter);
        recyclerView.addItemDecoration(new SpacesItemDecoration(15));

        addressAdapter.setOnItemClick(new AddressAdapter.OnItemClick() {
            @Override
            public void onItemClick(int position) {
                arrayList.get(position);
            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
