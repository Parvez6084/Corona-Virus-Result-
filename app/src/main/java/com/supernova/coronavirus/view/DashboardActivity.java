package com.supernova.coronavirus.view;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import com.androidstudy.networkmanager.Monitor;
import com.androidstudy.networkmanager.Tovuti;
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
import com.google.android.material.snackbar.Snackbar;
import com.supernova.coronavirus.R;
import com.supernova.coronavirus.model.CountryModel;
import com.supernova.coronavirus.utility.MyPreferences;
import com.supernova.coronavirus.utility.Utility;
import com.supernova.coronavirus.viewModel.DashBoardViewModel;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DashboardActivity extends AppCompatActivity {

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
    @BindView(R.id.country_date_tv)
    TextView countryDatetv;
    DashBoardViewModel dashBoardViewModel;
    @BindView(R.id.cardCancelButton)
    ImageView cardCancelButton;
    @BindView(R.id.dashboardActivity)
    RelativeLayout dashboardActivity;


    private float totalConfirm = 0, totalRecovery = 0, totalDeaths = 0;
    private float cTotalConfirm = 0, cTotalRecovery = 0, cTotalDeaths = 0;
    private float todayActive = 0, todayTotalConfirm = 0, todayTotalDeaths = 0;

    private long updateTime;
    private List<CountryModel> countryModels;
    public List<String> countryNames;
    private MyPreferences myPreferences;

    private static final String BANGLA_LOCALE = "bn";
    private static final String ENGLISH_LOCALE = "en_US";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).hide();
        dashBoardViewModel = ViewModelProviders.of(this).get(DashBoardViewModel.class);
        myPreferences = new MyPreferences(this);
        countryCardView.setVisibility(View.GONE);

        if (myPreferences.getDefaultLanguage().equals(BANGLA_LOCALE)) {
            Utility.setLanguageV2(this, BANGLA_LOCALE);

        } else {
            Utility.setLanguageV2(this, ENGLISH_LOCALE);

        }


        Tovuti.from(this).monitor((connectionType, isConnected, isFast) -> {

            if (isConnected) {
                ProgressDialog dialog = new ProgressDialog(DashboardActivity.this);
                dialog.setTitle(getString(R.string.dialog_title));
                dialog.setMessage(getString(R.string.dialog_boady));
                dialog.setCancelable(false);
                dialog.show();

                dashBoardViewModel.dataModelLiveData().observe(DashboardActivity.this, allDataModel -> {

                    if (allDataModel != null) {

                        dialog.dismiss();

                        totalConfirm = allDataModel.getCases();
                        totalRecovery = allDataModel.getRecovered();
                        totalDeaths = allDataModel.getDeaths();
                        updateTime = allDataModel.getUpdated();

                        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
                        String dateString = formatter.format(new Date(Long.parseLong(String.valueOf(updateTime))));
                        String date = "Update on  " + dateString;
                        int dataLan = date.length();
                        SpannableString spannableString = new SpannableString(date);
                        ForegroundColorSpan colorWhitesText = new ForegroundColorSpan(Color.WHITE);
                        spannableString.setSpan(colorWhitesText, 11, dataLan, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                        dateTv.setText(spannableString);
                        countryDatetv.setText(spannableString);
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
                    dashBoardViewModel.countryModelLiveData().observe(DashboardActivity.this, countries -> {

                        if (countries != null) {
                            dialog.dismiss();
                            DashboardActivity.this.countryModels = countries;
                            DashboardActivity.this.countryNames = new ArrayList<>();
                            for (CountryModel c : countries) {
                                countryNames.add(c.getCountry());
                            }

                            String searchName = myPreferences.getCountryName();
                            CountryModel country = Utility.searchItem(countryModels, searchName);
                            if (countryNames != null) {

                                assert country != null;
                                String countryName = country.getCountry();
                                cTotalConfirm = country.getCases();
                                todayTotalConfirm = country.getTodayCases();
                                cTotalRecovery = country.getRecovered();
                                todayActive = country.getActive();
                                cTotalDeaths = country.getDeaths();
                                todayTotalDeaths = country.getTodayDeaths();

                                countryNameTv.setText(countryName +" "+ getResources().getString(R.string.corona));
                                countryTotalConfirmedTv.setText(Math.round(cTotalConfirm) + "");
                                countryTotalDeathsTv.setText(Math.round(cTotalDeaths) + "");
                                countryTotalRecoveryTv.setText(Math.round(cTotalRecovery) + "");
                                countryNewConformedTv.setText(Math.round(todayTotalConfirm) + "");
                                countryActiveCasesTv.setText(Math.round(todayActive) + "");
                                countryNewDeathsTv.setText(Math.round(todayTotalDeaths) + "");

                                chart(countryDynamicArcView);
                                ArrayList<PieEntry> countryList = new ArrayList<>();

                                countryList.add(new PieEntry(cTotalRecovery, ""));
                                countryList.add(new PieEntry(cTotalConfirm, ""));
                                countryList.add(new PieEntry(cTotalDeaths, ""));

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

            } else {
                Utility.snackbar(DashboardActivity.this, dashboardActivity);
            }

        });

    }

    public static String nFormate(double d) {
        NumberFormat nf = NumberFormat.getInstance(Locale.CHINA);
        nf.setMaximumFractionDigits(10);
        String st = nf.format(d);
        return st;
    }

    @Override
    protected void onStop() {
        Tovuti.from(this).stop();
        super.onStop();
    }

    private void chart(PieChart pieChart) {

        pieChart.setUsePercentValues(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(0, 0, 0, 0);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(false);
        pieChart.setHoleColor(getResources().getColor(R.color.colorSpaceGray));
        pieChart.setTransparentCircleRadius(0f);
        pieChart.animateY(1400, Easing.EaseInOutQuad);
        pieChart.setHoleRadius(72f);
        pieChart.setDrawCenterText(true);
        pieChart.setRotationAngle(30);
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);
        pieChart.getLegend().setEnabled(false);
    }

    @OnClick({R.id.cardCancelButton, R.id.floating_action_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cardCancelButton:
                myPreferences.setCountryName("");
                countryCardView.setVisibility(View.GONE);
                break;
            case R.id.floating_action_button:
                startActivity(new Intent(DashboardActivity.this, MainActivity.class));
                break;
        }
    }


}
