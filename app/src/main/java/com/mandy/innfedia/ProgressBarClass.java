package com.mandy.innfedia;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.view.Window;

import com.wang.avi.AVLoadingIndicatorView;

public class ProgressBarClass {

    public static Dialog showProgressDialog(Context context) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setCancelable(false);

        dialog.setContentView(R.layout.custom_progress);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        AVLoadingIndicatorView  avLoadingIndicatorView = dialog.findViewById(R.id.avi);

        dialog.show();


        return dialog;
    }

}
