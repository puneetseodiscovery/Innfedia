package com.mandy.innfedia.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.view.Window;

import com.mandy.innfedia.R;
import com.wang.avi.AVLoadingIndicatorView;

public class ProgressBarClass {

    public static Dialog showProgressDialog(Context context) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setCancelable(false);

        dialog.setContentView(R.layout.custom_progress);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        dialog.show();

        return dialog;
    }

}
