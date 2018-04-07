package com.nyc.polymerse.network;

import com.nyc.polymerse.models.SuggestedLocationsResultsModel;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Wayne Kellman on 3/31/18.
 */

public interface PlacesApi {

    @GET("/places/v1/autosuggest")
    Call<SuggestedLocationsResultsModel> getSuggestedLocations(@Query("q") String Language,
                                                                     @Query("in") String Location,
                                                                     @Query("Accept-Language") String acceptedLanguage,
                                                                     @Query("app_id") String appId,
                                                                     @Query("app_code") String appCode);

}
