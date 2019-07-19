package com.mandy.innfedia.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mandy.innfedia.MainActivity;
import com.mandy.innfedia.R;

public class YourOrderDetailsFragment extends Fragment {

    View view;

    public YourOrderDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_your_order_details, container, false);

        MainActivity.textView.setText("Order Details");

        return view;
    }

}
