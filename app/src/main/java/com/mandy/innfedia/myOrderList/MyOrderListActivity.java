package com.mandy.innfedia.myOrderList;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mandy.innfedia.R;
import com.mandy.innfedia.commonActivity.NoInternetActivity;
import com.mandy.innfedia.controller.Controller;
import com.mandy.innfedia.utils.CheckInternet;
import com.mandy.innfedia.utils.ProgressBarClass;
import com.mandy.innfedia.utils.SharedToken;
import com.mandy.innfedia.utils.Snack;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;

public class MyOrderListActivity extends AppCompatActivity implements Controller.OrderList {

    @BindView(R.id.tooolbar)
    Toolbar toolbar;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.txtTime)
    TextView txtTime;
    @BindView(R.id.txtFilter)
    TextView txtFilter;
    @BindView(R.id.rel)
    RelativeLayout rel;
    @BindView(R.id.recyclerOrder)
    RecyclerView recyclerOrder;

    SharedToken sharedToken;
    Controller controller;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order_list);
        ButterKnife.bind(this);
        controller = new Controller(this);
        dialog = ProgressBarClass.showProgressDialog(this);
        sharedToken = new SharedToken(this);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        textView.setText("My Order List");

        if (CheckInternet.isInternetAvailable(this)) {
            dialog.show();
            controller.setOrderList("Bearer " + sharedToken.getShared());
        } else {
            startActivity(new Intent(this, NoInternetActivity.class));
        }
    }


    private void setRecyclerView(ArrayList<OrderListApi.Datum> arrayList) {
        YourOrderAdapter adapter = new YourOrderAdapter(this, arrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerOrder.setLayoutManager(linearLayoutManager);
        recyclerOrder.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onSucess(Response<OrderListApi> response) {
        dialog.dismiss();
        if (response.isSuccessful()) {
            ArrayList<OrderListApi.Datum> arrayList = new ArrayList<>();
            if (response.body().getStatus() == 200) {
                for (int i = 0; i < response.body().getData().size(); i++) {
                    arrayList.add(response.body().getData().get(i));
                }
                   setRecyclerView(arrayList);

            } else {
                Snack.snackbar(MyOrderListActivity.this, response.body().getMessage());
            }

        } else {
            Snack.snackbar(MyOrderListActivity.this, response.message());
        }
    }

    @Override
    public void error(String error) {
        dialog.dismiss();
        Snack.snackbar(MyOrderListActivity.this, error);
    }
}
