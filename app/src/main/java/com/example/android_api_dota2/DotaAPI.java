package com.example.android_api_dota2;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DotaAPI {
    @GET("heroStats")
    Call<List<Heroes>> getHeroes();

    @GET("proPlayers")
    Call<List<Heroes>> getPros(@Path("name") String nameSearched);

    // avoir le nom d'un utilisateur recherche
    @GET("search?q={name}") //tester en remplacant par query
    Call<List<Heroes>> userSearch(@Path("name") String nameSearched);

}
