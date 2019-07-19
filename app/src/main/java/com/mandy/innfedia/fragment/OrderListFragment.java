package com.mandy.innfedia.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mandy.innfedia.MainActivity;
import com.mandy.innfedia.R;
import com.mandy.innfedia.SpacesItemDecoration;
import com.mandy.innfedia.adapter.YourOrderAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderListFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<String> arrayList = new ArrayList<>();
    View view;
    FragmentManager manager;

    public OrderListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_order_list, container, false);

        init();

        MainActivity.textView.setText("My Order List");

        manager = getActivity().getSupportFragmentManager();

        arrayList.add("Dispatch");
        arrayList.add("Recived");
        arrayList.add("Recived");
        arrayList.add("Recived");
        arrayList.add("Recived");

        setRecyclerView();

        return view;
    }

    private void init() {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerOrder);
    }


    private void setRecyclerView() {
        YourOrderAdapter adapter = new YourOrderAdapter(getContext(), arrayList, manager);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}
