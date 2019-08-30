package com.mandy.innfedia.MyCart;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mandy.innfedia.Activities.NoInternetActivity;
import com.mandy.innfedia.AddressActivity.AddressActivity;
import com.mandy.innfedia.Controller.Controller;
import com.mandy.innfedia.GetMeesageApi;
import com.mandy.innfedia.MyCart.ExploreMore.CartMoreAdapter;
import com.mandy.innfedia.MyCart.ExploreMore.GetExploreMoreData;
import com.mandy.innfedia.MyCart.ExploreMore.onclick;
import com.mandy.innfedia.MyCart.topitems.Click;
import com.mandy.innfedia.MyCart.topitems.GetTopDataApi;
import com.mandy.innfedia.MyCart.topitems.TopItemsAdapter;
import com.mandy.innfedia.ProductDetils.GetAddToCart;
import com.mandy.innfedia.R;
import com.mandy.innfedia.SpacesItemDecoration;
import com.mandy.innfedia.Utils.CheckInternet;
import com.mandy.innfedia.Utils.Config;
import com.mandy.innfedia.Utils.ProgressBarClass;
import com.mandy.innfedia.Utils.SharedToken;
import com.mandy.innfedia.Utils.Snack;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;

public class MyCartActivity extends AppCompatActivity implements Controller.MyCartList, Controller.DeleteItems, Controller.IncreseItemQuantity, Controller.TopItemsList, Controller.ExploreMore, Controller.AddToCart {

