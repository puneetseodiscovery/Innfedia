package com.mandy.innfedia.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mandy.innfedia.Activities.PaymentActivity;
import com.mandy.innfedia.AddressAdapter;
import com.mandy.innfedia.MainActivity;
import com.mandy.innfedia.R;

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

        MainActivity.textView.setText("Select Address");

        //set data into recylcer view
        setData();


        btnAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.framelayout, new AddAddressFragment());
                transaction.addToBackStack(null);
                transaction.commit();
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
        arrayList.add("House no:40, Phase - 8, sector - 71, Balongi Mohali,Punjab 140301");
        arrayList.add("Mandy web design,#B-100,Phase - 8, sector - 71 Mohali,Punjab 140300");

        addressAdapter = new AddressAdapter(getContext(), arrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(addressAdapter);

        addressAdapter.setOnItemClick(new AddressAdapter.OnItemClick() {
            @Override
            public void onItemClick(int position) {
                arrayList.get(position);
            }
        });

    }

}
