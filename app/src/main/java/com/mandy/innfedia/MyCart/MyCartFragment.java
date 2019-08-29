package com.mandy.innfedia.MyCart;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mandy.innfedia.Activities.NoInternetActivity;
import com.mandy.innfedia.Controller.Controller;
import com.mandy.innfedia.ApiModel.GetAddToCart;
import com.mandy.innfedia.ApiModel.GetMeesageApi;
import com.mandy.innfedia.MainActivity;
import com.mandy.innfedia.MyCart.topitems.Click;
import com.mandy.innfedia.MyCart.topitems.GetTopDataApi;
import com.mandy.innfedia.MyCart.topitems.TopItemsAdapter;
import com.mandy.innfedia.R;
import com.mandy.innfedia.SpacesItemDecoration;
import com.mandy.innfedia.Utils.CheckInternet;
import com.mandy.innfedia.Utils.Config;
import com.mandy.innfedia.Utils.ProgressBarClass;
import com.mandy.innfedia.Utils.SharedToken;
import com.mandy.innfedia.Utils.Snack;
import com.mandy.innfedia.MyCart.ExploreMore.CartMoreAdapter;
import com.mandy.innfedia.address.AddressFragment;
import com.mandy.innfedia.MyCart.ExploreMore.GetExploreMoreData;
import com.mandy.innfedia.MyCart.ExploreMore.onclick;

import java.util.ArrayList;

