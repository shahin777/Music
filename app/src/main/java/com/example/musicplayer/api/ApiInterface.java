package com.example.musicplayer.api;

import com.example.musicplayer.model.ItemMusic;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
     String BASE_URL = "https://api.whichapp.com/";

    @GET("v1/countries")
    Call<List<ItemMusic>> getMusic();
}
