package com.mandy.innfedia.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedToken {

    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public SharedToken(Context context) {
        this.context = context;

    }

    public void setSharedata(String token) {
        sharedPreferences = context.getSharedPreferences("User", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString("Token", token);
        editor.apply();
    }

    public String getShared() {
        sharedPreferences = context.getSharedPreferences("User", Context.MODE_PRIVATE);
        String data = sharedPreferences.getString("Token", "");
        return data;
    }

    public void clearShaerd() {
        sharedPreferences = context.getSharedPreferences("User", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();

    }


}
