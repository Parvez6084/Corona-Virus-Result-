package com.supernova.coronavirus.utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.supernova.coronavirus.model.CountryModel;

public class MyPreferences {


    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public MyPreferences(Context context) {

        this.sharedPreferences = context.getSharedPreferences(Constant.USER, Context.MODE_PRIVATE);
    }

    public void setUserInformation(CountryModel user) {

        editor = sharedPreferences.edit();
        editor.putString(Constant.COUNTRY, user.getCountry());
        editor.apply();
    }

    public String getCountryName() {

        return sharedPreferences.getString(Constant.COUNTRY, "");
    }

    public void setCountryName(String name) {

        editor = sharedPreferences.edit();

        editor.putString(Constant.COUNTRY, name);
        editor.apply();
    }

    public void cleanDataSharedPreferences() {

        editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}
