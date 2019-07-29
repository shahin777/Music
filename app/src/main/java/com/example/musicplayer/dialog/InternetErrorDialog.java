package com.example.musicplayer.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.musicplayer.R;
import com.example.musicplayer.application.AppContext;

public class InternetErrorDialog extends Dialog implements View.OnClickListener{

    Activity c;
    TextView txtTryAgain, txtCancel;

    public InternetErrorDialog(Activity a) {
        super(a);
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_internet_error);
        txtTryAgain = findViewById(R.id.txtTryAgain);
        txtCancel = findViewById(R.id.txtCancel);

        txtTryAgain.setOnClickListener(this);
        txtCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtTryAgain:
                AppContext.preferences.edit().putString("CLICK_BTN", "TRUE").commit();
                dismiss();
                break;
            case R.id.txtCancel:
                AppContext.preferences.edit().putString("CLICK_BTN", "FALSE").commit();
                dismiss();
                break;
        }
    }
}