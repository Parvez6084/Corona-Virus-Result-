package com.supernova.coronavirus.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.format.DateFormat;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProviders;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.supernova.coronavirus.R;
import com.supernova.coronavirus.model.CountryModel;
import com.supernova.coronavirus.utility.MyPreferences;
import com.supernova.coronavirus.utility.Utility;
import com.supernova.coronavirus.viewModel.DashBoadViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DashboadActivity extends AppCompatActivity {

    @BindView(R.id.country_name_tv)
    TextView countryNameTv;
    @BindView(R.id.country_dynamicArcView)
    PieChart countryDynamicArcView;
    @BindView(R.id.country_new_conformed_tv)
    TextView countryNewConformedTv;
    @BindView(R.id.country_new_deaths_tv)
    TextView countryNewDeathsTv;
    @BindView(R.id.country_active_cases_tv)
    TextView countryActiveCasesTv;
    @BindView(R.id.country_total_confirmed_tv)
    TextView countryTotalConfirmedTv;
    @BindView(R.id.country_total_deaths_tv)
    TextView countryTotalDeathsTv;
    @BindView(R.id.country_total_recovery_tv)
    TextView countryTotalRecoveryTv;
    @BindView(R.id.country_cardView)
    CardView countryCardView;
    @BindView(R.id.world_dynamicArcView)
    PieChart worldDynamicArcView;
    @BindView(R.id.world_total_confirmed_tv)
    TextView worldTotalConfirmedTv;
    @BindView(R.id.world_total_deaths_tv)
    TextView worldTotalDeathsTv;
    @BindView(R.id.world_total_recovery_tv)
    TextView worldTotalRecoveryTv;
    @BindView(R.id.world_cardView)
    CardView worldCardView;
    @BindView(R.id.floating_action_button)
    FloatingActionButton floatingActionButton;
    @BindView(R.id.date_tv)
    TextView dateTv;
    DashBoadViewModel dashBoadViewModel;
    @BindView(R.id.cardCancleButton)
    ImageView cardCancleButton;
    private float totalConfirm = 0, totalRecovery = 0, totalDeaths = 0;
    private float ctotalConfirm = 0, ctotalRecovery = 0, ctotalDeaths = 0;
    private float todayActive = 0, todaytotalConfirm = 0, todaytotalDeaths = 0;

    private long updateTime;
    private List<CountryModel> countryModels;
    private List<String> countryNames;
    private MyPreferences myPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboad);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).hide();
        dashBoadViewModel = ViewModelProviders.of(this).get(DashBoadViewModel.class);
        myPreferences = new MyPreferences(this);
        countryCardView.setVisibility(View.GONE);

        ProgressDialog pdialog = new ProgressDialog(this);
        pdialog.setTitle("Please wait");
        pdialog.setMessage("Take in process ...");
        pdialog.setCancelable(false);
        pdialog.show();

        dashBoadViewModel.dataModelLiveData().observe(this, allDataModel -> {

            if (allDataModel != null) {

                pdialog.dismiss();

                totalConfirm = allDataModel.getCases();
                totalRecovery = allDataModel.getRecovered();
                totalDeaths = allDataModel.getDeaths();
                updateTime = allDataModel.getUpdated();

                SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
                String dateString = formatter.format(new Date(Long.parseLong(String.valueOf(updateTime))));
                String date = "Update on  " + dateString;

                SpannableString spannableString = new SpannableString(date);
                ForegroundColorSpan colorWhitesText = new ForegroundColorSpan(Color.WHITE);
                spannableString.setSpan(colorWhitesText,11,23, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                dateTv.setText(spannableString);
                worldTotalConfirmedTv.setText(Math.round(totalConfirm) + "");
                worldTotalDeathsTv.setText(Math.round(totalDeaths) + "");
                worldTotalRecoveryTv.setText(Math.round(totalRecovery) + "");

                chart(worldDynamicArcView);
                ArrayList<PieEntry> worldList = new ArrayList<>();
                worldList.add(new PieEntry(totalRecovery, ""));
                worldList.add(new PieEntry(totalConfirm, ""));
                worldList.add(new PieEntry(totalDeaths, ""));
                PieDataSet pieDataSet = new PieDataSet(worldList, "");
                pieDataSet.setSliceSpace(0f);
                pieDataSet.setSelectionShift(3f);
                pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                PieData data = new PieData((pieDataSet));
                data.setValueTextSize(10f);
                data.setValueTextColor(Color.WHITE);

                worldDynamicArcView.setData(data);
                worldDynamicArcView.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                    @Override
                    public void onValueSelected(Entry e, Highlight h) {
                        worldDynamicArcView.setUsePercentValues(false);
                        worldDynamicArcView.setDrawHoleEnabled(false);
                    }

                    @Override
                    public void onNothingSelected() {
                        worldDynamicArcView.setUsePercentValues(true);
                        worldDynamicArcView.setDrawHoleEnabled(true);

                    }
                });
            }
        });

        if (myPreferences.getCountryName().equals("")) {
            countryCardView.setVisibility(View.GONE);
        } else {
            countryCardView.setVisibility(View.VISIBLE);
            dashBoadViewModel.countryModelLiveData().observe(this, countries -> {

                if (countries != null) {
                    pdialog.dismiss();
                    this.countryModels = countries;
                    this.countryNames = new ArrayList<>();
                    for (CountryModel c : countries) {
                        countryNames.add(c.getCountry());
                    }

                    String searchName = myPreferences.getCountryName();
                    CountryModel country = Utility.searchItem(countryModels, searchName);
                    if (countryNames != null) {

                        String countryName = country.getCountry();
                        ctotalConfirm = country.getCases();
                        todaytotalConfirm = country.getTodayCases();
                        ctotalRecovery = country.getRecovered();
                        todayActive = country.getActive();
                        ctotalDeaths = country.getDeaths();
                        todaytotalDeaths = country.getTodayDeaths();

                        countryNameTv.setText(countryName + " CORONA VIRUS");
                        countryTotalConfirmedTv.setText(Math.round(ctotalConfirm) + "");
                        countryTotalDeathsTv.setText(Math.round(ctotalDeaths) + "");
                        countryTotalRecoveryTv.setText(Math.round(ctotalRecovery) + "");
                        countryNewConformedTv.setText(Math.round(todaytotalConfirm) + "");
                        countryActiveCasesTv.setText(Math.round(todayActive) + "");
                        countryNewDeathsTv.setText(Math.round(todaytotalDeaths) + "");

                        chart(countryDynamicArcView);
                        ArrayList<PieEntry> countryList = new ArrayList<>();

                        countryList.add(new PieEntry(ctotalRecovery, ""));
                        countryList.add(new PieEntry(ctotalConfirm, ""));
                        countryList.add(new PieEntry(ctotalDeaths, ""));

                        PieDataSet pieDataSet = new PieDataSet(countryList, "");
                        pieDataSet.setSliceSpace(0f);
                        pieDataSet.setSelectionShift(3f);
                        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                        PieData data = new PieData((pieDataSet));
                        data.setValueTextSize(10f);
                        data.setValueTextColor(Color.WHITE);

                        countryDynamicArcView.setData(data);
                        countryDynamicArcView.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                            @Override
                            public void onValueSelected(Entry e, Highlight h) {
                                countryDynamicArcView.setUsePercentValues(false);
                                countryDynamicArcView.setDrawHoleEnabled(false);
                            }

                            @Override
                            public void onNothingSelected() {
                                countryDynamicArcView.setUsePercentValues(true);
                                countryDynamicArcView.setDrawHoleEnabled(true);

                            }

                        });

                    }

                }
            });
        }



    }


    private void chart(PieChart pieChart) {

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(0, 0, 0, 0);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(getResources().getColor(R.color.colorSpeceGray));
        pieChart.setTransparentCircleRadius(0f);
        pieChart.animateY(1400, Easing.EaseInOutQuad);
        pieChart.setHoleRadius(72f);
        pieChart.setDrawCenterText(true);
        pieChart.setRotationAngle(30);
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);
        pieChart.getLegend().setEnabled(false);
    }

    @OnClick({R.id.cardCancleButton, R.id.floating_action_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cardCancleButton:
                myPreferences.cleanDataSharedPreferences();
                countryCardView.setVisibility(View.GONE);
                break;
            case R.id.floating_action_button:
                startActivity(new Intent(DashboadActivity.this, MainActivity.class));
                break;
        }
    }
}
