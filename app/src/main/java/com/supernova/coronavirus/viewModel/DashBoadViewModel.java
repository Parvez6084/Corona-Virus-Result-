package com.supernova.coronavirus.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.supernova.coronavirus.model.AllDataModel;
import com.supernova.coronavirus.model.CountryModel;
import com.supernova.coronavirus.model.DashBoardRepository;

import java.util.List;

public class DashBoadViewModel extends AndroidViewModel {

    private DashBoardRepository dashBoardRepository;

    public DashBoadViewModel(@NonNull Application application) {
        super(application);

        dashBoardRepository = new DashBoardRepository();
    }

    public LiveData<AllDataModel> dataModelLiveData() {
        return dashBoardRepository.getAllData();
    }

   public LiveData<List<CountryModel>> countryModelLiveData() {
        return dashBoardRepository.getCountryData();
    }


}