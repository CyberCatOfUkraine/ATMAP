package com.shaman.labka.Workers;

import androidx.fragment.app.Fragment;

import com.shaman.labka.MainActivity;
import com.shaman.labka.R;

public class FragmentWorker {
    private static MainActivity _mainActivity;
    public static void SetMainActivity(MainActivity activity){
        _mainActivity=activity;
    }

    public static void SetFragment(Fragment fragment) {
        _mainActivity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment).commit();
    }
}
