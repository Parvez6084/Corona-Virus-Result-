package com.supernova.coronavirus.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.supernova.coronavirus.R;
import com.supernova.coronavirus.model.CountryModel;
import com.supernova.coronavirus.utility.MyPreferences;
import com.supernova.coronavirus.utility.Utility;
import com.supernova.coronavirus.viewModel.DashBoadViewModel;

import java.util.ArrayList;
import java.util.List;
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
    DashBoadViewModel dashBoadViewModel;
    private List<CountryModel> countryModels;
    private List<String> countryNames;
    private MyPreferences myPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).hide();
        dashBoadViewModel = ViewModelProviders.of(this).get(DashBoadViewModel.class);
        myPreferences = new MyPreferences(this);

        ProgressDialog pdialog = new ProgressDialog(this);
        pdialog.setTitle("Please wait");
        pdialog.setMessage("Take in process ...");
        pdialog.setCancelable(false);
        pdialog.show();

        dashBoadViewModel.countryModelLiveData().observe(this, countries -> {

            if (countries != null) {
                pdialog.dismiss();

                this.countryModels = countries;
                this.countryNames = new ArrayList<>();

                for (CountryModel c : countries) {
                    countryNames.add(c.getCountry());
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, countryNames);
                countryEt.setAdapter(arrayAdapter);

            }

            countryEt.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View arg1, int pos,
                                        long id) {


                }
            });

        });

    }

    @OnClick(R.id.select_button)
    public void onViewClicked() {
        myPreferences.cleanDataSharedPreferences();
        if (countryNames.contains(countryEt.getText().toString())) {
            myPreferences.setCountryName(countryEt.getText().toString());
            startActivity(new Intent(MainActivity.this, DashboadActivity.class));
        } else {
            countryEt.setError("Select a Country");
        }
    }
}
