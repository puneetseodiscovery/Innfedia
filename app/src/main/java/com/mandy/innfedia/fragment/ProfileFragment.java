package com.mandy.innfedia.fragment;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.makeramen.roundedimageview.RoundedImageView;
import com.mandy.innfedia.MainActivity;
import com.mandy.innfedia.R;
import com.zfdang.multiple_images_selector.ImagesSelectorActivity;
import com.zfdang.multiple_images_selector.SelectorSettings;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private final int RESULT_LOAD_IMAGE = 12;
    Button button, btnEdit;
    View view;
    EditText edtName, edtEmail, edtPhone;
    RoundedImageView roundedImageView;
    ArrayList<String> image_uris = new ArrayList<>();

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        init();
        MainActivity.textView.setText("My Profile");
        Fresco.initialize(getActivity());


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtEmail.setFocusable(false);
                edtEmail.setClickable(false);
                edtEmail.setFocusableInTouchMode(false);

                edtName.setFocusable(false);
                edtName.setClickable(false);
                edtName.setFocusableInTouchMode(false);
                edtPhone.setFocusable(false);
                edtPhone.setClickable(false);
                edtPhone.setFocusableInTouchMode(false);

                Toast.makeText(getContext(), "Update successful", Toast.LENGTH_SHORT).show();

                btnEdit.setVisibility(View.VISIBLE);
                button.setVisibility(View.GONE);
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtEmail.setFocusable(true);
                edtEmail.setClickable(true);
                edtEmail.setFocusableInTouchMode(true);
                edtName.setFocusable(true);
                edtName.setClickable(true);
                edtName.setFocusableInTouchMode(true);
                edtPhone.setFocusable(true);
                edtPhone.setClickable(true);
                edtPhone.setFocusableInTouchMode(true);

                button.setVisibility(View.VISIBLE);
                btnEdit.setVisibility(View.GONE);

                edtName.requestFocus();

            }
        });


        roundedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // start multiple photos selector
                Intent intent = new Intent(getContext(), ImagesSelectorActivity.class);
                intent.putExtra(SelectorSettings.SELECTOR_MAX_IMAGE_NUMBER, 1);
                //  intent.putExtra(SelectorSettings.SELECTOR_MIN_IMAGE_SIZE, 100000);

                intent.putExtra(SelectorSettings.SELECTOR_SHOW_CAMERA, true);
                intent.putStringArrayListExtra(SelectorSettings.SELECTOR_INITIAL_SELECTED_LIST, image_uris);
                // start the selector
                startActivityForResult(intent, RESULT_LOAD_IMAGE);
            }
        });

        return view;
    }

    private void init() {
        button = (Button) view.findViewById(R.id.btnProfile);
        edtName = (EditText) view.findViewById(R.id.edtName);
        edtEmail = (EditText) view.findViewById(R.id.edtEmail);
        edtPhone = (EditText) view.findViewById(R.id.edtPhone);
        btnEdit = (Button) view.findViewById(R.id.btnEdit);
        roundedImageView = (RoundedImageView) view.findViewById(R.id.imageView);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_LOAD_IMAGE) {
            image_uris.clear();
            if (resultCode == RESULT_OK) {
                image_uris = data.getStringArrayListExtra(SelectorSettings.SELECTOR_RESULTS);
                assert image_uris != null;
                for (int i = 0; i < image_uris.size(); i++) {
                    roundedImageView.setImageURI(Uri.parse(image_uris.get(i)));
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
