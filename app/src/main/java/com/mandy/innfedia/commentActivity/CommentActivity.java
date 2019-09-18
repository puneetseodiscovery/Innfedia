package com.mandy.innfedia.commentActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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

public class CommentActivity extends AppCompatActivity implements Controller.CommentsList {

    @BindView(R.id.tooolbar)
    Toolbar toolbar;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    CommentsAdapter commentsAdapter;

    Dialog dialog;
    Controller controller;
    SharedToken sharedToken;
    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ButterKnife.bind(this);
        dialog = ProgressBarClass.showProgressDialog(this);
        controller = new Controller(this);
        sharedToken = new SharedToken(this);

        id = getIntent().getStringExtra("pId");

        if (CheckInternet.isInternetAvailable(this)) {
            controller.setCommentsList("Bearer " + sharedToken.getShared(), id);
        } else {
            startActivity(new Intent(this, NoInternetActivity.class));
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        textView.setText("Comments");


    }

    private void setData(ArrayList<CommentsApi.Datum> arrayList) {
        commentsAdapter = new CommentsAdapter(this, arrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(commentsAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onSucess(Response<CommentsApi> response) {
        dialog.dismiss();
        if (response.isSuccessful()) {
            ArrayList<CommentsApi.Datum> arrayList = new ArrayList<>();
            if (response.body().getStatus() == 200) {
                for (int i = 0; i < response.body().getData().size(); i++) {
                    arrayList.add(response.body().getData().get(i));
                    setData(arrayList);
                }
            } else {
                Snack.snackbar(CommentActivity.this, response.body().getMessage());
            }
        }
    }

    @Override
    public void error(String error) {
        dialog.dismiss();
        Snack.snackbar(CommentActivity.this, error);
    }
}
