package com.mandy.innfedia.address;


import android.app.Dialog;
import android.content.Context;
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

import com.mandy.innfedia.Activities.NoInternetActivity;
import com.mandy.innfedia.Payment.PaymentActivity;
import com.mandy.innfedia.Retrofit.ApiInterface;
import com.mandy.innfedia.Retrofit.ServiceGenerator;
import com.mandy.innfedia.Utils.CheckInternet;
import com.mandy.innfedia.Utils.ProgressBarClass;
import com.mandy.innfedia.Utils.SharedToken;
import com.mandy.innfedia.Utils.Snack;
import com.mandy.innfedia.address.adapter.AddressAdapter;
import com.mandy.innfedia.MainActivity;
import com.mandy.innfedia.R;
import com.mandy.innfedia.address.addAddress.AddAddressFragment;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddressFragment extends Fragment {
    RecyclerView recyclerView;
    Button btnAddAddress, btnConfirm;
    AddressAdapter addressAdapter;
    SharedToken sharedToken;
    View view;
    Context context;
    String address, id;

    public AddressFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_address, container, false);

        init();

        sharedToken = new SharedToken(context);
        MainActivity.textView.setText("Select Address");

        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getString("Cid");
        }


        if (CheckInternet.isInternetAvailable(context)) {
            getAddressList();
        } else {
            context.startActivity(new Intent(context, NoInternetActivity.class));
        }

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

                Intent intent = new Intent(context, PaymentActivity.class);
                intent.putExtra("Cid", id);
                intent.putExtra("Address", address);
                startActivity(intent);
            }
        });


        return view;
    }

    private void init() {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_address);
        btnAddAddress = (Button) view.findViewById(R.id.add_address_btn);
        btnConfirm = (Button) view.findViewById(R.id.continue_btn);
    }


    private void getAddressList() {
        final ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<GetAddressApi> call = apiInterface.getAddressList("Bearer " + sharedToken.getShared());
        final Dialog dialog = ProgressBarClass.showProgressDialog(context);
        dialog.show();
        call.enqueue(new Callback<GetAddressApi>() {
            @Override
            public void onResponse(Call<GetAddressApi> call, Response<GetAddressApi> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 200) {
                        ArrayList<GetAddressApi.Datum> arrayList = new ArrayList<>();
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            arrayList.add(response.body().getData().get(i));
                            setData(arrayList);
                        }
                    } else {
                        Snack.snackbar(getActivity(), response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<GetAddressApi> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }

    //set data into recyclerView
    private void setData(final ArrayList<GetAddressApi.Datum> arrayList) {
        addressAdapter = new AddressAdapter(getContext(), arrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(addressAdapter);

        addressAdapter.setOnItemClick(new AddressAdapter.OnItemClick() {
            @Override
            public void onItemClick(int position, String addres) {
                address = addres;
            }

        });

    }

    @Override
    public void onAttach(Context context1) {
        super.onAttach(context1);
        context = context1;
    }
}
