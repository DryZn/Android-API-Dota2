package com.example.android_api_dota2;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DotaAPI {
    @GET("/api/heroes")
    Call<List<Heroes>> loadChanges();
}
