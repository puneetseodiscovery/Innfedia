package com.mandy.innfedia.addressActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mandy.innfedia.commonActivity.NoInternetActivity;
import com.mandy.innfedia.addressActivity.adapter.AddressAdapter;
import com.mandy.innfedia.addressActivity.addAddress.ADDAddressActivity;
import com.mandy.innfedia.controller.Controller;
import com.mandy.innfedia.payment.PaymentActivity;
import com.mandy.innfedia.R;
import com.mandy.innfedia.utils.CheckInternet;
import com.mandy.innfedia.utils.ProgressBarClass;
import com.mandy.innfedia.utils.SharedToken;
import com.mandy.innfedia.utils.Snack;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;

public class AddressActivity extends AppCompatActivity implements Controller.GetAddressList {

    @BindView(R.id.tooolbar)
    Toolbar toolbar;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.recycler_address)
    RecyclerView recyclerAddress;
    @BindView(R.id.add_address_btn)
    Button addAddressBtn;
    @BindView(R.id.continue_btn)
    Button continueBtn;
    @BindView(R.id.linear5)
    LinearLayout linear5;
    Controller controller;
    SharedToken sharedToken;
    Dialog dialog;
    String address, id;
    String size = "", color = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        ButterKnife.bind(this);
        controller = new Controller(this);
        sharedToken = new SharedToken(this);
        dialog = ProgressBarClass.showProgressDialog(this);
        dialog.show();

        id = getIntent().getStringExtra("Cid");
        if (!id.equals("0")) {
            size = getIntent().getStringExtra("size");
            color = getIntent().getStringExtra("color");
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        textView.setText("Address list");

        if (CheckInternet.isInternetAvailable(this)) {
            controller.setGetAddressList("Bearer " + sharedToken.getShared());
        } else {
            startActivity(new Intent(this, NoInternetActivity.class));
        }

    }

    @OnClick({R.id.add_address_btn, R.id.continue_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_address_btn:
                if (id.equals("0")) {
                    Intent intent = new Intent(this, ADDAddressActivity.class);
                    intent.putExtra("Cid", id);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(this, ADDAddressActivity.class);
                    intent.putExtra("Cid", id);
                    intent.putExtra("size", size);
                    intent.putExtra("color", color);
                    startActivity(intent);
                }
                break;
            case R.id.continue_btn:
                if (id.equals("0")) {
                    Intent intent1 = new Intent(this, PaymentActivity.class);
                    intent1.putExtra("Cid", id);
                    intent1.putExtra("Address", address);
                    startActivity(intent1);
                } else {
                    Intent intent1 = new Intent(this, PaymentActivity.class);
                    intent1.putExtra("Cid", id);
                    intent1.putExtra("Address", address);
                    intent1.putExtra("size", size);
                    intent1.putExtra("color", color);
                    startActivity(intent1);
                }
                break;
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onSucess(Response<GetAddressApi> response) {
        dialog.dismiss();
        if (response.body().getStatus() == 200) {
            ArrayList<GetAddressApi.Datum> arrayList = new ArrayList<>();
            for (int i = 0; i < response.body().getData().size(); i++) {
                arrayList.add(response.body().getData().get(i));
                setData(arrayList);
            }
        } else {
            Snack.snackbar(AddressActivity.this, response.body().getMessage());
        }
    }

    @Override
    public void error(String error) {
        dialog.dismiss();
    }


    //set data into recyclerView
    private void setData(final ArrayList<GetAddressApi.Datum> arrayList) {
        AddressAdapter addressAdapter = new AddressAdapter(this, arrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerAddress.setLayoutManager(linearLayoutManager);
        recyclerAddress.setAdapter(addressAdapter);

        addressAdapter.setOnItemClick(new AddressAdapter.OnItemClick() {
            @Override
            public void onItemClick(int position, String addres) {
                address = addres;
            }

        });

    }
}
