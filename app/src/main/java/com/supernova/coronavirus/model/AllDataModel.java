package com.supernova.coronavirus.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllDataModel {

    @SerializedName("cases")
    @Expose
    private Float cases;
    @SerializedName("deaths")
    @Expose
    private Float deaths;
    @SerializedName("recovered")
    @Expose
    private Float recovered;
    @SerializedName("updated")
    @Expose
    private Long updated;

    public Float getCases() {
        return cases;
    }

    public void setCases(Float cases) {
        this.cases = cases;
    }

    public Float getDeaths() {
        return deaths;
    }

    public void setDeaths(Float deaths) {
        this.deaths = deaths;
    }

    public Float getRecovered() {
        return recovered;
    }

    public void setRecovered(Float recovered) {
        this.recovered = recovered;
    }

    public Long getUpdated() {
        return updated;
    }

    public void setUpdated(Long updated) {
        this.updated = updated;
    }

}