package com.shaman.labka.Workers;


import com.shaman.labka.OrmModels.Settings;

public class SettingsWorker {
    public void Save(Settings settings){
        settings.save();
    }
    public void ReplaceEmail(String email){
        Settings settings=Settings.findById(Settings.class,1);
        settings.Email=email;
        settings.save();
    }
    public void ReplaceUserName(String userName){
        Settings settings=Settings.findById(Settings.class,1);
        settings.UserName=userName;
        settings.save();
    }
    ///Якщо прилітає NULL то таблиця не створена
    public Settings Get(){
        return Settings.findById(Settings.class,1);
    }
}
