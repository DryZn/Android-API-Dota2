package com.example.android_api_dota2;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;

import java.lang.reflect.Type;
import java.util.ArrayList;
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
    String dataName;

    public ControllerAPI(MainActivity view, SharedPreferences sharedPreferences, String dataName) {
        this.view = view;
        this.sharedPreferences = sharedPreferences;
        this.dataName = dataName;
    }

    public void start() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        DotaAPI dotaAPI = retrofit.create(DotaAPI.class);

        Call<List<Heroes>> call = dotaAPI.getHeroes();
        call.enqueue(this);

    }

    @Override
    public void onResponse(Call<List<Heroes>> call, Response<List<Heroes>> response) {
        if(response.isSuccessful()) {
            System.out.println("ici ");
            System.out.println(response.headers());
            List<Heroes> heroesList = response.body();
            // tri par ordre alphabetique (oui l'api ne le fait pas)
            List<Heroes> heroesSortedList = new ArrayList<Heroes>();
            heroesSortedList.add(0, heroesList.get(0));
            boolean replaced;
            for (Heroes heroe : heroesList){
                replaced = false;
                for (Heroes heroeComp : heroesSortedList) {
                    if (heroe.localized_name != heroeComp.localized_name) {
                        // Valve ne va pas ajouter de hero qui a le meme nom qu'un autre avec des caracteres en moins a la fin
                        for (int i = 0; i < heroe.localized_name.length(); i++) {
                            if (heroe.localized_name.charAt(i) < heroeComp.localized_name.charAt(i)){
                                heroesSortedList.add(heroesSortedList.indexOf(heroeComp), heroe);
                                replaced = true;
                                break;
                            }
                            else if (heroe.localized_name.charAt(i) > heroeComp.localized_name.charAt(i)) break;
                        }
                    }
                    if (replaced) break; // je n'ai aucune honte
                }
                if (!replaced) heroesSortedList.add(heroesSortedList.size(), heroe);
            }
            view.initRecycler(heroesSortedList);
            // mise en cache des nouvelles donnees
            save(heroesSortedList);
        } else {
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<List<Heroes>> call, Throwable t) {
        t.printStackTrace();
        //Au cas ou le telephone n est pas encore connecte a internet, on recharge la memoire en cache
        List<Heroes> changesList = getSave();
        view.initRecycler(changesList);
    }

    private void save(List<Heroes> changesList) {
        String changesListString = new Gson().toJson(changesList);
        sharedPreferences
                .edit()
                .putString(dataName, changesListString)
                .apply();

    }

    private List<Heroes> getSave() {
        String changesListString = sharedPreferences.getString(dataName, "");
        Type changeListType = new TypeToken<List<Heroes>>(){}.getType();
        List<Heroes> changesList = new Gson().fromJson(changesListString, changeListType);
        return changesList;
    }
}
