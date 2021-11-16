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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.orm.SchemaGenerator;
import com.orm.SugarContext;
import com.orm.SugarDb;
import com.shaman.labka.Collections.Tuple;
import com.shaman.labka.OrmModels.Settings;
import com.shaman.labka.Workers.ColorWorker;
import com.shaman.labka.Workers.FragmentWorker;
import com.shaman.labka.Workers.SettingsWorker;

import java.util.Random;
import java.util.Timer;

///Додаток логічна  гра  на  визначення  співпадіння  кольору тексту  та  назви  кольору.
///Генерація   випадковим   чином   кольорів   та підрахунок правильних  відповідей  впродовж  1  хвилини та  вивід  результатів  роботи програми
public class MainActivity extends AppCompatActivity {
    private final ColorWorker _worker;              ///Доступ до списку кольорів та його ініціалізація
    private TextView _questTextView;                ///Отримує поточний кольор та його назву
    private TextView _correctAnswerTextView;        ///Отримує кількість правильних відповідей
    private TextView _timeLeftTextView;             ///Отримує час що лишився
    private Tuple<Integer, Integer> _currentColor;  ///Поточний кольор та назва кольору
    //private State _gameState;                       ///Параметр стану програми

    boolean _gameStarted;                           ///Гра почата
    short _rightAnswerNumber;                       ///Правильна кількість відповідей

    private Timer _mTimer;                          ///Таймер
    //private MyTimerTask mMyTimerTask;               ///Завдання для таймеру
    private short _timeLeft;                        ///Лічильник кількості часу що лишився
    private  int _countOfAttempt;                   ///Лічильник кількості спроб

    public MainActivity() {
        _worker = new ColorWorker();
        _currentColor = _worker.GetCurrentColor();
        //_gameState = State.Started;
        _gameStarted = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentWorker.SetMainActivity(this);

        SchemaGenerator schemaGenerator = new SchemaGenerator(this);
        schemaGenerator.createDatabase(new SugarDb(this).getDB());
        SugarContext.init(this);

        Settings settings=new SettingsWorker().Get();
        if (settings==null||settings.UserName==null){
            FragmentWorker.SetFragment(NotRegistredFragment.newInstance("",""));
        }else {
            FragmentWorker.SetFragment(MainMenuFragment.newInstance("",""));
        }
/*
        _questTextView          = findViewById(R.id.quest_textView);
        _correctAnswerTextView  = findViewById(R.id.correct_answer_textView);
        _timeLeftTextView       = findViewById(R.id.time_left_textView);
        Button ssr_btn          = findViewById(R.id.btn_start_stop_reset);
        Button btn_yes          = findViewById(R.id.btn_yes);
        Button btn_no           = findViewById(R.id.btn_no);

        ssr_btn.setOnClickListener(view -> {
            switch (_gameState) {
                case Started:///Старт після перезапуску
                    _gameState = State.Stopped;
                    ssr_btn.setText(getString(R.string.stop));


                    //region Робимо доступними ігрові елементи
                    _questTextView.setVisibility(View.VISIBLE);
                    _correctAnswerTextView.setVisibility(View.VISIBLE);
                    _timeLeftTextView.setVisibility(View.VISIBLE);
                    btn_yes.setVisibility(View.VISIBLE);
                    btn_no.setVisibility(View.VISIBLE);
                    _gameStarted = true;
                    //endregion

                    //region Ініціалізація початковими значеннями
                    _rightAnswerNumber=0;
                    _correctAnswerTextView.setText(String.valueOf(_rightAnswerNumber));

                    _countOfAttempt=0;
                    //endregion

                    //region Ініціалізація таймеру
                    if (_mTimer != null) {
                        _mTimer.cancel();
                    }

                    // re-schedule timer here
                    // otherwise, IllegalStateException of
                    // "TimerTask is scheduled already"
                    // will be thrown
                    _mTimer = new Timer();
                    mMyTimerTask = new MyTimerTask();
                    _timeLeft=60;
                    _mTimer.schedule(mMyTimerTask, 1000, 1000);


                    //endregion
                    _gameState = State.Stopped;
                    ssr_btn.setText(getString(R.string.stop));
                    break;
                case Stopped:///Зупинка
                    _gameState = State.Restarted;
                    ssr_btn.setText(getString(R.string.restart));
                    _gameStarted=false;
                    break;
                case Restarted:///Перезапуск, скидання лічильника
                    _gameState = State.Started;
                    ssr_btn.setText(getString(R.string.start));
                    break;
            }

        });

        UpdateColorAndColorName(ReturnRandomizeColor());


        btn_yes.setOnClickListener(view -> {
            if (_gameStarted) {
                Integer val = _worker.getValueByKey(_currentColor.colorID);
                UpdateAnswerTextView(val.equals(_currentColor.colorNameID));


                UpdateColorAndColorName(ReturnRandomizeColor());
            }

        });
        findViewById(R.id.btn_no).setOnClickListener(view -> {
            if (_gameStarted) {

                Integer val = _worker.getValueByKey(_currentColor.colorID);

                UpdateAnswerTextView(!val.equals(_currentColor.colorNameID));

                UpdateColorAndColorName(ReturnRandomizeColor());
            }
        });*/
    }

    private void loadFragment(Fragment fragment) {
        FragmentWorker.SetFragment(fragment);
    }
    private void UpdateAnswerTextView(boolean increase) {
        if (increase)
            _rightAnswerNumber++;

        _correctAnswerTextView.setText(String.format("%s %s", getString(R.string.correct_answer_number), _rightAnswerNumber));
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

    ///Оновляє кольор і його назву та зберігає в змінній поточного кольору
    private void UpdateColorAndColorName(Tuple<Integer, Integer> color) {
        if (_gameStarted) {
            _countOfAttempt++;
            _currentColor = color;
            _questTextView.setText(getResources().getString(_currentColor.colorNameID));
            _questTextView.setTextColor(getResources().getColor(_currentColor.colorID));
        }
    }

    private void ShowToast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    private Tuple<Integer, Integer> ReturnRandomizeColor() {
        Integer color = _worker.getKeyAt(new Random().nextInt(_worker.size()));
        Integer colorName = _worker.getValueByKey(_worker.getKeyAt(new Random().nextInt(_worker.size())));
        return new Tuple<>(color, colorName);
    }
/*
    class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            runOnUiThread(() -> {
                if (_gameStarted){
                    _timeLeft--;
                    if (_timeLeft<0)
                        return;
                    if (_timeLeft==0) {
                        _correctAnswerTextView.setText(String.format("%s %s %s %s", getString(R.string.correct_answer_number), _rightAnswerNumber," з ",_countOfAttempt));
                        ShowToast("Час вийшов, правильних відповідей: " + _rightAnswerNumber+" з "+_countOfAttempt);
                        findViewById(R.id.btn_start_stop_reset).performClick();
                    }

                    String s =getResources().getString(R.string.time_left)+_timeLeft;
                    _timeLeftTextView.setText(s);
                }else {
                    _timeLeftTextView.setText(getResources().getString(R.string.time_left));
                }

            });
        }
}*/
}