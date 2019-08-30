package com.mandy.innfedia.home2;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.mandy.innfedia.Activities.NoInternetActivity;
import com.mandy.innfedia.Controller.Controller;
import com.mandy.innfedia.R;
import com.mandy.innfedia.SpacesItemDecoration;
import com.mandy.innfedia.Utils.CheckInternet;
import com.mandy.innfedia.Utils.ProgressBarClass;
import com.mandy.innfedia.Utils.SharedToken;
import com.mandy.innfedia.Utils.Snack;
import com.mandy.innfedia.home2.adapter.BottomWearAdapter;
import com.mandy.innfedia.home2.adapter.ExploreMoreAdapter;
import com.mandy.innfedia.home2.adapter.TopWearAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;

public class SubCategoryActivity extends AppCompatActivity implements Controller.GetSubCateogry {

    @BindView(R.id.tooolbar)
    Toolbar toolbar;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.recyclerTop)
    RecyclerView recyclerTop;
    @BindView(R.id.recyclerBottom)
    RecyclerView recyclerBottom;
    @BindView(R.id.recyclerExploreMore)
    RecyclerView recyclerExploreMore;

    String id;
    SharedToken sharedToken;
    Controller controller;
    Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);
        ButterKnife.bind(this);
        sharedToken = new SharedToken(this);
        controller = new Controller(this);
        dialog = ProgressBarClass.showProgressDialog(this);

        id = getIntent().getStringExtra("SubID");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        textView.setText("Sub Cateogry list");

        if (CheckInternet.isInternetAvailable(this)) {
            dialog.show();
            controller.setGetSubCateogry("Bearer " + sharedToken.getShared(), id);
        } else {
            startActivity(new Intent(this, NoInternetActivity.class));
        }

    }


    //set Top wear
    private void setTopData(List<GetSubCategoryApi.TopWear> topWear) {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerTop.setLayoutManager(layoutManager);
        recyclerTop.addItemDecoration(new SpacesItemDecoration(10));
        TopWearAdapter adapter = new TopWearAdapter(this, topWear);
        recyclerTop.setAdapter(adapter);
    }


    //set the top reyclerView item
    private void setBottomData(List<GetSubCategoryApi.BottomWear> bottomWears) {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerBottom.setLayoutManager(layoutManager);
        recyclerBottom.addItemDecoration(new SpacesItemDecoration(10));
        BottomWearAdapter adapter = new BottomWearAdapter(this, bottomWears);
        recyclerBottom.setAdapter(adapter);
    }

    //set the top reyclerView item
    private void setExploreData(List<GetSubCategoryApi.ExploreMore> exploreWears) {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerExploreMore.setLayoutManager(layoutManager);
        recyclerExploreMore.addItemDecoration(new SpacesItemDecoration(10));
        ExploreMoreAdapter adapter = new ExploreMoreAdapter(this, exploreWears);
        recyclerExploreMore.setAdapter(adapter);
    }


    @Override
    public void onSucess(Response<GetSubCategoryApi> response) {
        dialog.dismiss();
        if (response.body().getStatus() == 200) {
            for (int i = 0; i < response.body().getData().size(); i++) {
                setBottomData(response.body().getData().get(i).getBottomWear());
                setTopData(response.body().getData().get(i).getTopWear());
                setExploreData(response.body().getData().get(i).getExploreMore());
            }
        } else {
            Snack.snackbar(SubCategoryActivity.this, response.body().getMessage());
        }
    }

    @Override
    public void error(String error) {
        dialog.dismiss();
        Snack.snackbar(SubCategoryActivity.this, error);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
