package com.example.android_api_dota2;

import android.content.SharedPreferences;
import android.content.res.Resources;

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
    private String dataName;

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
            // tri des donnees recues
            List<Heroes> heroesSortedList = (List<Heroes>) sortArray(heroesList, "");
            // utiliser interface ici
            view.heroesList.data = heroesList;
            view.heroesList.adaptater = new HerosAdapter(view.heroesList, heroesSortedList);
            view.heroesList.list_items.swapAdapter(view.heroesList.adaptater, false);
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
        List<Heroes> savedList = getSave();
        view.heroesList.adaptater = new HerosAdapter(view.heroesList, savedList);
        view.heroesList.list_items.swapAdapter(view.heroesList.adaptater, false);
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

    // fonction pour trier les heros en fonction du parametre donne
    protected static List<?> sortArray(List<Heroes> heroesList, String sortType) {
        List<Heroes> heroesSortedList = new ArrayList<>();
        boolean replaced;
        for (Heroes heroe : heroesList){
            replaced = false;
            for (Heroes heroeComp : heroesSortedList) {
                switch (sortType) {
                    // tri par ordre alphabetique (oui l'api ne le fait pas)
                    default:
                        if (heroe.localized_name != heroeComp.localized_name) {
                            // Valve ne va pas ajouter de hero qui a presque le meme nom qu'un autre donc inutile de verifier quelle nom contient le plus de caracteres
                            for (int i = 0; i < heroe.localized_name.length(); i++) {
                                if (heroe.localized_name.charAt(i) < heroeComp.localized_name.charAt(i)) {
                                    // insertion du heros dans la nouvelle liste si prioritaire
                                    heroesSortedList.add(heroesSortedList.indexOf(heroeComp), heroe);
                                    replaced = true;
                                    break;
                                } else if (heroe.localized_name.charAt(i) > heroeComp.localized_name.charAt(i))
                                    break;
                            }
                        }
                        break;
                }
                if (replaced) break;
            }
            // ajout a la fin de la liste si non prioritaire
            if (!replaced) heroesSortedList.add(heroesSortedList.size(), heroe);
        }
        return heroesSortedList;
    }

    // fonction qui retourne en tableau filtre selon les parametres
    protected static List<Heroes> filterArray(List<Heroes> heroesList, String filter, Resources resources) {
        // on ne traite pas les valeurs par defaut
        if (filter.equals(resources.getStringArray(R.array.roles)[0])) return heroesList;
        else if (filter.equals(resources.getStringArray(R.array.attack_types)[0])) return heroesList;

        List<Heroes> filteredArray = new ArrayList<>();
        for (Heroes hero : heroesList) {
            // on fait du cas par cas
            if (hero.getAttackType().equals(filter)) {
                filteredArray.add(hero);
                System.out.println(filter);
                System.out.println(hero.getAttackType());
            } else if (hero.getRoles().contains(filter))
                filteredArray.add(hero);
            else if (hero.getAttribute().equals(filter))
                filteredArray.add(hero);
        }

        return filteredArray;
    }
}
