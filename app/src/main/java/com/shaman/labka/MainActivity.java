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

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private final ColorWorker _worker;
    private TextView _questTextView;
    private TextView _correctAnswerTextView;
    private TextView _timeLeftTextView;
    private Tuple<Integer, Integer> _currentColor;
    private State _gameState;

    boolean _gameStarted;
    short _rightAnswerNumber;

    public MainActivity() {
        _worker = new ColorWorker();
        _currentColor = _worker.GetCurrentColor();
        _gameState = State.Started;
        _gameStarted = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _questTextView = findViewById(R.id.quest_textView);
        _correctAnswerTextView = findViewById(R.id.correct_answer_textView);
        _timeLeftTextView = findViewById(R.id.time_left_textView);

        Button ssr_btn = findViewById(R.id.btn_start_stop_reset);
        Button btn_yes = findViewById(R.id.btn_yes);
        Button btn_no = findViewById(R.id.btn_no);

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
                    //endregion

                    _gameState = State.Stopped;
                    ssr_btn.setText(getString(R.string.stop));

                    ShowToast("1");
                    break;
                case Stopped:///Зупинка
                    _gameState = State.Restarted;
                    ssr_btn.setText(getString(R.string.restart));
                    ShowToast("2");
                    _gameStarted=false;
                    break;
                case Restarted:///Перезапуск, скидання лічильника
                    _gameState = State.Started;
                    ssr_btn.setText(getString(R.string.start));
                    ShowToast("3");
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
        });
    }

    private void UpdateAnswerTextView(boolean increase) {
        if (increase)
            _rightAnswerNumber++;

        _correctAnswerTextView.setText(String.format("%s %s", getString(R.string.correct_answer_number), _rightAnswerNumber));
    }

    ///Оновляє кольор і його назву та зберігає в змінній поточного кольору
    private void UpdateColorAndColorName(Tuple<Integer, Integer> color) {
        if (_gameStarted) {
            _currentColor = color;
            _questTextView.setText(getResources().getString(_currentColor.colorNameID));
            _questTextView.setTextColor(getResources().getColor(_currentColor.colorID));
        }
    }

    private void ShowToast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    ///сюда повісить початкові повідомленнЯ
    private void ShowToastLong(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }

    private Tuple<Integer, Integer> ReturnRandomizeColor() {
        Integer color = _worker.getKeyAt(new Random().nextInt(_worker.size()));
        Integer colorName = _worker.getValueByKey(_worker.getKeyAt(new Random().nextInt(_worker.size())));
        return new Tuple<>(color, colorName);
    }

    ///Це таймер, неперевірений
    public void Counter(MainActivity activity) {
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            int i = 60;

            public void run() {
                i--;

                if (i < 0) {
                    timer.cancel();
                    activity.ShowToast("");
                }
            }
        }, 0, 1000);
    }
}