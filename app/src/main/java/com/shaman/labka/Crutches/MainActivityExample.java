package com.shaman.labka.Crutches;

import com.shaman.labka.MainActivity;

public class MainActivityExample {
    private static MainActivity _activity;
    public static void SetActivity(MainActivity activity){
        if (_activity==null){
            _activity=activity;
        }
    }
    public static MainActivity Get(){
        return _activity;
    }
}
