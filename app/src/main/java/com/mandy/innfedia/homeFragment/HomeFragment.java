package com.mandy.innfedia.homeFragment;

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
import android.widget.TextView;
import android.widget.Toast;

import com.mandy.innfedia.R;
import com.mandy.innfedia.commonActivity.NoInternetActivity;
import com.mandy.innfedia.controller.Controller;
import com.mandy.innfedia.dashboardproducts.ProductsActivity;
import com.mandy.innfedia.homeFragment.adapter.BestSellAdapter;
import com.mandy.innfedia.homeFragment.adapter.CategoryAdapter;
import com.mandy.innfedia.homeFragment.adapter.DiscountedAdapter;
import com.mandy.innfedia.homeFragment.adapter.NewArrivalAdapter;
import com.mandy.innfedia.homeFragment.adapter.ViewPagerAdapter;
import com.mandy.innfedia.homeFragment.apis.BannerApi;
import com.mandy.innfedia.homeFragment.apis.CategoryApi;
import com.mandy.innfedia.productList.GetProductList;
import com.mandy.innfedia.utils.CheckInternet;
import com.mandy.innfedia.utils.ProgressBarClass;
import com.mandy.innfedia.utils.Snack;
import com.mandy.innfedia.utils.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Response;


public class HomeFragment extends Fragment implements Controller.GetCategory, Controller.GetBanner, Controller.GetNewArrival, Controller.GetDisccountedItem, Controller.BestSell {

    View view;
    ArrayList<CategoryApi.Datum> arrayCategory = new ArrayList<>();
    ArrayList<GetProductList.Datum> arrayNewArrivals = new ArrayList<>();
    ArrayList<GetProductList.Datum> arrayDiscounted = new ArrayList<>();
    ArrayList<BannerApi.Datum> arrayBanner = new ArrayList<>();

    ViewPager viewPager;
    RecyclerView recyclerViewCategory, recyclerViewNew, recyclerViewDiscount, recyclerViewBestSell;
    FragmentManager manager;
    Context context;
    Controller controller;
    Dialog dialog;

    @BindView(R.id.txtSeeNew)
    TextView txtSeeNew;
    @BindView(R.id.txtSeeDiscount)
    TextView txtSeeDiscount;
    Unbinder unbinder;
    @BindView(R.id.txtSeeBest)
    TextView txtSeeBest;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        controller = new Controller((Controller.GetCategory) this, (Controller.GetBanner) this, (Controller.GetNewArrival) this, (Controller.GetDisccountedItem) this, (Controller.BestSell) this);
        dialog = ProgressBarClass.showProgressDialog(context);

        init();

        ((NestedScrollView) view.findViewById(R.id.scrool_view)).post(new Runnable() {
            public void run() {
                ((NestedScrollView) view.findViewById(R.id.scrool_view)).fullScroll(View.FOCUS_UP);
            }
        });


        if (CheckInternet.isInternetAvailable(context)) {
            dialog.show();
            //get category item
            controller.setGetCategory();
            //view pager offer list
            controller.setGetBanner();
            //view the new arival
            controller.setGetNewArrival();

            //set the discount data
            controller.setGetDisccountedItem();

            controller.setBestSell();
        } else {
            context.startActivity(new Intent(context, NoInternetActivity.class));
        }

        unbinder = ButterKnife.bind(this, view);
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

    // get the category
    @Override
    public void onSuccess(Response<CategoryApi> response) {
        dialog.dismiss();
        arrayCategory.clear();
        if (response.body().getStatus().equals(200)) {
            for (int i = 0; i < response.body().getData().size(); i++) {
                arrayCategory.add(response.body().getData().get(i));

                setCategoryData();
            }
        } else {
            Snack.snackbar(getActivity(), response.body().getMessage());
        }
    }

    // for banner
    @Override
    public void onSuccessBanner(Response<BannerApi> response) {
        dialog.dismiss();
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

    // for new arrivals
    @Override
    public void onSuccessNew(Response<GetProductList> response) {
        dialog.dismiss();
        arrayNewArrivals.clear();
        if (response.body().getStatus().equals(200)) {
            for (int i = 0; i < response.body().getData().size(); i++) {
                arrayNewArrivals.add(response.body().getData().get(i));

                setNewArivel();
            }
        } else {
            Toast.makeText(getContext(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSuccessDiscont(Response<GetProductList> response) {
        dialog.dismiss();
        arrayDiscounted.clear();
        if (response.body().getStatus().equals(200)) {
            for (int i = 0; i < response.body().getData().size(); i++) {
                arrayDiscounted.add(response.body().getData().get(i));

                setDiscount();
            }
        } else {
            Toast.makeText(getContext(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onSucessBest(Response<GetProductList> response) {
        dialog.dismiss();
        if (response.isSuccessful()) {
            ArrayList<GetProductList.Datum> arrayList = new ArrayList<>();
            if (response.body().getStatus() == 200) {
                for (int i = 0; i < response.body().getData().size(); i++) {
                    arrayList.add(response.body().getData().get(i));
                }
                setBestSell(arrayList);
            }
        } else {
            Toast.makeText(context, "" + response.message(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void error(String error) {
        dialog.dismiss();
        Snack.snackbar(getActivity(), error);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.txtSeeNew, R.id.txtSeeDiscount})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txtSeeNew:
                Intent intent = new Intent(context, ProductsActivity.class);
                intent.putExtra("type", "0");
                startActivity(intent);
                break;
            case R.id.txtSeeDiscount:
                Intent intent1 = new Intent(context, ProductsActivity.class);
                intent1.putExtra("type", "1");
                startActivity(intent1);
                break;
        }
    }

    @OnClick(R.id.txtSeeBest)
    public void onViewClicked() {
        Intent intent1 = new Intent(context, ProductsActivity.class);
        intent1.putExtra("type", "2");
        startActivity(intent1);

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
    private void setBestSell(ArrayList<GetProductList.Datum> arrayList) {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerViewBestSell.setLayoutManager(layoutManager);
        BestSellAdapter bestSellAdapter = new BestSellAdapter(context, arrayList, manager);
        recyclerViewBestSell.setAdapter(bestSellAdapter);
        recyclerViewBestSell.addItemDecoration(new SpacesItemDecoration(10));

    }


    @Override
    public void onAttach(Context context1) {
        super.onAttach(context1);
        context = context1;
    }

}
