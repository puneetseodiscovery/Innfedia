package com.mandy.innfedia.productList;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mandy.innfedia.Activities.NoInternetActivity;
import com.mandy.innfedia.Retrofit.ApiInterface;
import com.mandy.innfedia.MainActivity;
import com.mandy.innfedia.R;
import com.mandy.innfedia.Retrofit.ServiceGenerator;
import com.mandy.innfedia.SpacesItemDecoration;
import com.mandy.innfedia.Utils.CheckInternet;
import com.mandy.innfedia.Utils.ProgressBarClass;
import com.mandy.innfedia.Utils.SharedToken;
import com.mandy.innfedia.Utils.Snack;
import com.mandy.innfedia.productList.adapter.ProductListAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductListFragment extends Fragment {

    View view;
    Context context;
    public static String id;
    RecyclerView recyclerView;
    FragmentManager manager;
    ArrayList<GetProductList.Datum> arrayList = new ArrayList<>();

    public ProductListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_product_list, container, false);

        init();

        if (CheckInternet.isInternetAvailable(context)) {
            getData();
        } else {
            context.startActivity(new Intent(context, NoInternetActivity.class));
        }

        MainActivity.textView.setText("Product's List");

        return view;

    }

    private void init() {
        recyclerView = view.findViewById(R.id.recyclerProduct);
        manager = getActivity().getSupportFragmentManager();

        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getString("SubId");
            Toast.makeText(context, "" + id, Toast.LENGTH_SHORT).show();
        }

    }


    //get data from apis
    private void getData() {
        SharedToken sharedToken = new SharedToken(context);
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<GetProductList> call = apiInterface.getProductList("Bearer " + sharedToken.getShared(), id);
        final Dialog dialog = ProgressBarClass.showProgressDialog(context);
        dialog.show();
        call.enqueue(new Callback<GetProductList>() {
            @Override
            public void onResponse(Call<GetProductList> call, Response<GetProductList> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    arrayList.clear();
                    if (response.body().getStatus().equals(200)) {
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            arrayList.add(response.body().getData().get(i));

                            setData(arrayList);

                        }

                    } else {
                        Snack.snackbar(getActivity(), response.body().getMessage());
                    }


                } else {
                    Snack.snackbar(getActivity(), response.message());
                }
            }

            @Override
            public void onFailure(Call<GetProductList> call, Throwable t) {
                dialog.dismiss();
                Snack.snackbar(getActivity(), t.getMessage());
            }
        });
    }

    private void setData(ArrayList<GetProductList.Datum> datum) {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        ProductListAdapter adapter = new ProductListAdapter(context, datum, manager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SpacesItemDecoration(10));
    }


    @Override
    public void onAttach(Context context1) {
        super.onAttach(context1);
        context = context1;
    }
}
