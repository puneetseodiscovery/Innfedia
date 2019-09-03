package com.mandy.innfedia.myOrderList.myorderdetails;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.mandy.innfedia.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderDetailsActivity extends AppCompatActivity {

    @BindView(R.id.tooolbar)
    Toolbar toolbar;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.productName)
    TextView productName;
    @BindView(R.id.productPrice)
    TextView productPrice;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        textView.setText("My Order Details");


    }

    @OnClick(R.id.btnRate)
    public void onViewClicked() {
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
