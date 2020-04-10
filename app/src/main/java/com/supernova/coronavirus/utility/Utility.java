package com.supernova.coronavirus.utility;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.supernova.coronavirus.R;
import com.supernova.coronavirus.model.CountryModel;

import java.util.List;
import java.util.Locale;

public class Utility {

    public static CountryModel searchItem(List<CountryModel> countries, String name) {

        for (CountryModel c : countries) {

            if (c.getCountry().trim().toLowerCase().equals(name.trim().toLowerCase())) {
                return c;
            }
        }

        return null;
    }

    public static void setLanguage(Context context, Locale locale){
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        context.getResources().updateConfiguration(configuration,
                context.getResources().getDisplayMetrics());
    }

    public static void setLanguageV2(Context context, String language){

        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config,context.getResources().getDisplayMetrics());
    }

    public static void snackbar(Context context,View view){
        Snackbar snackbar;
        snackbar = Snackbar.make(view, R.string.no_internet_warning, Snackbar.LENGTH_LONG);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(context.getResources().getColor(R.color.colorRed));
        TextView textView = snackBarView.findViewById(R.id.snackbar_text);
        textView.setTextColor(context.getResources().getColor(R.color.colorWhite));
        snackbar.show();
    }

}
