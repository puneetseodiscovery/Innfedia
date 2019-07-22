package com.mandy.innfedia.fragment;


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

import com.mandy.innfedia.MainActivity;
import com.mandy.innfedia.R;
import com.mandy.innfedia.SpacesItemDecoration;
import com.mandy.innfedia.adapter.CartAdapter;
import com.mandy.innfedia.adapter.CartMoreAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyCartFragment extends Fragment {
    NestedScrollView scrollView;
    RecyclerView recyclerViewCart, recyclerViewMore, recyclerViewTop;
    Button button, btnProcced;
    View view;
    FragmentManager manager;
    ArrayList<Integer> arrayImage = new ArrayList<>();

    public MyCartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_cart, container, false);

        init();

        MainActivity.textView.setText("My Cart");


        arrayImage.add(R.drawable.jwellery);
        arrayImage.add(R.drawable.women);
        arrayImage.add(R.drawable.kid);
        arrayImage.add(R.drawable.womantop);
        arrayImage.add(R.drawable.kid);
        arrayImage.add(R.drawable.jwellery);
        arrayImage.add(R.drawable.women);
        arrayImage.add(R.drawable.jwellery);
        arrayImage.add(R.drawable.womantop);
        arrayImage.add(R.drawable.kid);

        //set the cart data
        setCart();

        //see more
        setMore();

        //set top
        setTop();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.framelayout, new AddressFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        btnProcced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.framelayout, new AddressFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        ((NestedScrollView) view.findViewById(R.id.scrool_view)).post(new Runnable() {
            public void run() {
                ((NestedScrollView) view.findViewById(R.id.scrool_view)).fullScroll(View.FOCUS_UP);
            }
        });


        return view;
    }


    private void init() {
        scrollView = (NestedScrollView) view.findViewById(R.id.scrool_view);
        button = (Button) view.findViewById(R.id.btnProced);
        btnProcced = (Button) view.findViewById(R.id.btnProced2);
        recyclerViewCart = (RecyclerView) view.findViewById(R.id.recyclerCart);
        recyclerViewMore = (RecyclerView) view.findViewById(R.id.recyclerMore);
        recyclerViewTop = (RecyclerView) view.findViewById(R.id.recyclerTopPickup);

        manager = getActivity().getSupportFragmentManager();

    }


    private void setCart() {
        CartAdapter adapter = new CartAdapter(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewCart.setLayoutManager(linearLayoutManager);
        recyclerViewCart.setAdapter(adapter);
        recyclerViewCart.addItemDecoration(new SpacesItemDecoration(15));
    }


    private void setMore() {
        CartMoreAdapter adapter = new CartMoreAdapter(getContext(), arrayImage, manager);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewMore.setLayoutManager(linearLayoutManager);
        recyclerViewMore.setAdapter(adapter);
        recyclerViewMore.addItemDecoration(new SpacesItemDecoration(15));
    }


    private void setTop() {
        CartMoreAdapter adapter = new CartMoreAdapter(getContext(), arrayImage, manager);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewTop.setLayoutManager(linearLayoutManager);
        recyclerViewTop.setAdapter(adapter);
        recyclerViewTop.addItemDecoration(new SpacesItemDecoration(15));
    }


}
