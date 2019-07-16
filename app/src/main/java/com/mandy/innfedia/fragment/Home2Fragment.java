package com.mandy.innfedia.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mandy.innfedia.R;
import com.mandy.innfedia.SpacesItemDecoration;
import com.mandy.innfedia.adapter.main2.BottomWearAdapter;
import com.mandy.innfedia.adapter.main2.ExploreMoreAdapter;
import com.mandy.innfedia.adapter.main2.TopWearAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home2Fragment extends Fragment {
    RecyclerView recyclerViewTop, recyclerViewBottom, recyclerViewExplore;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<Integer> arrayImage = new ArrayList<>();
    FragmentManager manager;

    View view;

    public Home2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home2, container, false);

        init();

        //array data
        setArrayList();

        //vew the top data
        setTopData();

        //set explore data
        setExploreData();


        //set the Bottom data
        setBottomData();


        return view;

    }


    private void init() {
        recyclerViewTop = (RecyclerView) view.findViewById(R.id.recyclerTop);
        recyclerViewBottom = (RecyclerView) view.findViewById(R.id.recyclerBottom);
        recyclerViewExplore = (RecyclerView) view.findViewById(R.id.recyclerExploreMore);

        manager = getActivity().getSupportFragmentManager();

    }


    private void setArrayList() {
        arrayList.add("T-Shirt");
        arrayList.add("Polo");
        arrayList.add("Causal");
        arrayList.add("Formal Shirt");
        arrayList.add("Suit & Blazers");
        arrayList.add("Kurtas");

        arrayImage.add(R.drawable.image1);
        arrayImage.add(R.drawable.image2);
        arrayImage.add(R.drawable.image3);
        arrayImage.add(R.drawable.image4);
        arrayImage.add(R.drawable.image5);
        arrayImage.add(R.drawable.image6);
    }

    //set the top reyclerView item
    private void setTopData() {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerViewTop.setLayoutManager(layoutManager);
        recyclerViewTop.addItemDecoration(new SpacesItemDecoration(15));

        TopWearAdapter adapter = new TopWearAdapter(getContext(), arrayList, arrayImage,manager);
        recyclerViewTop.setAdapter(adapter);
    }


    //set the top reyclerView item
    private void setBottomData() {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerViewBottom.setLayoutManager(layoutManager);
        recyclerViewBottom.addItemDecoration(new SpacesItemDecoration(15));

        BottomWearAdapter adapter = new BottomWearAdapter(getContext(), arrayList, arrayImage,manager);
        recyclerViewBottom.setAdapter(adapter);
    }


    //set the top reyclerView item
    private void setExploreData() {

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerViewExplore.setLayoutManager(layoutManager);
        recyclerViewExplore.addItemDecoration(new SpacesItemDecoration(15));

        ExploreMoreAdapter adapter = new ExploreMoreAdapter(getContext(), arrayList, arrayImage,manager);
        recyclerViewExplore.setAdapter(adapter);
    }

}
