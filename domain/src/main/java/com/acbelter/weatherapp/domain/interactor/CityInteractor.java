package com.acbelter.weatherapp.domain.interactor;

import com.acbelter.weatherapp.domain.model.city.AutocompleteData;
import com.acbelter.weatherapp.domain.model.city.CityData;
import com.acbelter.weatherapp.domain.model.city.CityParams;
import com.acbelter.weatherapp.domain.repository.CityRepo;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.Single;

public class CityInteractor {

    private final CityRepo cityRepo;
    private final Scheduler schedulerIO;
    private final Scheduler schedulerMain;

    public CityInteractor(CityRepo cityRepo, Scheduler schedulerIO, Scheduler schedulerMain) {
        this.cityRepo = cityRepo;
        this.schedulerIO = schedulerIO;
        this.schedulerMain = schedulerMain;
    }

    public Single<List<AutocompleteData>> getCityList(CityParams cityParams) {
        return cityRepo.getCityList(cityParams)
                .subscribeOn(schedulerIO)
                .observeOn(schedulerMain);
    }

    public Flowable<List<CityData>> getFavorites() {
        return cityRepo.getFavoritesCities()
                .subscribeOn(schedulerIO)
                .observeOn(schedulerMain);
    }

    public Single<CityData> getCityData(AutocompleteData autocompleteData) {
        return cityRepo.getCityData(autocompleteData)
                .subscribeOn(schedulerIO)
                .observeOn(schedulerMain);
    }

    public void saveSelectedCity(CityData cityData) {
        cityRepo.saveCity(cityData);
    }
}
