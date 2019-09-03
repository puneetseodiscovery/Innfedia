package com.mandy.innfedia.productList;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.mandy.innfedia.commonActivity.NoInternetActivity;
import com.mandy.innfedia.controller.Controller;
import com.mandy.innfedia.R;
import com.mandy.innfedia.SpacesItemDecoration;
import com.mandy.innfedia.utils.CheckInternet;
import com.mandy.innfedia.utils.ProgressBarClass;
import com.mandy.innfedia.utils.SharedToken;
import com.mandy.innfedia.utils.Snack;
import com.mandy.innfedia.productList.adapter.ProductListAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;

public class ProductListActivity extends AppCompatActivity implements Controller.GetItemsList {

    @BindView(R.id.tooolbar)
    Toolbar toolbar;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.recyclerProduct)
    RecyclerView recyclerProduct;
    SharedToken sharedToken;
    Dialog dialog;
    Controller controller;
    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        ButterKnife.bind(this);
        dialog = ProgressBarClass.showProgressDialog(this);
        sharedToken = new SharedToken(this);
        controller = new Controller(this);
        id = getIntent().getStringExtra("SubId");


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        textView.setText("Product list");


        if (CheckInternet.isInternetAvailable(this)) {
            dialog.show();
            controller.setGetItemsList("Bearer " + sharedToken.getShared(), id);
        } else {
            startActivity(new Intent(this, NoInternetActivity.class));
        }


    }

    @Override
    public void onSuccess(Response<GetProductList> response) {
        dialog.dismiss();
        if (response.body().getStatus().equals(200)) {
            ArrayList<GetProductList.Datum> arrayList = new ArrayList<>();
            for (int i = 0; i < response.body().getData().size(); i++) {
                arrayList.add(response.body().getData().get(i));

                setData(arrayList);

            }

        } else {
            Snack.snackbar(ProductListActivity.this, response.body().getMessage());
        }
    }

    @Override
    public void error(String error) {
        dialog.dismiss();
        Snack.snackbar(ProductListActivity.this, error);
    }

    private void setData(ArrayList<GetProductList.Datum> datum) {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerProduct.setLayoutManager(layoutManager);
        ProductListAdapter adapter = new ProductListAdapter(this, datum,id);
        recyclerProduct.setAdapter(adapter);
        recyclerProduct.addItemDecoration(new SpacesItemDecoration(10));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
