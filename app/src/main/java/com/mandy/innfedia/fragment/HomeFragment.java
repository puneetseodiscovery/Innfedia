package com.mandy.innfedia.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mandy.innfedia.Activities.NoInternetActivity;
import com.mandy.innfedia.Retrofit.ApiInterface;
import com.mandy.innfedia.ApiModel.BannerApi;
import com.mandy.innfedia.ApiModel.CategoryApi;
import com.mandy.innfedia.ApiModel.DiscountedApi;
import com.mandy.innfedia.ApiModel.NewArivalApi;
import com.mandy.innfedia.Utils.CheckInternet;
import com.mandy.innfedia.Utils.ProgressBarClass;
import com.mandy.innfedia.R;
import com.mandy.innfedia.Retrofit.ServiceGenerator;
import com.mandy.innfedia.SpacesItemDecoration;
import com.mandy.innfedia.adapter.BestSellAdapter;
import com.mandy.innfedia.adapter.CategoryAdapter;
import com.mandy.innfedia.adapter.DiscountedAdapter;
import com.mandy.innfedia.adapter.NewArrivalAdapter;
import com.mandy.innfedia.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    View view;
    ArrayList<CategoryApi.Datum> arrayCategory = new ArrayList<>();
    ArrayList<NewArivalApi.Datum> arrayNewArrivals = new ArrayList<>();
    ArrayList<DiscountedApi.Datum> arrayDiscounted = new ArrayList<>();
    ArrayList<BannerApi.Datum> arrayBanner = new ArrayList<>();

    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<Integer> arrayImage = new ArrayList<>();
    ViewPager viewPager;
    RecyclerView recyclerViewCategory, recyclerViewNew, recyclerViewDiscount, recyclerViewBestSell;
    FragmentManager manager;
    Context context;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);


        init();


        ((NestedScrollView) view.findViewById(R.id.scrool_view)).post(new Runnable() {
            public void run() {
                ((NestedScrollView) view.findViewById(R.id.scrool_view)).fullScroll(View.FOCUS_UP);
            }
        });


        if (CheckInternet.isInternetAvailable(context)) {
            //get category item
            getCategory();
            //view pager offer list
            getBanner();

            //view the new arival
            getNewArrivals();

            //set the discount data
            getDiscounted();
        } else {
            context.startActivity(new Intent(context, NoInternetActivity.class));
        }

//        //set data into best sell
//        setBestSell();


        return view;
    }

    private void init() {
        recyclerViewCategory = (RecyclerView) view.findViewById(R.id.recyclerCategory);
        recyclerViewNew = (RecyclerView) view.findViewById(R.id.recyclerNew);
        recyclerViewDiscount = (RecyclerView) view.findViewById(R.id.recyclerDiscount);
        recyclerViewBestSell = (RecyclerView) view.findViewById(R.id.recyclerBestSell);
        viewPager = (ViewPager) view.findViewById(R.id.recyclerOffer);

        manager = getActivity().getSupportFragmentManager();

    }


    //set category into recyclerView
    private void setCategoryData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategory.setLayoutManager(layoutManager);
        CategoryAdapter categoryAdapter = new CategoryAdapter(getContext(), arrayCategory, manager);
        recyclerViewCategory.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();
    }


    //set image into view pager
    private void setOfferImage() {
        final PagerAdapter adapter;

        TabLayout tabLayout;
        tabLayout = view.findViewById(R.id.indicator);

        adapter = new ViewPagerAdapter(context, arrayBanner);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager, true);


        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SliderTimer(), 4000, 6000);

    }

    // timer for change image
    private class SliderTimer extends TimerTask {
        @Override
        public void run() {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (viewPager.getCurrentItem() < arrayBanner.size() - 1) {
                            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                        } else {
                            viewPager.setCurrentItem(0);
                        }
                    }
                });
            }
        }
    }


    //set the data into new arivel
    private void setNewArivel() {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerViewNew.setLayoutManager(layoutManager);
        NewArrivalAdapter arrivalAdapter = new NewArrivalAdapter(getContext(), arrayNewArrivals, manager);
        recyclerViewNew.setAdapter(arrivalAdapter);
        recyclerViewNew.addItemDecoration(new SpacesItemDecoration(10));

    }


    //set the data into Discount
    private void setDiscount() {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerViewDiscount.setLayoutManager(layoutManager);
        DiscountedAdapter discountedAdapter = new DiscountedAdapter(getContext(), arrayDiscounted, manager);
        recyclerViewDiscount.setAdapter(discountedAdapter);
        recyclerViewDiscount.addItemDecoration(new SpacesItemDecoration(10));

    }


    //set the data into BestSell
    private void setBestSell() {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerViewBestSell.setLayoutManager(layoutManager);

        BestSellAdapter bestSellAdapter = new BestSellAdapter(getContext(), arrayList, arrayImage, manager);
        recyclerViewBestSell.setAdapter(bestSellAdapter);
        recyclerViewBestSell.addItemDecoration(new SpacesItemDecoration(10));

    }


    @Override
    public void onAttach(Context context1) {
        super.onAttach(context1);
        context = context1;
    }


    //get category
    private void getCategory() {
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<CategoryApi> call = apiInterface.getCategory();

        final Dialog dialog = ProgressBarClass.showProgressDialog(context);
        dialog.show();

        call.enqueue(new Callback<CategoryApi>() {
            @Override
            public void onResponse(Call<CategoryApi> call, Response<CategoryApi> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    arrayCategory.clear();
                    if (response.body().getStatus().equals(200)) {
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            arrayCategory.add(response.body().getData().get(i));

                            setCategoryData();
                        }
                    } else {
                        Toast.makeText(getContext(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CategoryApi> call, Throwable t) {
                dialog.dismiss();
            }
        });

    }


    //get the new arivals data
    private void getNewArrivals() {
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<NewArivalApi> call = apiInterface.newArrivalsApi();

        final Dialog dialog = ProgressBarClass.showProgressDialog(getContext());
        dialog.show();

        call.enqueue(new Callback<NewArivalApi>() {
            @Override
            public void onResponse(Call<NewArivalApi> call, Response<NewArivalApi> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    arrayNewArrivals.clear();
                    if (response.body().getStatus().equals(200)) {
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            arrayNewArrivals.add(response.body().getData().get(i));

                            setNewArivel();
                        }
                    } else {
                        Toast.makeText(getContext(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NewArivalApi> call, Throwable t) {
                dialog.dismiss();
            }
        });

    }

    // get the discounted product
    private void getDiscounted() {
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<DiscountedApi> call = apiInterface.getDiscountedapi();

        final Dialog dialog = ProgressBarClass.showProgressDialog(getContext());
        dialog.show();

        call.enqueue(new Callback<DiscountedApi>() {
            @Override
            public void onResponse(Call<DiscountedApi> call, Response<DiscountedApi> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    arrayDiscounted.clear();
                    if (response.body().getStatus().equals(200)) {
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            arrayDiscounted.add(response.body().getData().get(i));

                            setDiscount();
                        }
                    } else {
                        Toast.makeText(getContext(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DiscountedApi> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // get the banner
    private void getBanner() {
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<BannerApi> call = apiInterface.getBanner();
        final Dialog dialog = ProgressBarClass.showProgressDialog(context);
        dialog.show();
        call.enqueue(new Callback<BannerApi>() {
            @Override
            public void onResponse(Call<BannerApi> call, Response<BannerApi> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    arrayBanner.clear();
                    if (response.body().getStatus() == 200) {
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            arrayBanner.add(response.body().getData().get(i));
                        }
                        setOfferImage();

                    } else {
                        Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<BannerApi> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
