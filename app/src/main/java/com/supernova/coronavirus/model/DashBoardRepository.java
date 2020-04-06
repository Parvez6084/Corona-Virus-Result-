package com.supernova.coronavirus.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.supernova.coronavirus.networkcall.ApiClient;
import com.supernova.coronavirus.networkcall.ApiInterface;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashBoardRepository {


    private MutableLiveData<AllDataModel> allDataModelMutableLiveData;
    private MutableLiveData<List<CountryModel>> countryModelMutableLiveData;

    public MutableLiveData<AllDataModel> getAllData() {


        allDataModelMutableLiveData = new MutableLiveData<>();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<AllDataModel> call = apiInterface.getWorldAllData();
        call.enqueue(new Callback<AllDataModel>() {

            @Override
            public void onResponse(@NonNull Call<AllDataModel> call, @NonNull Response<AllDataModel> response) {

                AllDataModel allDataModel = response.body();
                allDataModelMutableLiveData.setValue(allDataModel);
            }

            @Override
            public void onFailure(@NonNull Call<AllDataModel> call, @NonNull Throwable t) {

                Log.d("Error", Objects.requireNonNull(t.getMessage()));
            }
        });

        return allDataModelMutableLiveData;
    }

    public MutableLiveData<List<CountryModel>> getCountryData() {


        countryModelMutableLiveData = new MutableLiveData<>();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<CountryModel>> call = apiInterface.getCountryData();
        call.enqueue(new Callback<List<CountryModel>>() {

            @Override
            public void onResponse(@NonNull Call<List<CountryModel>> call, @NonNull Response<List<CountryModel>> response) {

                List<CountryModel> countryModel = response.body();
                countryModelMutableLiveData.setValue(countryModel);
            }

            @Override
            public void onFailure(@NonNull Call<List<CountryModel>> call, @NonNull Throwable t) {

                Log.d("Error", Objects.requireNonNull(t.getMessage()));
            }
        });

        return countryModelMutableLiveData;
    }



}
