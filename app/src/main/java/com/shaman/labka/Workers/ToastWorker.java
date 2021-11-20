package com.shaman.labka.Workers;

import android.widget.Toast;

import com.shaman.labka.MainActivity;

public class ToastWorker {
    private static MainActivity _activity;
    public static void Initialize(MainActivity activity) {
        _activity=activity;
    }

    public static void ShowToast(String text) {
        Toast.makeText(_activity.getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }
}
