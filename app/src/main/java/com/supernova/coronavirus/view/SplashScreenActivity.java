package com.supernova.coronavirus.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.supernova.coronavirus.R;
import com.supernova.coronavirus.utility.MyPreferences;
import com.supernova.coronavirus.utility.Utility;

import java.util.Locale;
import java.util.Objects;

import butterknife.ButterKnife;

public class SplashScreenActivity extends AppCompatActivity {

    private static final String BANGLA_LOCALE = "bn";
    private static final String ENGLISH_LOCALE = "en_US";
    private MyPreferences myPreferences;
    private Locale locale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).hide();
        myPreferences = new MyPreferences(this);
        new Handler().postDelayed(() -> {

            if (myPreferences.getDefaultLanguage().equals(BANGLA_LOCALE)) {
                locale = new Locale(BANGLA_LOCALE);
                Utility.setLanguage(this, locale);
                recreate();
            } else {
                locale = new Locale(ENGLISH_LOCALE);
                Utility.setLanguage(this, locale);
                recreate();
            }

                Intent i = new Intent(SplashScreenActivity.this, DashboardActivity.class);
                startActivity(i);
                finish();

        }, 3000);

    }
}
