/*
* Завдання до лабораторної роботи:
* Створити   додаток   у якому реалізувати  варіант
*   логічної  гри  на  визначення  співпадіння  кольору тексту  та  назви  кольору.
*
*
* P.S. На момент цього коміту це 4 лабораторна робота яку виконує студент
* минулого авіаційного а тепер
* торгівельно-економічного університету
* (м. Київ)
* так що тапочки кидайте на домен nau.edu.ua
* */
package com.shaman.labka;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.orm.SchemaGenerator;
import com.orm.SugarContext;
import com.orm.SugarDb;
import com.shaman.labka.OrmModels.Settings;
import com.shaman.labka.Workers.FragmentWorker;
import com.shaman.labka.Workers.SettingsWorker;

///Додаток логічна  гра  на  визначення  співпадіння  кольору тексту  та  назви  кольору.
///Генерація   випадковим   чином   кольорів   та підрахунок правильних  відповідей  впродовж  1  хвилини та  вивід  результатів  роботи програми
public class MainActivity extends AppCompatActivity {

    private static boolean _firstLaunch=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //region FragmentWorker initializing
        FragmentWorker.setMainActivity(this);
        //endregion

        //region DB initializing
        SchemaGenerator schemaGenerator = new SchemaGenerator(this);
        schemaGenerator.createDatabase(new SugarDb(this).getDB());
        SugarContext.init(this);
        //endregion

        //region first fragment initializing
        if (_firstLaunch){
            Settings settings=new SettingsWorker().Get();
            if (settings==null||settings.UserName==null){
                loadFragment(NotRegistredFragment.newInstance("",""));
            }else {
                loadFragment(MainMenuFragment.newInstance("",""));
            }
            _firstLaunch=false;
        }
        //endregion
    }

    private void loadFragment(Fragment fragment) {
        FragmentWorker.setFragment(fragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_settings){
            loadFragment(SettingsFragment.newInstance("",""));

        }
        if(item.getItemId()==R.id.action_statistic){
            loadFragment(StatisticFragment.newInstance("",""));

        }
        if(item.getItemId()==R.id.action_go_to_main){
            loadFragment(MainMenuFragment.newInstance("",""));
        }
        return super.onOptionsItemSelected(item);
    }
}