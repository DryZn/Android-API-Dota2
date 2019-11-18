package com.example.android_api_dota2;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SmashAPI {
    @GET("/api/heroes")
    //@GET("/api/characters")
    //@GET("/api/characters?game=ultimate")
    Call<List<Characters>> loadChanges();
}
