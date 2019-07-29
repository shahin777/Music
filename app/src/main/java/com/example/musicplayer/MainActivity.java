package com.example.musicplayer;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.musicplayer.adapter.MusicAdapter;
import com.example.musicplayer.api.ApiInterface;
import com.example.musicplayer.application.AppContext;
import com.example.musicplayer.dialog.InternetErrorDialog;
import com.example.musicplayer.model.ItemMusic;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private MusicAdapter musicAdapter;
    private List<ItemMusic> musicList = new ArrayList<>();
    private InternetErrorDialog internetErrorDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initComponent();
        new NetCheck().execute();
    }

    private void initComponent() {
        musicList.clear();
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        musicAdapter = new MusicAdapter(musicList);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(musicAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        internetErrorDialog = new InternetErrorDialog(this);
        internetErrorDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (AppContext.preferences.getString("CLICK_BTN", "FALSE").equals("TRUE")) {
                    new NetCheck().execute();
                } else {
                    finish();
                }
            }
        });
    }

    private void getResponse() {
        ApiInterface api = AppContext.retrofit.create(ApiInterface.class);
        api.getMusic().enqueue(new Callback<List<ItemMusic>>() {
            @Override
            public void onResponse(Call<List<ItemMusic>> call, Response<List<ItemMusic>> response) {
                getListMusic(response);
            }

            @Override
            public void onFailure(Call<List<ItemMusic>> call, Throwable t) {

            }
        });
    }

    private void getListMusic(Response<List<ItemMusic>> response) {
        List<ItemMusic> musics = response.body();
        for (int i = 0; i < musics.size(); i++) {
            ItemMusic itemMusic = new ItemMusic();

            itemMusic.setIso(musics.get(i).getIso());
            itemMusic.setName(musics.get(i).getName());
            itemMusic.setPhone(musics.get(i).getPhone());

            musicList.add(itemMusic);
        }
        avi.hide();
        musicAdapter.notifyDataSetChanged();
    }

    private class NetCheck extends AsyncTask<String, String, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            avi.show();
        }

        @Override
        protected Boolean doInBackground(String... args) {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null) {
                if (netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    return true;
                } else if (netInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    return true;
                }
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean th) {
            if (th == true) {
                getResponse();
            } else {
                avi.hide();
                internetErrorDialog.show();
            }
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
