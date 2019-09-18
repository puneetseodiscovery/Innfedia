package com.mandy.innfedia.myOrderList.myorderdetails;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mandy.innfedia.GetMeesageApi;
import com.mandy.innfedia.R;
import com.mandy.innfedia.commonActivity.NoInternetActivity;
import com.mandy.innfedia.controller.Controller;
import com.mandy.innfedia.utils.CheckInternet;
import com.mandy.innfedia.utils.Config;
import com.mandy.innfedia.utils.ProgressBarClass;
import com.mandy.innfedia.utils.SharedToken;
import com.mandy.innfedia.utils.Snack;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;

public class OrderDetailsActivity extends AppCompatActivity implements Controller.OrderDetails, Controller.RateNow {

    @BindView(R.id.tooolbar)
    Toolbar toolbar;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.productName)
    TextView productName;
    @BindView(R.id.productRating)
    RatingBar productRating;
    @BindView(R.id.txtDiliverData)
    TextView txtDiliverData;
    @BindView(R.id.txtPackageSigned)
    TextView txtPackageSigned;
    @BindView(R.id.ratingbar)
    RatingBar ratingbar;
    @BindView(R.id.edtComment)
    EditText edtComment;
    @BindView(R.id.btnRate)
    Button btnRate;

    String orderId, token, productId, rating;
    Controller controller;
    Dialog dialog;
    SharedToken sharedToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        ButterKnife.bind(this);
        dialog = ProgressBarClass.showProgressDialog(this);
        sharedToken = new SharedToken(this);
        controller = new Controller((Controller.OrderDetails) this, (Controller.RateNow) this);
        token = "Bearer " + sharedToken.getShared();

        if (CheckInternet.isInternetAvailable(this)) {
            dialog.show();
            controller.setOrderDetails(token, orderId);
        } else {
            startActivity(new Intent(this, NoInternetActivity.class));
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        textView.setText("My Order Details");

        orderId = getIntent().getStringExtra("OrderId");

    }

    @OnClick(R.id.btnRate)
    public void onViewClicked() {
        if (TextUtils.isEmpty(edtComment.getText().toString())) {
            edtComment.setError("Enter the your Comment");
            edtComment.requestFocus();
        } else if (TextUtils.isEmpty(rating)) {
            ratingbar.requestFocus();
            Toast.makeText(this, "Select the rating", Toast.LENGTH_LONG).show();
        } else {
            if (CheckInternet.isInternetAvailable(this)) {
                dialog.show();
                controller.setRateNow(token, edtComment.getText().toString(), rating, productId);
            } else {
                startActivity(new Intent(this, NoInternetActivity.class));
            }
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onSucess(Response<OrderDetailsApi> response) {
        dialog.dismiss();
        if (response.isSuccessful()) {
            if (response.body().getStatus() == 200) {
                setData(response.body().getData().get(0));
            } else {
                Snack.snackbar(OrderDetailsActivity.this, response.body().getMessage());
            }
        } else {
            Snack.snackbar(OrderDetailsActivity.this, response.message());
        }
    }

    private void setData(OrderDetailsApi.Datum datum) {
        Glide.with(this).load(Config.GET_PRODUCT_IMAGE + datum.getImage()).dontAnimate().dontTransform().into(imageView);
        productName.setText(datum.getProductName() + "\n" + Config.GET_RUPPESS_SYMBOL + " " + datum.getPrice());
        productId = datum.getProductId().toString();
        if (datum.getOrderReceivedBy() != null) {
            txtPackageSigned.setText("Package was handed directly to the customer Signed:" + datum.getOrderReceivedBy().toString());
        }
        txtDiliverData.setText("Delivered: " + datum.getOrderDeliveredOn().toString());
    }

    @Override
    public void onSucessRate(Response<GetMeesageApi> response) {
        dialog.dismiss();
        if (response.isSuccessful()) {
            if (response.body().getStatus() == 200) {
                Snack.snackbar(OrderDetailsActivity.this, response.body().getMessage());
            } else {
                Snack.snackbar(OrderDetailsActivity.this, response.body().getMessage());
            }

        } else {
            Snack.snackbar(OrderDetailsActivity.this, response.message());
        }
    }

    @Override
    public void error(String error) {
        dialog.dismiss();
        Snack.snackbar(OrderDetailsActivity.this, error);
    }
}
