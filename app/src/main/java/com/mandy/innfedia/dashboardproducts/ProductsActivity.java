package com.mandy.innfedia.dashboardproducts;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.mandy.innfedia.R;
import com.mandy.innfedia.utils.SharedToken;
import com.mandy.innfedia.utils.SpacesItemDecoration;
import com.mandy.innfedia.commonActivity.NoInternetActivity;
import com.mandy.innfedia.controller.Controller;
import com.mandy.innfedia.productList.GetProductList;
import com.mandy.innfedia.productList.adapter.ProductListAdapter;
import com.mandy.innfedia.utils.CheckInternet;
import com.mandy.innfedia.utils.ProgressBarClass;
import com.mandy.innfedia.utils.Snack;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;

public class ProductsActivity extends AppCompatActivity implements Controller.GetNewArrival, Controller.GetDisccountedItem, Controller.BestSell, Controller.GetSearchedList {
    @BindView(R.id.tooolbar)
    Toolbar toolbar;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.recyclerProduct)
    RecyclerView recyclerProduct;


    Dialog dialog;
    Controller controller;
    String type, id;
    SharedToken sharedToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        ButterKnife.bind(this);
        controller = new Controller((Controller.GetNewArrival) this, (Controller.GetDisccountedItem) this, (Controller.BestSell) this, (Controller.GetSearchedList) this);
        dialog = ProgressBarClass.showProgressDialog(this);
        sharedToken = new SharedToken(this);
        type = getIntent().getStringExtra("type");


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        if (CheckInternet.isInternetAvailable(this)) {
            dialog.show();
            if (type.equalsIgnoreCase("0")) {
                controller.setGetNewArrival();
                textView.setText("New arrivals products list");
            }

            if (type.equalsIgnoreCase("1")) {
                controller.setGetDisccountedItem();
                textView.setText("Discounted products list");
            }

            if (type.equalsIgnoreCase("2")) {
                controller.setBestSell();
                textView.setText("Best sell product list");
            }
            if (type.equalsIgnoreCase("3")) {
                id = getIntent().getStringExtra("id");
                dialog.show();
                controller.setGetSearchedList("Bearer " + sharedToken.getShared(), id);
                textView.setText("Searched product list");

            }

        } else {
            startActivity(new Intent(this, NoInternetActivity.class));
        }

    }

    private void setData(ArrayList<GetProductList.Datum> datum) {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerProduct.setLayoutManager(layoutManager);
        ProductListAdapter adapter = new ProductListAdapter(this, datum);
        recyclerProduct.setAdapter(adapter);
        recyclerProduct.addItemDecoration(new SpacesItemDecoration(5));
    }

    @Override
    public void onSuccessNew(Response<GetProductList> response) {
        dialog.dismiss();
        if (response.body().getStatus() == 200) {
            ArrayList<GetProductList.Datum> arrayList = new ArrayList<>();
            for (int i = 0; i < response.body().getData().size(); i++) {
                arrayList.add(response.body().getData().get(i));
                setData(arrayList);

            }
        } else {
            Snack.snackbar(ProductsActivity.this, response.body().getMessage());
        }
    }

    @Override
    public void onSuccessDiscont(Response<GetProductList> response) {
        dialog.dismiss();
        if (response.body().getStatus() == 200) {
            ArrayList<GetProductList.Datum> arrayList = new ArrayList<>();
            for (int i = 0; i < response.body().getData().size(); i++) {
                arrayList.add(response.body().getData().get(i));
                setData(arrayList);

            }
        } else {
            Snack.snackbar(ProductsActivity.this, response.body().getMessage());
        }
    }

    @Override
    public void onSucessBest(Response<GetProductList> response) {
        dialog.dismiss();
        if (response.body().getStatus() == 200) {
            ArrayList<GetProductList.Datum> arrayList = new ArrayList<>();
            for (int i = 0; i < response.body().getData().size(); i++) {
                arrayList.add(response.body().getData().get(i));
                setData(arrayList);

            }
        } else {
            Snack.snackbar(ProductsActivity.this, response.body().getMessage());
        }

    }

    @Override
    public void onSucessSearch(Response<GetProductList> response) {
        dialog.dismiss();
        if (response.isSuccessful()) {
            if (response.body().getStatus() == 200) {
                ArrayList<GetProductList.Datum> arrayList = new ArrayList<>();
                for (int i = 0; i < response.body().getData().size(); i++) {
                    arrayList.add(response.body().getData().get(i));
                    setData(arrayList);

                }
            } else {
                Toast.makeText(this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void error(String error) {
        dialog.dismiss();
        Snack.snackbar(ProductsActivity.this, error);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
