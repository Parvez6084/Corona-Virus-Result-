package com.supernova.coronavirus.networkcall;


import com.supernova.coronavirus.model.AllDataModel;
import com.supernova.coronavirus.model.CountryModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;


public interface ApiInterface {


    @GET("all")
    Call<AllDataModel> getWorldAllData();

    @GET("countries")
    Call<List<CountryModel>> getCountryData();


}
