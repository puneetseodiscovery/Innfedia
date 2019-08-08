package com.mandy.innfedia.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mandy.innfedia.MainActivity;
import com.mandy.innfedia.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddAddressFragment extends Fragment {
    EditText edtName, edtPhone, edtPostcode, edtCity, edtState, edtFlat, edtNear;
    Button button;
    FragmentManager manager;
    View view;
    Context context;

    public AddAddressFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_address, container, false);


        init();
        MainActivity.textView.setText("Add Address");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.framelayout, new AddressFragment());
                transaction.commit();
            }
        });

        return view;
    }

    private void init() {

        edtName = (EditText) view.findViewById(R.id.edtName);
        edtPhone = (EditText) view.findViewById(R.id.edtMobile);
        edtPostcode = (EditText) view.findViewById(R.id.edtPostcode);
        edtCity = (EditText) view.findViewById(R.id.edtTown);
        edtState = (EditText) view.findViewById(R.id.edtState);
        edtFlat = (EditText) view.findViewById(R.id.edtFlat);
        edtNear = (EditText) view.findViewById(R.id.edtNear);
        button = (Button) view.findViewById(R.id.btnAdd);

        manager = getActivity().getSupportFragmentManager();


    }


    @Override
    public void onAttach(Context context1) {
        super.onAttach(context1);
        context = context1;
    }
}
