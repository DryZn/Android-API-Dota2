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

public class ControllerAPI {
    static final String BASE_URL = "https://api.opendota.com/api/";
    private MainActivity view;
    private SharedPreferences sharedPreferences;
    private int dataNameID;
    private String dataName;
    protected String searchedData;

    public ControllerAPI(MainActivity view, SharedPreferences sharedPreferences, int dataNameID) {
        this.view = view;
        this.sharedPreferences = sharedPreferences;
        this.dataNameID = dataNameID;
    }

    // on recharge si possible les donnees ici
    public void start() {
        // preparation de la reception d'une reponse get
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        DotaAPI dotaAPI = retrofit.create(DotaAPI.class);
        dataName = view.getResources().getString(dataNameID);

        switch (dataNameID) {
            case R.string.frag_hero_list_tag:
                lauchHeroesRequest(dotaAPI);
                break;
            case R.string.frag_community_tag:
                lauchCommunityRequest(dotaAPI);
                break;

        }
    }

    private void lauchCommunityRequest(DotaAPI dotaAPI) {
        Call<List<Users>> call = dotaAPI.userSearch(searchedData);
        call.enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                List<Users> heroesList = response.body();
                // tri des donnees recues
                //List<Users> heroesSortedList = (List<Heroes>) sortArray(heroesList, "");
                save(heroesList);
                //initFragData(heroesSortedList);
                if(response.isSuccessful()) {
                    // mise en cache des nouvelles donnees
                } else {
                    System.out.println(response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {
                t.printStackTrace();
                //Au cas ou le telephone n est pas encore connecte a internet, on recharge la memoire en cache
                List<Users> savedList = (List<Users>) getSave();
                //initFragData(savedList);
            }
        });
    }

        private void lauchHeroesRequest(DotaAPI dotaAPI) {
        Call<List<Heroes>> call = dotaAPI.getHeroes();
        call.enqueue(new Callback<List<Heroes>>() {
            @Override
            public void onResponse(Call<List<Heroes>> call, Response<List<Heroes>> response) {
                List<Heroes> heroesList = response.body();
                // tri des donnees recues
                List<Heroes> heroesSortedList = (List<Heroes>) sortArray(heroesList, "");
                save(heroesSortedList);
                // association de l'adatpeur contenant les donnees au recyclerview
                view.updateRecyclerFrag(heroesSortedList);
                if(response.isSuccessful()) {
                    // mise en cache des nouvelles donnees
                } else {
                    System.out.println(response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<Heroes>> call, Throwable t) {
                t.printStackTrace();
                //Au cas ou le telephone n est pas encore connecte a internet, on recharge la memoire en cache
                List<Heroes> savedList = (List<Heroes>) getSave();
                view.updateRecyclerFrag(savedList);
            }
        });
    }

    private void save(List<?> changesList) {
        String changesListString = new Gson().toJson(changesList);
        sharedPreferences
                .edit()
                .putString(dataName, changesListString)
                .apply();

    }

    private List<?> getSave() {
        String changesListString = sharedPreferences.getString(dataName, "");
        Type changeListType = new TypeToken<List<?>>(){}.getType();
        List<?> changesList = new Gson().fromJson(changesListString, changeListType);
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
        // for (Heroes hero : heroesFilteredList) {
        for (Heroes hero : heroesList) {
            // on fait du cas par cas
            if (hero.getAttackType().equals(filter)) {
                filteredArray.add(hero);
            } else if (hero.getRoles().contains(filter))
                filteredArray.add(hero);
            else if (hero.getAttribute().equals(filter))
                filteredArray.add(hero);
        }
        return filteredArray;
    }
}
