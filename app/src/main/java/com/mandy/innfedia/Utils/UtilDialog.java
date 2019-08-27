package com.mandy.innfedia.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mandy.innfedia.Activities.LoginActivity;
import com.mandy.innfedia.R;
import com.mandy.innfedia.Utils.SharedToken;

public class UtilDialog {

    public static Dialog dialog(final Context context, String titel, String button) {
        final Dialog dialog = new Dialog(context);

        dialog.setContentView(R.layout.custom_dialog);
        dialog.setCancelable(false);

        TextView textView;
        final Button btnCancel, btnLogout;

        textView = dialog.findViewById(R.id.txtTitle);
        btnCancel = dialog.findViewById(R.id.btnCancel);
        btnLogout = dialog.findViewById(R.id.btnLogout);

        //set the title
        textView.setText(titel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        btnLogout.setText(button);

        if (btnLogout.getText().equals("Logout")) {
            btnLogout.setBackgroundColor(context.getResources().getColor(R.color.yellow));
        } else {
            btnLogout.setBackgroundColor(context.getResources().getColor(R.color.red));
        }

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnLogout.getText().equals("Logout")) {
                    dialog.dismiss();
                    SharedToken sharedToken = new SharedToken(context);
                    sharedToken.clearShaerd();
                    context.startActivity(new Intent(context, LoginActivity.class));
                    ((Activity) context).finish();
                } else {
                    Toast.makeText(context, "Delete Successful", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });

        return dialog;
    }

}
