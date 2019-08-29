package com.mandy.innfedia.home2;


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
import com.mandy.innfedia.home2.adapter.BottomWearAdapter;
import com.mandy.innfedia.home2.adapter.ExploreMoreAdapter;
import com.mandy.innfedia.home2.adapter.TopWearAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home2Fragment extends Fragment {
    RecyclerView recyclerViewTop, recyclerViewBottom, recyclerViewExplore;
    FragmentManager manager;
    View view;
    Context context;
    String id;

    public Home2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home2, container, false);

        init();

        MainActivity.textView.setText("Infedia");

        if (CheckInternet.isInternetAvailable(context)) {
            getData();
        } else {
            context.startActivity(new Intent(context, NoInternetActivity.class));
        }

        return view;

    }


    private void init() {
        recyclerViewTop = (RecyclerView) view.findViewById(R.id.recyclerTop);
        recyclerViewBottom = (RecyclerView) view.findViewById(R.id.recyclerBottom);
        recyclerViewExplore = (RecyclerView) view.findViewById(R.id.recyclerExploreMore);

        manager = getActivity().getSupportFragmentManager();

        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getString("SubID");

        }


    }


    //get product data
    private void getData() {
        SharedToken sharedToken = new SharedToken(context);
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<GetSubCategoryApi> call = apiInterface.getSubCategory("Bearer " + sharedToken.getShared(), id);

        final Dialog dialog = ProgressBarClass.showProgressDialog(context);
        dialog.show();

        call.enqueue(new Callback<GetSubCategoryApi>() {
            @Override
            public void onResponse(Call<GetSubCategoryApi> call, Response<GetSubCategoryApi> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals(200)) {
                        for (int i = 0; i < response.body().getData().size(); i++) {

                            setTopData(response.body().getData().get(i).getTopWear());

                            setBottomData(response.body().getData().get(i).getBottomWear());

                            setExploreData(response.body().getData().get(i).getExploreMore());
                        }
                    } else {
                        Snack.snackbar(getActivity(), response.body().getMessage());
                    }

                } else {
                    Snack.snackbar(getActivity(), response.message());
                }
            }

            @Override
            public void onFailure(Call<GetSubCategoryApi> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }

    //set Top wear
    private void setTopData(List<GetSubCategoryApi.TopWear> topWear) {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerViewTop.setLayoutManager(layoutManager);
        recyclerViewTop.addItemDecoration(new SpacesItemDecoration(10));
        TopWearAdapter adapter = new TopWearAdapter(context, topWear, manager);
        recyclerViewTop.setAdapter(adapter);
    }


    //set the top reyclerView item
    private void setBottomData(List<GetSubCategoryApi.BottomWear> bottomWears) {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerViewBottom.setLayoutManager(layoutManager);
        recyclerViewBottom.addItemDecoration(new SpacesItemDecoration(10));
        BottomWearAdapter adapter = new BottomWearAdapter(getContext(), bottomWears, manager);
        recyclerViewBottom.setAdapter(adapter);
    }

    //set the top reyclerView item
    private void setExploreData(List<GetSubCategoryApi.ExploreMore> exploreWears) {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerViewExplore.setLayoutManager(layoutManager);
        recyclerViewExplore.addItemDecoration(new SpacesItemDecoration(10));
        ExploreMoreAdapter adapter = new ExploreMoreAdapter(getContext(), exploreWears, manager);
        recyclerViewExplore.setAdapter(adapter);
    }


    @Override
    public void onAttach(Context context1) {
        super.onAttach(context1);
        context = context1;
    }
}
