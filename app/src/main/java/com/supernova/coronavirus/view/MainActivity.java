package com.supernova.coronavirus.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.supernova.coronavirus.R;
import com.supernova.coronavirus.model.CountryModel;
import com.supernova.coronavirus.utility.MyPreferences;
import com.supernova.coronavirus.viewModel.DashBoardViewModel;

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
    DashBoardViewModel dashBoardViewModel;
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

        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle(getString(R.string.dialog_title));
        dialog.setMessage(getString(R.string.dialog_boady));
        dialog.setCancelable(false);
        dialog.show();

        dashBoardViewModel.countryModelLiveData().observe(this, countries -> {

            if (countries != null) {
                dialog.dismiss();

                this.countryNames = new ArrayList<>();

                for (CountryModel c : countries) {
                    countryNames.add(c.getCountry());
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, countryNames);
                countryEt.setAdapter(arrayAdapter);
            }

            countryEt.setOnItemClickListener((parent, arg1, pos, id) -> {

            });

        });

    }

    @OnClick(R.id.select_button)
    public void onViewClicked() {
        myPreferences.cleanDataSharedPreferences();
        if (countryNames.contains(countryEt.getText().toString())) {
            myPreferences.setCountryName(countryEt.getText().toString());
            startActivity(new Intent(MainActivity.this, DashboardActivity.class));
        } else {
            countryEt.setError(getString(R.string.edit_text_null_warning));
        }
    }
}
