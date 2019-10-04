package com.mandy.innfedia.searchActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.mandy.innfedia.R;
import com.mandy.innfedia.commonActivity.NoInternetActivity;
import com.mandy.innfedia.controller.Controller;
import com.mandy.innfedia.dashboardproducts.ProductsActivity;
import com.mandy.innfedia.utils.CheckInternet;
import com.mandy.innfedia.utils.ProgressBarClass;
import com.mandy.innfedia.utils.SharedToken;
import com.mandy.innfedia.utils.Snack;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements Controller.GetSearchList {

    @BindView(R.id.tooolbar)
    Toolbar toolbar;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.autoComplete)
    AutoCompleteTextView autoComplete;
    ArrayList<SearchListApi.Datum> arrayList = new ArrayList<>();
    ArrayList<String> arrayString = new ArrayList<>();
    Controller controller;
    SharedToken sharedToken;
    Dialog dialog;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        dialog = ProgressBarClass.showProgressDialog(this);
        sharedToken = new SharedToken(this);
        controller = new Controller(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        textView.setText("Search items");

        if (CheckInternet.isInternetAvailable(this)) {
            dialog.show();
            controller.setGetSearchList("Bearer " + sharedToken.getShared());
        } else {
            startActivity(new Intent(this, NoInternetActivity.class));
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onSucess(Response<SearchListApi> response) {
        dialog.dismiss();
        if (response.isSuccessful()) {
            arrayString.clear();
            arrayList.clear();
            if (response.body().getStatus() == 200) {
                for (int i = 0; i < response.body().getData().size(); i++) {
                    arrayList.add(response.body().getData().get(i));
                    arrayString.add(response.body().getData().get(i).getName().toString());
                }

                setData();

            } else {
                Snack.snackbar(SearchActivity.this, response.body().getMessage());
            }
        }
    }

    @Override
    public void error(String error) {
        dialog.dismiss();
        Snack.snackbar(SearchActivity.this, error);
    }


    private void setData() {
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, arrayString);
        autoComplete.setAdapter(adapter);

        Log.d("++++++++arr", arrayString.toString());

        autoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String abc = adapter.getItem(position).toString();
                for (int i = 0; i < arrayList.size(); i++) {
                    if (abc.equalsIgnoreCase(arrayList.get(i).getName())) {
                        pos = arrayList.get(i).getId();
                    }
                }

                Intent intent = new Intent(SearchActivity.this, ProductsActivity.class);
                intent.putExtra("type", "3");
                intent.putExtra("id", "" + pos);
                startActivity(intent);
            }
        });
    }
}
