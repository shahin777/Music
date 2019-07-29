package com.example.musicplayer.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.musicplayer.R;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

import static com.example.musicplayer.api.ApiInterface.BASE_URL;

public class AppContext extends Application {

  public static Context context;
  public static SharedPreferences preferences;
  public static Retrofit retrofit;

  @Override
  public void onCreate() {
    super.onCreate();

    context = getApplicationContext();
    preferences = PreferenceManager.getDefaultSharedPreferences(this);

    retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
            .setDefaultFontPath("fonts/IRANSansWeb.ttf")
            .setFontAttrId(R.attr.fontPath)
            .build()
    );
  }
}
