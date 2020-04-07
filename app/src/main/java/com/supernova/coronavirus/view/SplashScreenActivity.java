package com.supernova.coronavirus.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.supernova.coronavirus.R;
import com.supernova.coronavirus.utility.Connectivity;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashScreenActivity extends AppCompatActivity {

    @BindView(R.id.splashScreen)
    RelativeLayout splashScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).hide();

        new Handler().postDelayed(() -> {

            if ((Connectivity.isConnectedFast(this))) {

                Intent i = new Intent(SplashScreenActivity.this, DashboardActivity.class);
                startActivity(i);
                finish();
            } else {
                Snackbar snack = Snackbar.make(splashScreen, R.string.no_internet_warning, Snackbar.LENGTH_INDEFINITE);
                snack.show();
            }

        }, 3000);

    }
}
