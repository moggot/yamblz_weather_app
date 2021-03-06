package com.acbelter.weatherapp.data.network;

import android.support.annotation.WorkerThread;

import com.acbelter.weatherapp.data.placesmodel.Places;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.acbelter.weatherapp.data.BuildConfig.GOOGLE_PLACES_API_KEY;

public interface PlacesApi {

    String BASE_PLACES_URL = "https://maps.googleapis.com/maps/api/place/autocomplete/";

    @GET("json?types=(cities)&key=" + GOOGLE_PLACES_API_KEY)
    @WorkerThread
    Observable<Places> getPlaces(@Query("input") String input);
}
