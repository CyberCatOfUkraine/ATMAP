package com.shaman.labka.Workers;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.shaman.labka.MainActivity;
import com.shaman.labka.R;

import java.util.ArrayList;

public class FragmentWorker {
    private static MainActivity _mainActivity;
    private static Fragment _oldFragment;

    public static void setMainActivity(MainActivity activity){
        _mainActivity=activity;
    }

    public static Fragment getFragmentForSave(){
        return  _oldFragment;
    }
    public static void setFragment(Fragment fragment) {
        if (_oldFragment !=null){

            _mainActivity.getSupportFragmentManager().beginTransaction()
                    .remove(_oldFragment).commit();
        }
        _mainActivity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment).commit();
        _oldFragment=fragment;
    }

    public static Fragment GetFragmentBiId(int id) {
    return _mainActivity.getSupportFragmentManager().findFragmentById(id);
    }
    /*public static void setFragment(Fragment oldFragment,Fragment fragment) {
        oldFragment.onDestroy();
        _mainActivity.getSupportFragmentManager().beginTransaction()
                .remove(oldFragment).commit();
        _mainActivity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment).commit();
    }*/
}
