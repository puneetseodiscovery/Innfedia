package com.mandy.innfedia.address.addAddress;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.mandy.innfedia.Activities.NoInternetActivity;
import com.mandy.innfedia.Retrofit.ApiInterface;
import com.mandy.innfedia.ApiModel.GetMeesageApi;
import com.mandy.innfedia.MainActivity;
import com.mandy.innfedia.R;
import com.mandy.innfedia.Retrofit.ServiceGenerator;
import com.mandy.innfedia.Utils.CheckInternet;
import com.mandy.innfedia.Utils.ProgressBarClass;
import com.mandy.innfedia.Utils.SharedToken;
import com.mandy.innfedia.Utils.Snack;
import com.mandy.innfedia.address.AddressFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddAddressFragment extends Fragment {
    FragmentManager manager;
    View view;
    Context context;
    SharedToken sharedToken;
    String name, mobile, postcode, town, state, flat, near;

    @BindView(R.id.edtName)
    EditText edtName;
    @BindView(R.id.edtMobile)
    EditText edtMobile;
    @BindView(R.id.edtPostcode)
    EditText edtPostcode;
    @BindView(R.id.edtTown)
    EditText edtTown;
    @BindView(R.id.edtState)
    EditText edtState;
    @BindView(R.id.edtFlat)
    EditText edtFlat;
    @BindView(R.id.edtNear)
    EditText edtNear;
    @BindView(R.id.btnAdd)
    Button btnAdd;
    Unbinder unbinder;

    public AddAddressFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_address, container, false);
        unbinder = ButterKnife.bind(this, view);


        sharedToken = new SharedToken(context);

        manager = getActivity().getSupportFragmentManager();
        MainActivity.textView.setText("Add Address");


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnAdd)
    public void onViewClicked() {
        name = edtName.getText().toString();
        mobile = edtMobile.getText().toString();
        postcode = edtPostcode.getText().toString();
        town = edtTown.getText().toString();
        state = edtState.getText().toString();
        flat = edtFlat.getText().toString();
        near = edtNear.getText().toString();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(mobile) || TextUtils.isEmpty(postcode) || TextUtils.isEmpty(town) || TextUtils.isEmpty(state) || TextUtils.isEmpty(flat)
                || TextUtils.isEmpty(near)) {
            getTextError(edtName);
            getTextError(edtMobile);
            getTextError(edtPostcode);
            getTextError(edtTown);
            getTextError(edtState);
            getTextError(edtFlat);
            getTextError(edtNear);
        } else if (mobile.length() != 9) {
            edtMobile.setError("Enter 10 digit mobile number");
            edtMobile.requestFocus();
        } else {
            if (CheckInternet.isInternetAvailable(context)) {
                addAddress();
            } else {
                context.startActivity(new Intent(context, NoInternetActivity.class));
            }
        }


    }


    //check validation for edit text
    private void getTextError(final EditText editText) {
        if (TextUtils.isEmpty(editText.getText().toString()) || editText.getText().toString().length() > 30) {
            editText.setError("Enter this field");
        }

    }

    // private set data
    private void addAddress() {
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<GetMeesageApi> call = apiInterface.addAddress("Bearer " + sharedToken.getShared(), name, mobile, postcode, town, state, flat, near);
        final Dialog dialog = ProgressBarClass.showProgressDialog(context);
        dialog.show();

        call.enqueue(new Callback<GetMeesageApi>() {
            @Override
            public void onResponse(Call<GetMeesageApi> call, Response<GetMeesageApi> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 200) {
                        Snack.snackbar(getActivity(), response.body().getMessage());
                        FragmentTransaction transaction = manager.beginTransaction();
                        transaction.replace(R.id.framelayout, new AddressFragment());
                        transaction.commit();
                    } else {
                        Snack.snackbar(getActivity(), response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<GetMeesageApi> call, Throwable t) {
                dialog.dismiss();
                Snack.snackbar(getActivity(), t.getMessage());
            }
        });

    }


    @Override
    public void onAttach(Context context1) {
        super.onAttach(context1);
        context = context1;
    }


}
