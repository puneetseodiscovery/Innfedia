package com.mandy.innfedia.Utils;


import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.widget.Toast;

public class Snack {
    public static Snackbar snackbar(Activity context, String string) {
        Snackbar snackbar = Snackbar.make(context.findViewById(android.R.id.content), string, Snackbar.LENGTH_LONG);
        snackbar.show();
        return snackbar;
    }
}
