package com.cotemig.sharedepreferences3d2_ii.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SecurityPreferences {
    private final SharedPreferences mSharedPreferences;

    public SecurityPreferences (Context context){
        this.mSharedPreferences = context.getSharedPreferences("3D2II",Context.MODE_PRIVATE);
    }
    public void storeString(String key, String valor){
        this.mSharedPreferences.edit().putString(key,valor).apply();
    }
    public String getStoreString(String key){
        return this.mSharedPreferences.getString(key,"Não Localizado.");
    }
    public void RemoveString(String key){
        this.mSharedPreferences.edit().remove(key).apply();
    }

}
