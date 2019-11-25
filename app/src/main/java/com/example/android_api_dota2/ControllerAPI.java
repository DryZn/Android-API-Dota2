package com.example.android_api_dota2;

import android.content.SharedPreferences;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ControllerAPI implements Callback<List<Heroes>>{
    static final String BASE_URL = "https://api.opendota.com/api/";
    private MainActivity view;
    private SharedPreferences sharedPreferences;

    public ControllerAPI(MainActivity view, SharedPreferences sharedPreferences) {
        this.view = view;
        this.sharedPreferences = sharedPreferences;
    }

    public void start() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        DotaAPI smashAPI = retrofit.create(DotaAPI.class);

        Call<List<Heroes>> call = smashAPI.loadChanges();
        call.enqueue(this);

    }

    @Override
    public void onResponse(Call<List<Heroes>> call, Response<List<Heroes>> response) {
        if(response.isSuccessful()) {
            List<Heroes> changesList = response.body();
            view.initRecycler(changesList);
            save(changesList);
        } else {
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<List<Heroes>> call, Throwable t) {
        t.printStackTrace();

        //Au cas ou le téléphone n est pas encore connecté a internet, on réessaie jusqu'à l'être
        List<Heroes> changesList = getSave();
        view.initRecycler(changesList);
    }

    private void save(List<Heroes> changesList) {
        String changesListString = new Gson().toJson(changesList);
        sharedPreferences
                .edit()
                .putString("liste", changesListString)
                .apply();

    }
    //inverse de save
    private List<Heroes> getSave() {
        String changesListString = sharedPreferences.getString("liste", "");
        Type changeListType = new TypeToken<List<Heroes>>(){}.getType();
        List<Heroes> changesList = new Gson().fromJson(changesListString, changeListType);
        return changesList;
    }
}
