package com.mandy.innfedia.productDetails;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.mandy.innfedia.commonActivity.NoInternetActivity;
import com.mandy.innfedia.controller.Controller;
import com.mandy.innfedia.productDetails.adapter.ColorAdapter;
import com.mandy.innfedia.productDetails.adapter.SeeRelatedItemAdapter;
import com.mandy.innfedia.productDetails.adapter.SizeAdapter;
import com.mandy.innfedia.productDetails.adapter.ViewPagerProductImageAdapter;
import com.mandy.innfedia.R;
import com.mandy.innfedia.SpacesItemDecoration;
import com.mandy.innfedia.utils.CheckInternet;
import com.mandy.innfedia.utils.Config;
import com.mandy.innfedia.utils.ProgressBarClass;
import com.mandy.innfedia.utils.SharedToken;
import com.mandy.innfedia.utils.Snack;
import com.mandy.innfedia.productList.GetProductList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.relex.circleindicator.CircleIndicator;
import retrofit2.Response;

public class ProductDetailsActivity extends AppCompatActivity implements Controller.AddToCart, Controller.GetRelatedItems, Controller.GetProductDetails {

    @BindView(R.id.tooolbar)
    Toolbar toolbar;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.txtproductName)
    TextView txtproductName;
    @BindView(R.id.txtSubProductName)
    TextView txtSubProductName;
    @BindView(R.id.ratingbar)
    RatingBar ratingbar;
    @BindView(R.id.txtratingNumber)
    TextView txtratingNumber;
    @BindView(R.id.linear)
    LinearLayout linear;
    @BindView(R.id.circleindecator)
    CircleIndicator circleindecator;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.txtMRP)
    TextView txtMRP;
    @BindView(R.id.txtPrice)
    TextView txtPrice;
    @BindView(R.id.txtColor)
    TextView txtColor;
    @BindView(R.id.recyclerColor)
    RecyclerView recyclerColor;
    @BindView(R.id.txtSize)
    TextView txtSize;
    @BindView(R.id.recyclerSize)
    RecyclerView recyclerSize;
    @BindView(R.id.perview_description)
    ReadMoreTextView perviewDescription;
    @BindView(R.id.txtFeture)
    ReadMoreTextView txtFeture;
    @BindView(R.id.btnAddCart)
    Button btnAddCart;
    @BindView(R.id.btnBuynow)
    Button btnBuynow;
    @BindView(R.id.recyclerRelated)
    RecyclerView recyclerRelated;

    Controller controller;
    SharedToken sharedToken;
    Dialog dialog;
    String token, id, catId, sizeId, colorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        ButterKnife.bind(this);
        sharedToken = new SharedToken(this);
        dialog = ProgressBarClass.showProgressDialog(this);
        controller = new Controller((Controller.GetProductDetails) this, (Controller.GetRelatedItems) this, (Controller.AddToCart) this);

        token = "Bearer " + sharedToken.getShared();
        id = getIntent().getStringExtra("subId");
        catId = getIntent().getStringExtra("Cid");

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        textView.setText("Product Details");

        if (CheckInternet.isInternetAvailable(this)) {
            dialog.show();
            controller.setGetProductDetails(token, id);
            controller.setGetRelatedItems(token, catId);
        } else {
            startActivity(new Intent(this, NoInternetActivity.class));
        }


    }

    @OnClick({R.id.btnAddCart, R.id.btnBuynow})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnAddCart:
                if (CheckInternet.isInternetAvailable(this)) {
                    dialog.show();
                    controller.setAddToCart(token, id, colorId, sizeId);
                } else {
                    startActivity(new Intent(ProductDetailsActivity.this, NoInternetActivity.class));
                }
                break;
            case R.id.btnBuynow:

                break;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void addtocart(Response<GetAddToCart> response) {
        dialog.dismiss();
        if (response.body().getStatus() == 200) {
            Snack.snackbar(ProductDetailsActivity.this, response.body().getMessage());
        } else {
            Snack.snackbar(ProductDetailsActivity.this, response.body().getMessage());
        }
    }

    @Override
    public void errorCart(String error) {
        dialog.dismiss();
        Snack.snackbar(ProductDetailsActivity.this, error);
    }

    @Override
    public void onSucess(Response<GetProductDetailsApi> response) {
        dialog.dismiss();
        if (response.body().getStatus() == 200) {
            for (int i = 0; i < response.body().getData().size(); i++) {
                GetProductDetailsApi.Datum datum = response.body().getData().get(i);

                txtproductName.setText(datum.getTitle());
                txtSubProductName.setText(datum.getSlug());
                txtratingNumber.setText(datum.getTotalUsers().toString());
                perviewDescription.setText(datum.getDescription());
                txtFeture.setText(datum.getFeatures());
                txtMRP.setText(Config.GET_RUPPESS_SYMBOL + datum.getPrice());
                ratingbar.setRating(Float.parseFloat(datum.getAvgRating()));
                txtPrice.setText(Config.GET_RUPPESS_SYMBOL + datum.getSpecialPrice());

                setViewPager(response.body().getData().get(i).getProductImages());
                setSize(response.body().getData().get(i).getSizes());
                setColor(response.body().getData().get(i).getColors());
            }


        } else {
            Snack.snackbar(ProductDetailsActivity.this, response.body().getMessage());
        }
    }

    @Override
    public void error(String error) {
        dialog.dismiss();
        Snack.snackbar(ProductDetailsActivity.this, error);
    }

    @Override
    public void onSuccessRelated(Response<GetProductList> response) {
        if (response.body().getStatus() == 200) {
            ArrayList<GetProductList.Datum> arrayList = new ArrayList<>();
            for (int i = 0; i < response.body().getData().size(); i++) {
                arrayList.add(response.body().getData().get(i));
                setRelatedData(arrayList);
            }
        } else {
            Snack.snackbar(ProductDetailsActivity.this, response.body().getMessage());
        }
    }

    @Override
    public void errorRelated(String error) {
        Snack.snackbar(ProductDetailsActivity.this, error);
    }


    // set view pager
    private void setViewPager(List<GetProductDetailsApi.ProductImage> arrayList) {
        PagerAdapter adapter = new ViewPagerProductImageAdapter(this, arrayList);
        viewPager.setAdapter(adapter);

        circleindecator.setViewPager(viewPager);

        adapter.registerDataSetObserver(circleindecator.getDataSetObserver());

    }


    //set the size
    private void setSize(final List<GetProductDetailsApi.Size> arrayList) {
        SizeAdapter adapter = new SizeAdapter(this, arrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerSize.setLayoutManager(linearLayoutManager);
        recyclerSize.setAdapter(adapter);
        recyclerSize.addItemDecoration(new SpacesItemDecoration(10));
        adapter.setOnItemClick(new SizeAdapter.OnItemClick() {
            @Override
            public void onItemClick(int position, int id) {
                txtSize.setText(arrayList.get(position).getSize());
                sizeId = String.valueOf(id);
            }
        });
    }


    //set the color
    private void setColor(final List<GetProductDetailsApi.Color> arrayList) {
        ColorAdapter colorAdapter = new ColorAdapter(this, arrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerColor.setLayoutManager(linearLayoutManager);
        recyclerColor.setAdapter(colorAdapter);
        recyclerColor.addItemDecoration(new SpacesItemDecoration(10));

        colorAdapter.setOnItemClick(new ColorAdapter.OnItemClick() {
            @Override
            public void onItemClick(int position, int id) {
                txtColor.setText(arrayList.get(position).getSize());
                colorId = String.valueOf(id);
            }
        });

    }

    //set related data
    private void setRelatedData(ArrayList<GetProductList.Datum> arrayList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerRelated.setLayoutManager(linearLayoutManager);
        SeeRelatedItemAdapter adapter = new SeeRelatedItemAdapter(this, arrayList);
        recyclerRelated.setAdapter(adapter);
        recyclerRelated.addItemDecoration(new SpacesItemDecoration(10));
        adapter.setOnClick(new onClick() {
            @Override
            public void GetId(String id) {
                if (CheckInternet.isInternetAvailable(ProductDetailsActivity.this)) {
                    dialog.show();
                    controller.setGetProductDetails(token, id);
                } else {
                    startActivity(new Intent(ProductDetailsActivity.this, NoInternetActivity.class));
                }
            }
        });
    }


}
