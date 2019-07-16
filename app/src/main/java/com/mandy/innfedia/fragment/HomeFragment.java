package com.mandy.innfedia.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mandy.innfedia.R;
import com.mandy.innfedia.SpacesItemDecoration;
import com.mandy.innfedia.adapter.BestSellAdapter;
import com.mandy.innfedia.adapter.CategoryAdapter;
import com.mandy.innfedia.adapter.DiscountedAdapter;
import com.mandy.innfedia.adapter.NewArrivalAdapter;
import com.mandy.innfedia.adapter.ViewPagerAdapter;
import com.mandy.innfedia.adapter.main2.ExploreMoreAdapter;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class HomeFragment extends Fragment {

    View view;
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


        setArrayList();

        //set data into recyclerView
        setCategoryData();


        //view pager offer list
        setOfferImage();

        //view the new arival
        setNewArivel();

        //set the discount data
        setDiscount();

        //set data into best sell
        setBestSell();


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
        CategoryAdapter categoryAdapter = new CategoryAdapter(getContext(), arrayList);
        recyclerViewCategory.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();
    }


    private void setArrayList() {

        clear();


        arrayList.add("Men");
        arrayList.add("Women");
        arrayList.add("Kids");
        arrayList.add("Jewelleries");

        arrayImage.add(R.drawable.image1);
        arrayImage.add(R.drawable.image2);
        arrayImage.add(R.drawable.image3);
        arrayImage.add(R.drawable.image4);
    }


    //set image into view pager
    private void setOfferImage() {
        final PagerAdapter adapter;

        TabLayout tabLayout;
        tabLayout = view.findViewById(R.id.indicator);

        adapter = new ViewPagerAdapter(getContext(), arrayImage);
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
                        if (viewPager.getCurrentItem() < arrayImage.size() - 1) {
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

        NewArrivalAdapter arrivalAdapter = new NewArrivalAdapter(getContext(), arrayList, arrayImage, manager);
        recyclerViewNew.setAdapter(arrivalAdapter);
        recyclerViewNew.addItemDecoration(new SpacesItemDecoration(15));

    }


    //set the data into Discount
    private void setDiscount() {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerViewDiscount.setLayoutManager(layoutManager);

        DiscountedAdapter discountedAdapter = new DiscountedAdapter(getContext(), arrayList, arrayImage, manager);
        recyclerViewDiscount.setAdapter(discountedAdapter);
        recyclerViewDiscount.addItemDecoration(new SpacesItemDecoration(15));

    }


    //set the data into BestSell
    private void setBestSell() {

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerViewBestSell.setLayoutManager(layoutManager);

        BestSellAdapter bestSellAdapter = new BestSellAdapter(getContext(), arrayList, arrayImage, manager);
        recyclerViewBestSell.setAdapter(bestSellAdapter);
        recyclerViewBestSell.addItemDecoration(new SpacesItemDecoration(15));

    }


    private void clear() {
        arrayImage.clear();
        arrayList.clear();
    }

    @Override
    public void onAttach(Context context1) {
        super.onAttach(context1);
        context = context1;
    }
}
