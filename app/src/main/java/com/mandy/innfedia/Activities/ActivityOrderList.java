package com.mandy.innfedia.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.mandy.innfedia.R;
import com.mandy.innfedia.SpacesItemDecoration;
import com.mandy.innfedia.adapter.YourOrderAdapter;

import java.util.ArrayList;

public class ActivityOrderList extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    ArrayList<String> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        init();

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Order list");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        arrayList.add("Dispatch");
        arrayList.add("Recived");
        arrayList.add("Recived");
        arrayList.add("Recived");
        arrayList.add("Recived");

        setRecyclerView();
    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.tooolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerOrder);
    }


    private void setRecyclerView() {
        YourOrderAdapter adapter = new YourOrderAdapter(this, arrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SpacesItemDecoration(10));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
