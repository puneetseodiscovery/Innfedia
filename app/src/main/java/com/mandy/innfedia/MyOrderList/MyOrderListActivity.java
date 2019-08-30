package com.mandy.innfedia.MyOrderList;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mandy.innfedia.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyOrderListActivity extends AppCompatActivity {

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
    ArrayList<String> arrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order_list);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        textView.setText("My Order List");


        arrayList.add("Dispatch");
        arrayList.add("Recived");
        arrayList.add("Recived");
        arrayList.add("Recived");
        arrayList.add("Recived");

        setRecyclerView();
    }


    private void setRecyclerView() {
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
}
