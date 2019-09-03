package com.mandy.innfedia.addressActivity.addAddress;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mandy.innfedia.commonActivity.NoInternetActivity;
import com.mandy.innfedia.addressActivity.AddressActivity;
import com.mandy.innfedia.controller.Controller;
import com.mandy.innfedia.GetMeesageApi;
import com.mandy.innfedia.R;
import com.mandy.innfedia.utils.CheckInternet;
import com.mandy.innfedia.utils.ProgressBarClass;
import com.mandy.innfedia.utils.SharedToken;
import com.mandy.innfedia.utils.Snack;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;

public class ADDAddressActivity extends AppCompatActivity implements Controller.ADDAddress {

    SharedToken sharedToken;
    String name, mobile, postcode, town, state, flat, near;
    Controller controller;
    Dialog dialog;

    @BindView(R.id.tooolbar)
    Toolbar toolbar;
    @BindView(R.id.textView)
    TextView textView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addaddress);
        ButterKnife.bind(this);
        sharedToken = new SharedToken(this);
        controller = new Controller(this);
        dialog = ProgressBarClass.showProgressDialog(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        textView.setText("Add Address");

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
        } else if (mobile.length() != 10) {
            edtMobile.setError("Enter 10 digit mobile number");
            edtMobile.requestFocus();
        } else {
            if (CheckInternet.isInternetAvailable(this)) {
                dialog.show();
                controller.setAddAddress("Bearer " + sharedToken.getShared(), name, mobile, postcode, town, state, flat, near);
            } else {
                startActivity(new Intent(this, NoInternetActivity.class));
            }
        }

    }

    //check validation for edit text
    private void getTextError(final EditText editText) {
        if (TextUtils.isEmpty(editText.getText().toString()) || editText.getText().toString().length() > 30) {
            editText.setError("Enter this field");
        }

    }


    @Override
    public void onSucess(Response<GetMeesageApi> response) {
        dialog.dismiss();
        if (response.body().getStatus() == 200) {
            Intent intent = new Intent(ADDAddressActivity.this, AddressActivity.class);
            intent.putExtra("Cid", getIntent().getStringExtra("Cid"));
            startActivity(intent);
        } else {
            Snack.snackbar(ADDAddressActivity.this, response.body().getMessage());
        }
    }

    @Override
    public void error(String error) {
        dialog.dismiss();
        Snack.snackbar(ADDAddressActivity.this, error);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
