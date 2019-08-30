package com.mandy.innfedia.AddressActivity;

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

import com.mandy.innfedia.Activities.NoInternetActivity;
import com.mandy.innfedia.AddressActivity.adapter.AddressAdapter;
import com.mandy.innfedia.AddressActivity.addAddress.ADDAddressActivity;
import com.mandy.innfedia.Controller.Controller;
import com.mandy.innfedia.Payment.PaymentActivity;
import com.mandy.innfedia.R;
import com.mandy.innfedia.Utils.CheckInternet;
import com.mandy.innfedia.Utils.Config;
import com.mandy.innfedia.Utils.ProgressBarClass;
import com.mandy.innfedia.Utils.SharedToken;
import com.mandy.innfedia.Utils.Snack;

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
                Intent intent = new Intent(this, ADDAddressActivity.class);
                intent.putExtra("Cid", id);
                startActivity(intent);
                break;
            case R.id.continue_btn:
                Intent intent1 = new Intent(this, PaymentActivity.class);
                intent1.putExtra("Cid", id);
                intent1.putExtra("Address", address);
                startActivity(intent1);
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
