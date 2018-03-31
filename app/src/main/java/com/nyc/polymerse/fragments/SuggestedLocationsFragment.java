package com.nyc.polymerse.fragments;


import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.nyc.polymerse.Constants;
import com.nyc.polymerse.R;
import com.nyc.polymerse.models.SuggestedLocationModel;
import com.nyc.polymerse.User;
import com.nyc.polymerse.UserSingleton;
import com.nyc.polymerse.models.SuggestedLocationsResultsModel;
import com.nyc.polymerse.network.PlacesApi;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class SuggestedLocationsFragment extends Fragment {

    private static final String TAG = "SuggestedLocationsFrag";
    private User currentUser;
    private String userLearnLang;
    private String userShareLang;
    private List<SuggestedLocationModel> suggestedLocationModelList;
    private FusedLocationProviderClient mFusedLocationClient;
    private RecyclerView recyclerView;


    public SuggestedLocationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_suggested_locations, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        currentUser = UserSingleton.getInstance().getUser();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        Map<String, String> langLearnMap = currentUser.getLangLearn();
        Map<String, String> langShareMap = currentUser.getLangTeach();

        for (String s : langLearnMap.keySet()) {
            userLearnLang = s;
        }

        for (String s : langShareMap.keySet()) {
            userShareLang = s;
        }

        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1020);
        } else {

            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object
                                getSuggestedLocations(location);

                            }
                        }
                    });
        }



    }

    private void getSuggestedLocations(Location location) {
        Log.d(TAG, "getSuggestedLocations: ran");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://places.api.here.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PlacesApi placesService = retrofit.create(PlacesApi.class);
        Call<SuggestedLocationsResultsModel> suggestedLocations = placesService.getSuggestedLocations(
                userLearnLang,
                location.getLatitude() + "," +location.getLongitude() + ";" + "r=7768",
                "en-US,en;q=0.9",
                Constants.APP_ID,
                Constants.APP_CODE);
        suggestedLocations.enqueue(new Callback<SuggestedLocationsResultsModel>() {
            @Override
            public void onResponse(Call<SuggestedLocationsResultsModel> call, Response<SuggestedLocationsResultsModel> response) {
                SuggestedLocationsResultsModel resultsModel = response.body();
                suggestedLocationModelList = resultsModel.getResults();
                if (suggestedLocationModelList!=null) {
                    Log.d(TAG, "onResponse: suggestedLocationModelList size " + suggestedLocationModelList.size());
                    for (SuggestedLocationModel s : suggestedLocationModelList) {
                        Log.d(TAG, "onResponse: " + s.getTitle());
                    }
                }
            }

            @Override
            public void onFailure(Call<SuggestedLocationsResultsModel> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