    @BindView(R.id.tooolbar)
    Toolbar toolbar;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.txtItems)
    TextView txtItems;
    @BindView(R.id.txtPrice)
    TextView txtPrice;
    @BindView(R.id.btnProced)
    Button btnProced;
    @BindView(R.id.recyclerCart)
    RecyclerView recyclerCart;
    @BindView(R.id.recyclerMore)
    RecyclerView recyclerMore;
    @BindView(R.id.recyclerTopPickup)
    RecyclerView recyclerTopPickup;
    @BindView(R.id.btnProced2)
    Button btnProced2;
    @BindView(R.id.scrool_view)
    NestedScrollView scroolView;

    SharedToken sharedToken;
    Dialog dialog;
    Controller controller;
    String token;
    FragmentManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);
        ButterKnife.bind(this);
        dialog = ProgressBarClass.showProgressDialog(this);
        controller = new Controller((Controller.MyCartList) this, (Controller.DeleteItems) this, (Controller.IncreseItemQuantity) this, (Controller.TopItemsList) this, (Controller.ExploreMore) this, (Controller.AddToCart) this);
        sharedToken = new SharedToken(this);
        token = "Bearer " + sharedToken.getShared();
        manager = getSupportFragmentManager();


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        textView.setText("My Cart");

        if (CheckInternet.isInternetAvailable(this)) {
            controller.setMyCartList(token);
            controller.setExploreMore(token);
            controller.setTopItemsList(token);
        } else {
            startActivity(new Intent(MyCartActivity.this, NoInternetActivity.class));
        }


        ((NestedScrollView) findViewById(R.id.scrool_view)).post(new Runnable() {
            public void run() {
                ((NestedScrollView) findViewById(R.id.scrool_view)).fullScroll(View.FOCUS_UP);
            }
        });


    }

    @OnClick({R.id.btnProced, R.id.btnProced2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnProced:
                sendData();
                break;
            case R.id.btnProced2:
                sendData();
                break;
        }
    }


    private void sendData() {
        Intent intent = new Intent(this, AddressActivity.class);
        intent.putExtra("Cid", "0");
        startActivity(intent);
    }


    //set more data
    private void setMore(ArrayList<GetExploreMoreData.Datum> arrayList) {
        CartMoreAdapter adapter = new CartMoreAdapter(this, arrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerMore.setLayoutManager(linearLayoutManager);
        recyclerMore.setAdapter(adapter);
        recyclerMore.addItemDecoration(new SpacesItemDecoration(5));

        adapter.setOnItemClickListener(new onclick() {
            @Override
            public void onButtonCLick(String id) {
                if (CheckInternet.isInternetAvailable(MyCartActivity.this)) {
                    dialog.show();
                    controller.setAddToCart(token, id);
                } else {
                    startActivity(new Intent(MyCartActivity.this, NoInternetActivity.class));
                }
            }
        });
    }


    //set top liste items
    private void setTop(ArrayList<GetTopDataApi.Datum> arrayList) {
        TopItemsAdapter adapter = new TopItemsAdapter(this, arrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerTopPickup.setLayoutManager(linearLayoutManager);
        recyclerTopPickup.setAdapter(adapter);
        recyclerTopPickup.addItemDecoration(new SpacesItemDecoration(5));

        adapter.setOnClick(new Click() {
            @Override
            public void onClick(String id) {
                if (CheckInternet.isInternetAvailable(MyCartActivity.this)) {
                    dialog.show();
                    controller.setAddToCart(token, id);
                } else {
                    startActivity(new Intent(MyCartActivity.this, NoInternetActivity.class));
                }
            }
        });
    }


    // set data into cart
    private void setCart(ArrayList<GetCartDataApi.TotalCartProduct> arrayList) {
        CartAdapter adapter = new CartAdapter(this, arrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerCart.setLayoutManager(linearLayoutManager);
        recyclerCart.setAdapter(adapter);
        recyclerCart.addItemDecoration(new SpacesItemDecoration(10));

        // click to increse or descrise the quantity
        adapter.setOnItemClickListener(new CartAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String number, String id) {
                if (CheckInternet.isInternetAvailable(MyCartActivity.this)) {
                    controller.setIncreseItemQuantity(token, id, number);
                } else {
                    startActivity(new Intent(MyCartActivity.this, NoInternetActivity.class));
                }
            }
        });

        // for delete the item
        adapter.setOnItemdelte(new CartAdapter.OnItemClickDelete() {
            @Override
            public void onItemDelete(String id) {
                if (CheckInternet.isInternetAvailable(MyCartActivity.this)) {
                    dialog.show();
                    controller.setDeleteItems(token, id);
                } else {
                    startActivity(new Intent(MyCartActivity.this, NoInternetActivity.class));
                }
            }
        });
    }


    //add quantitiy
    @Override
    public void onSucessAdd(Response<GetMeesageApi> response) {
        dialog.dismiss();
        if (response.body().getStatus() == 200) {
            Snack.snackbar(MyCartActivity.this, response.body().getMessage());
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        } else {
            Snack.snackbar(MyCartActivity.this, response.body().getMessage());
        }
    }

    @Override
    public void Adderror(String error) {
        dialog.dismiss();
        Snack.snackbar(MyCartActivity.this, error);
    }

    //delete the item
    @Override
    public void onSuccessDelete(Response<GetMeesageApi> response) {
        dialog.dismiss();
        if (response.body().getStatus() == 200) {
            Snack.snackbar(MyCartActivity.this, response.body().getMessage());
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        } else {
            Snack.snackbar(MyCartActivity.this, response.body().getMessage());
        }
    }

    @Override
    public void errorDelete(String error) {
        dialog.dismiss();
        Snack.snackbar(MyCartActivity.this, error);
    }

    //get the Product list
    @Override
    public void onSuccess(Response<GetCartDataApi> response) {
        dialog.dismiss();
        if (response.body().getStatus() == 200) {
            ArrayList<GetCartDataApi.TotalCartProduct> arrayList = new ArrayList<>();
            txtItems.setText(" ( " + response.body().getData().getTotalProducts() + " items )");
            txtPrice.setText(" " + Config.GET_RUPPESS_SYMBOL + " " + response.body().getData().getTotalCartPrice());
            for (int i = 0; i < response.body().getData().getTotalCartProducts().size(); i++) {
                arrayList.add(response.body().getData().getTotalCartProducts().get(i));
                setCart(arrayList);
            }

        } else {
            Snack.snackbar(MyCartActivity.this, response.body().getMessage());
        }
    }

    @Override
    public void error(String error) {
        dialog.dismiss();
        Snack.snackbar(MyCartActivity.this, error);
    }

    @Override
    public void onSuccessTop(Response<GetTopDataApi> response) {
        if (response.body().getStatus() == 200) {
            ArrayList<GetTopDataApi.Datum> arrayList = new ArrayList<>();
            for (int i = 0; i < response.body().getData().size(); i++) {
                arrayList.add(response.body().getData().get(i));
                setTop(arrayList);
            }

        } else {
            Snack.snackbar(MyCartActivity.this, response.body().getMessage());
        }

    }

    @Override
    public void onErrorTop(String error) {
        Snack.snackbar(MyCartActivity.this, error);
    }

    @Override
    public void onSuccessExplore(Response<GetExploreMoreData> response) {
        if (response.body().getStatus() == 200) {
            ArrayList<GetExploreMoreData.Datum> arrayList = new ArrayList<>();
            for (int i = 0; i < response.body().getData().size(); i++) {
                arrayList.add(response.body().getData().get(i));
                setMore(arrayList);
            }

        } else {
            Snack.snackbar(MyCartActivity.this, response.body().getMessage());
        }

    }

    @Override
    public void onErrorExplore(String error) {
        Snack.snackbar(MyCartActivity.this, error);
    }

    @Override
    public void addtocart(Response<GetAddToCart> response) {
        dialog.dismiss();
        if (response.body().getStatus() == 200) {
            Snack.snackbar(MyCartActivity.this, response.body().getMessage());
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        } else {
            Snack.snackbar(MyCartActivity.this, response.body().getMessage());
        }
    }

    @Override
    public void errorCart(String error) {
        dialog.dismiss();
        Snack.snackbar(MyCartActivity.this, error);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
