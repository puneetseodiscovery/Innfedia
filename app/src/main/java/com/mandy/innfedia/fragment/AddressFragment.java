package com.mandy.innfedia.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mandy.innfedia.Activities.AddAddressActivity;
import com.mandy.innfedia.Activities.PaymentActivity;
import com.mandy.innfedia.AddressAdapter;
import com.mandy.innfedia.R;
import com.mandy.innfedia.SpacesItemDecoration;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddressFragment extends Fragment {
    RecyclerView recyclerView;
    Button btnAddAddress, btnConfirm;
    AddressAdapter addressAdapter;
    View view;

    public AddressFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_address, container, false);

        init();


        //set data into recylcer view
        setData();


        btnAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AddAddressActivity.class));
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), PaymentActivity.class));
            }
        });


        return view;
    }

    private void init() {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_address);
        btnAddAddress = (Button) view.findViewById(R.id.add_address_btn);
        btnConfirm = (Button) view.findViewById(R.id.continue_btn);
    }


    //set data into recyclerView
    private void setData() {
        final ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("House no:40, Balongi,Mohali,Punjab" +
                "\n amitpanday51@gmail.com");
        arrayList.add("Mandy web design D-152, 140301 \n mohali ,pubjab");

        addressAdapter = new AddressAdapter(getContext(), arrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(addressAdapter);
        recyclerView.addItemDecoration(new SpacesItemDecoration(15));

        addressAdapter.setOnItemClick(new AddressAdapter.OnItemClick() {
            @Override
            public void onItemClick(int position) {
                arrayList.get(position);
            }
        });

    }

}
