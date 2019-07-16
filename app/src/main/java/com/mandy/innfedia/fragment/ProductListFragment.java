package com.mandy.innfedia.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mandy.innfedia.R;
import com.mandy.innfedia.SpacesItemDecoration;
import com.mandy.innfedia.adapter.ProductListAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductListFragment extends Fragment {

    View view;
    RecyclerView recyclerView;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<Integer> arrayImage = new ArrayList<>();
    FragmentManager manager;

    public ProductListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_product_list, container, false);


        recyclerView = view.findViewById(R.id.recyclerProduct);
        manager = getActivity().getSupportFragmentManager();

        setData();

        return view;

    }


    private void setData() {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);

        ProductListAdapter adapter = new ProductListAdapter(getContext(), manager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SpacesItemDecoration(15));
    }


}
