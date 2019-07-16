package com.mandy.innfedia.Activities;

import android.content.Intent;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.mandy.innfedia.R;
import com.mandy.innfedia.SpacesItemDecoration;
import com.mandy.innfedia.adapter.CartAdapter;
import com.mandy.innfedia.adapter.CartMoreAdapter;

public class ActivityCart extends AppCompatActivity {

    NestedScrollView scrollView;
    Toolbar toolbar;
    RecyclerView recyclerViewCart, recyclerViewMore, recyclerViewTop;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        init();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Cart");

        //set the cart data
        setCart();

        //see more
        setMore();

        //set top
        setTop();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddressActivity.class));
            }
        });


        ((NestedScrollView) findViewById(R.id.scrool_view)).post(new Runnable() {
            public void run() {
                ((NestedScrollView) findViewById(R.id.scrool_view)).fullScroll(View.FOCUS_UP);
            }
        });
    }

    private void init() {
        scrollView = (NestedScrollView) findViewById(R.id.scrool_view);
        button = (Button) findViewById(R.id.btnProced);
        recyclerViewCart = (RecyclerView) findViewById(R.id.recyclerCart);
        recyclerViewMore = (RecyclerView) findViewById(R.id.recyclerMore);
        recyclerViewTop = (RecyclerView) findViewById(R.id.recyclerTopPickup);

        toolbar = (Toolbar) findViewById(R.id.tooolbar);
    }


    private void setCart() {
        CartAdapter adapter = new CartAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewCart.setLayoutManager(linearLayoutManager);
        recyclerViewCart.setAdapter(adapter);
        recyclerViewCart.addItemDecoration(new SpacesItemDecoration(15));
    }


    private void setMore() {
        CartMoreAdapter adapter = new CartMoreAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewMore.setLayoutManager(linearLayoutManager);
        recyclerViewMore.setAdapter(adapter);
        recyclerViewMore.addItemDecoration(new SpacesItemDecoration(15));
    }


    private void setTop() {
        CartMoreAdapter adapter = new CartMoreAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewTop.setLayoutManager(linearLayoutManager);
        recyclerViewTop.setAdapter(adapter);
        recyclerViewTop.addItemDecoration(new SpacesItemDecoration(15));
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
