package com.mandy.innfedia.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mandy.innfedia.R;
import com.mandy.innfedia.SpacesItemDecoration;
import com.mandy.innfedia.adapter.ColorAdapter;
import com.mandy.innfedia.adapter.SizeAdapter;
import com.mandy.innfedia.adapter.ViewPagerProductImageAdapter;
import com.mandy.innfedia.adapter.main2.SeeRelatedItemAdapter;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetailsFragment extends Fragment {
    ViewPager viewPager;
    CircleIndicator circleIndicator;
    RecyclerView recyclerViewSize, recyclerViewColor, recyclerViewSeeRelated;
    TextView textColor, textSize;
    View view;
    FragmentManager manager;

    public ProductDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_product_details, container, false);

        manager = getActivity().getSupportFragmentManager();

        init();


        //set the image data
        setViewPager();

        //set size
        setSize();

        //set color
        setColor();

        //see the related item
        setRelatedData();


        return view;

    }


    private void init() {
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        circleIndicator = (CircleIndicator) view.findViewById(R.id.circleindecator);
        recyclerViewSize = (RecyclerView) view.findViewById(R.id.recyclerSize);
        recyclerViewSeeRelated = (RecyclerView) view.findViewById(R.id.recyclerRelated);
        recyclerViewColor = (RecyclerView) view.findViewById(R.id.recyclerColor);
        textColor = (TextView) view.findViewById(R.id.txtColor);
        textSize = (TextView) view.findViewById(R.id.txtSize);

    }


    //set private
    private void setViewPager() {
        ArrayList<Integer> arrayList = new ArrayList<>();

        arrayList.add(R.drawable.image6);
        arrayList.add(R.drawable.image1);
        arrayList.add(R.drawable.image2);
        arrayList.add(R.drawable.image3);
        PagerAdapter adapter = new ViewPagerProductImageAdapter(getContext(), arrayList);
        viewPager.setAdapter(adapter);
        circleIndicator.setViewPager(viewPager);

    }


    private void setSize() {
        final ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Small");
        arrayList.add("Medium");
        arrayList.add("Large");
        arrayList.add("X-Large");
        arrayList.add("XX-Large");
        SizeAdapter adapter = new SizeAdapter(getContext(), arrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewSize.setLayoutManager(linearLayoutManager);
        recyclerViewSize.setAdapter(adapter);
        recyclerViewSize.addItemDecoration(new SpacesItemDecoration(15));
        adapter.setOnItemClick(new ColorAdapter.OnItemClick() {
            @Override
            public void onItemClick(int position) {
                textSize.setText(arrayList.get(position));
            }
        });
    }


    private void setColor() {
        final ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("RED");
        arrayList.add("BLACK");
        arrayList.add("WHITE");
        arrayList.add("BLUE");
        arrayList.add("PINK");

        ColorAdapter colorAdapter = new ColorAdapter(getContext(), arrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewColor.setLayoutManager(linearLayoutManager);
        recyclerViewColor.setAdapter(colorAdapter);
        recyclerViewColor.addItemDecoration(new SpacesItemDecoration(15));
        colorAdapter.setOnItemClick(new ColorAdapter.OnItemClick() {
            @Override
            public void onItemClick(int position) {

                textColor.setText(arrayList.get(position));
            }
        });

    }


    // see the relted product
    private void setRelatedData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewSeeRelated.setLayoutManager(linearLayoutManager);
        SeeRelatedItemAdapter adapter = new SeeRelatedItemAdapter(getContext(), manager);
        recyclerViewSeeRelated.setAdapter(adapter);
        recyclerViewSeeRelated.addItemDecoration(new SpacesItemDecoration(15));
    }

}
