package com.supernova.coronavirus.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.androidstudy.networkmanager.Tovuti;
import com.supernova.coronavirus.R;
import com.supernova.coronavirus.model.CountryModel;
import com.supernova.coronavirus.utility.MyPreferences;
import com.supernova.coronavirus.utility.Utility;
import com.supernova.coronavirus.viewModel.DashBoardViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.country_Et)
    AutoCompleteTextView countryEt;
    @BindView(R.id.select_button)
    Button selectButton;
    DashBoardViewModel dashBoardViewModel;
    @BindView(R.id.english_language)
    RadioButton englishLanguage;
    @BindView(R.id.bangla_language)
    RadioButton banglaLanguage;
    @BindView(R.id.mainActivity)
    RelativeLayout mainActivity;

    private static final String BANGLA_LOCALE = "bn";
    private static final String ENGLISH_LOCALE = "en_US";
    private Locale locale;


    private List<String> countryNames;
    private MyPreferences myPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).hide();
        dashBoardViewModel = ViewModelProviders.of(this).get(DashBoardViewModel.class);
        myPreferences = new MyPreferences(this);

        englishLanguage.setChecked(true);
        if (myPreferences.getDefaultLanguage().equals(BANGLA_LOCALE)) {
            banglaLanguage.setChecked(true);
            banglaLanguage.setEnabled(false);
        } else {
            englishLanguage.setChecked(true);
            englishLanguage.setEnabled(false);
        }

        Tovuti.from(this).monitor((connectionType, isConnected, isFast) -> {

            if (isConnected) {
                ProgressDialog dialog = new ProgressDialog(MainActivity.this);
                dialog.setTitle(getString(R.string.dialog_title));
                dialog.setMessage(getString(R.string.dialog_boady));
                dialog.setCancelable(false);
                dialog.show();

                dashBoardViewModel.countryModelLiveData().observe(MainActivity.this, countries -> {

                    if (countries != null) {
                        dialog.dismiss();

                        MainActivity.this.countryNames = new ArrayList<>();

                        for (CountryModel c : countries) {
                            countryNames.add(c.getCountry());
                        }
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, countryNames);
                        countryEt.setAdapter(arrayAdapter);
                    }

                    countryEt.setOnItemClickListener((parent, arg1, pos, id) -> {

                    });

                });
            } else {
                Utility.snackbar(MainActivity.this, mainActivity);
                countryEt.setKeyListener(null);
                selectButton.setEnabled(false);
            }

        });

    }

    @OnClick({R.id.select_button, R.id.english_language, R.id.bangla_language})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.select_button:
                if (countryNames.contains(countryEt.getText().toString())) {
                    myPreferences.setCountryName(countryEt.getText().toString());
                    startActivity(new Intent(MainActivity.this, DashboardActivity.class));
                } else {
                    countryEt.setError(getString(R.string.edit_text_null_warning));
                }
                break;
            case R.id.english_language:
                Toast.makeText(this, "Language English", Toast.LENGTH_SHORT).show();
                myPreferences.setDefaultLanguage(ENGLISH_LOCALE);
                locale = new Locale(ENGLISH_LOCALE);
                languageCall();
                break;
            case R.id.bangla_language:
                Toast.makeText(this, "ভাষা বাংলা", Toast.LENGTH_SHORT).show();
                myPreferences.setDefaultLanguage(BANGLA_LOCALE);
                locale = new Locale(BANGLA_LOCALE);
                languageCall();
                break;
        }
    }
    private void languageCall(){
        Utility.setLanguage(this,locale);
        startActivity(new Intent(MainActivity.this, DashboardActivity.class));
        recreate();
    }

}