import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyCartFragment extends Fragment implements Controller.MyCartList, Controller.DeleteItems, Controller.IncreseItemQuantity, Controller.TopItemsList, Controller.ExploreMore, Controller.AddToCart {
    NestedScrollView scrollView;
    Context context;
    TextView txtItemNumber, txtTotalPrice;
    RecyclerView recyclerViewCart, recyclerViewMore, recyclerViewTop;
    Button button, btnProcced;
    View view;
    FragmentManager manager;
    SharedToken sharedToken;
    Dialog dialog;
    Controller controller;
    String token;

    public MyCartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_cart, container, false);
        dialog = ProgressBarClass.showProgressDialog(context);
        controller = new Controller((Controller.MyCartList) this, (Controller.DeleteItems) this, (Controller.IncreseItemQuantity) this, (Controller.TopItemsList) this, (Controller.ExploreMore) this, (Controller.AddToCart) this);
        manager = getActivity().getSupportFragmentManager();
        sharedToken = new SharedToken(context);
        token = "Bearer " + sharedToken.getShared();
        init();

        MainActivity.textView.setText("My Cart");


        if (CheckInternet.isInternetAvailable(context)) {
            controller.setMyCartList(token);
            controller.setExploreMore(token);
            controller.setTopItemsList(token);
        } else {
            context.startActivity(new Intent(context, NoInternetActivity.class));
        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
            }
        });


        btnProcced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
            }
        });

        ((NestedScrollView) view.findViewById(R.id.scrool_view)).post(new Runnable() {
            public void run() {
                ((NestedScrollView) view.findViewById(R.id.scrool_view)).fullScroll(View.FOCUS_UP);
            }
        });


        return view;
    }


    private void sendData() {
        AddressFragment addressFragment = new AddressFragment();
        Bundle bundle = new Bundle();
        bundle.putString("Cid", "0");
        addressFragment.setArguments(bundle);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.framelayout, addressFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void init() {
        button = (Button) view.findViewById(R.id.btnProced);
        btnProcced = (Button) view.findViewById(R.id.btnProced2);
        recyclerViewCart = (RecyclerView) view.findViewById(R.id.recyclerCart);
        recyclerViewMore = (RecyclerView) view.findViewById(R.id.recyclerMore);
        recyclerViewTop = (RecyclerView) view.findViewById(R.id.recyclerTopPickup);
        txtItemNumber = (TextView) view.findViewById(R.id.txtItems);
        txtTotalPrice = (TextView) view.findViewById(R.id.txtPrice);

    }

    //set more data
    private void setMore(ArrayList<GetExploreMoreData.Datum> arrayList) {
        CartMoreAdapter adapter = new CartMoreAdapter(getContext(), arrayList, manager);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewMore.setLayoutManager(linearLayoutManager);
        recyclerViewMore.setAdapter(adapter);
        recyclerViewMore.addItemDecoration(new SpacesItemDecoration(5));

        adapter.setOnItemClickListener(new onclick() {
            @Override
            public void onButtonCLick(String id) {
                if (CheckInternet.isInternetAvailable(context)) {
                    dialog.show();
                    controller.setAddToCart(token, id);
                } else {
                    context.startActivity(new Intent(context, NoInternetActivity.class));
                }
            }
        });
    }


    //set top liste items
    private void setTop(ArrayList<GetTopDataApi.Datum> arrayList) {
        TopItemsAdapter adapter = new TopItemsAdapter(getContext(), arrayList, manager);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewTop.setLayoutManager(linearLayoutManager);
        recyclerViewTop.setAdapter(adapter);
        recyclerViewTop.addItemDecoration(new SpacesItemDecoration(5));

        adapter.setOnClick(new Click() {
            @Override
            public void onClick(String id) {
                if (CheckInternet.isInternetAvailable(context)) {
                    dialog.show();
                    controller.setAddToCart(token, id);
                } else {
                    context.startActivity(new Intent(context, NoInternetActivity.class));
                }
            }
        });
    }


    // set data into cart
    private void setCart(ArrayList<GetCartDataApi.TotalCartProduct> arrayList) {
        CartAdapter adapter = new CartAdapter(getContext(), arrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerViewCart.setLayoutManager(linearLayoutManager);
        recyclerViewCart.setAdapter(adapter);
        recyclerViewCart.addItemDecoration(new SpacesItemDecoration(10));

        // click to increse or descrise the quantity
        adapter.setOnItemClickListener(new CartAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String number, String id) {
                if (CheckInternet.isInternetAvailable(context)) {
                    controller.setIncreseItemQuantity(token, id, number);
                } else {
                    context.startActivity(new Intent(context, NoInternetActivity.class));
                }
            }
        });

        // for delete the item
        adapter.setOnItemdelte(new CartAdapter.OnItemClickDelete() {
            @Override
            public void onItemDelete(String id) {
                if (CheckInternet.isInternetAvailable(context)) {
                    dialog.show();
                    controller.setDeleteItems(token, id);
                } else {
                    context.startActivity(new Intent(context, NoInternetActivity.class));
                }
            }
        });
    }

    @Override
    public void onAttach(Context context1) {
        super.onAttach(context1);
        context = context1;
    }

    //add quantitiy
    @Override
    public void onSucessAdd(Response<GetMeesageApi> response) {
        dialog.dismiss();
        if (response.body().getStatus() == 200) {
            Snack.snackbar(getActivity(), response.body().getMessage());
        } else {
            Snack.snackbar(getActivity(), response.body().getMessage());
        }
    }

    @Override
    public void Adderror(String error) {
        dialog.dismiss();
        Snack.snackbar(getActivity(), error);
    }

    //delete the item
    @Override
    public void onSuccessDelete(Response<GetMeesageApi> response) {
        dialog.dismiss();
        if (response.body().getStatus() == 200) {
            Snack.snackbar(getActivity(), response.body().getMessage());
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.detach(MyCartFragment.this).attach(MyCartFragment.this).commit();
        } else {
            Snack.snackbar(getActivity(), response.body().getMessage());
        }
    }

    @Override
    public void errorDelete(String error) {
        dialog.dismiss();
        Snack.snackbar(getActivity(), error);
    }

    //get the Product list
    @Override
    public void onSuccess(Response<GetCartDataApi> response) {
        dialog.dismiss();
        if (response.body().getStatus() == 200) {
            ArrayList<GetCartDataApi.TotalCartProduct> arrayList = new ArrayList<>();
            txtItemNumber.setText(" ( " + response.body().getData().getTotalProducts() + " items )");
            txtTotalPrice.setText(" " + Config.GET_RUPPESS_SYMBOL + " " + response.body().getData().getTotalCartPrice());
            for (int i = 0; i < response.body().getData().getTotalCartProducts().size(); i++) {
                arrayList.add(response.body().getData().getTotalCartProducts().get(i));
                setCart(arrayList);
            }

        } else {
            Snack.snackbar(getActivity(), response.body().getMessage());
        }
    }

    @Override
    public void error(String error) {
        dialog.dismiss();
        Snack.snackbar(getActivity(), error);
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
            Snack.snackbar(getActivity(), response.body().getMessage());
        }

    }

    @Override
    public void onErrorTop(String error) {
        Snack.snackbar(getActivity(), error);
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
            Snack.snackbar(getActivity(), response.body().getMessage());
        }

    }

    @Override
    public void onErrorExplore(String error) {
        Snack.snackbar(getActivity(), error);
    }

    @Override
    public void addtocart(Response<GetAddToCart> response) {
        dialog.dismiss();
        if (response.body().getStatus() == 200) {
            Snack.snackbar(getActivity(), response.body().getMessage());
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.detach(MyCartFragment.this).attach(MyCartFragment.this).commit();
        } else {
            Snack.snackbar(getActivity(), response.body().getMessage());
        }
    }

    @Override
    public void errorCart(String error) {
        dialog.dismiss();
        Snack.snackbar(getActivity(), error);
    }
}
