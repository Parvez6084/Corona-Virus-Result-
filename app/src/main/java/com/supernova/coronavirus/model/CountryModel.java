package com.supernova.coronavirus.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountryModel {

    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("cases")
    @Expose
    private Float cases;
    @SerializedName("todayCases")
    @Expose
    private Float todayCases;
    @SerializedName("deaths")
    @Expose
    private Float deaths;
    @SerializedName("todayDeaths")
    @Expose
    private Float todayDeaths;
    @SerializedName("recovered")
    @Expose
    private Float recovered;
    @SerializedName("active")
    @Expose
    private Float active;
    @SerializedName("critical")
    @Expose
    private Float critical;
    @SerializedName("casesPerOneMillion")
    @Expose
    private Float casesPerOneMillion;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Float getCases() {
        return cases;
    }

    public void setCases(Float cases) {
        this.cases = cases;
    }

    public Float getTodayCases() {
        return todayCases;
    }

    public void setTodayCases(Float todayCases) {
        this.todayCases = todayCases;
    }

    public Float getDeaths() {
        return deaths;
    }

    public void setDeaths(Float deaths) {
        this.deaths = deaths;
    }

    public Float getTodayDeaths() {
        return todayDeaths;
    }

    public void setTodayDeaths(Float todayDeaths) {
        this.todayDeaths = todayDeaths;
    }

    public Float getRecovered() {
        return recovered;
    }

    public void setRecovered(Float recovered) {
        this.recovered = recovered;
    }

    public Float getActive() {
        return active;
    }

    public void setActive(Float active) {
        this.active = active;
    }

    public Float getCritical() {
        return critical;
    }

    public void setCritical(Float critical) {
        this.critical = critical;
    }

    public Float getCasesPerOneMillion() {
        return casesPerOneMillion;
    }

    public void setCasesPerOneMillion(Float casesPerOneMillion) {
        this.casesPerOneMillion = casesPerOneMillion;
    }

}