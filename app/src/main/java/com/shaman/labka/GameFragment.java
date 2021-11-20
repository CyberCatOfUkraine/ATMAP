package com.shaman.labka;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shaman.labka.Collections.Tuple;
import com.shaman.labka.Crutches.MainActivityExample;
import com.shaman.labka.Workers.ColorWorker;
import com.shaman.labka.Workers.FragmentWorker;
import com.shaman.labka.Workers.SettingsWorker;
import com.shaman.labka.Workers.ToastWorker;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GameFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GameFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GameFragment newInstance(String param1, String param2) {
        GameFragment fragment = new GameFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    Button yes_btn;
    Button no_btn;
    Button finish_btn;
    TextView colorTextAndNameTextView;
    ProgressBar progressBar;
    TextView correctAnswerTextView;
    TextView timeLeftTextView;
    private int _rightAnswerNumber;
    private int _countOfAttempt;
    private int _timeLeft = 60;
    private int _timeMax = 60;

    private Timer _mTimer;                          ///Таймер
    private MyTimerTask mMyTimerTask;
    private Tuple<Integer, Integer> _currentColor;
    private ColorWorker _colorWorker;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        yes_btn = getView().findViewById(R.id.game_yes_btn);
        no_btn = getView().findViewById(R.id.game_no_btn);
        finish_btn = getView().findViewById(R.id.game_finish_btn);
        colorTextAndNameTextView = getView().findViewById(R.id.game_color_text_textView);
        timeLeftTextView = getView().findViewById(R.id.game_time_left_textView);
        correctAnswerTextView = getView().findViewById(R.id.game_correct_answers_textView);
        progressBar = getView().findViewById(R.id.game_progressBar);


        finish_btn.setOnClickListener(v -> {
            this.onDestroy();
            FragmentWorker.setFragment(MainMenuFragment.newInstance("", ""));
            _timeLeft=60;///WARN!
            _mTimer.cancel();
        });

        _colorWorker = new ColorWorker();

            _currentColor = _colorWorker.ReturnRandomizeColor();
            UpdateColorAndColorName(_currentColor);

        progressBar.setMax(_timeMax);
        yes_btn.setOnClickListener(v -> {
            Integer val = _colorWorker.getValueByKey(_currentColor.colorID);
            UpdateAnswerTextView(val.equals(_currentColor.colorNameID));


            UpdateColorAndColorName(_colorWorker.ReturnRandomizeColor());


        });
        no_btn.setOnClickListener(v -> {

            Integer val = _colorWorker.getValueByKey(_currentColor.colorID);

            UpdateAnswerTextView(!val.equals(_currentColor.colorNameID));

            UpdateColorAndColorName(_colorWorker.ReturnRandomizeColor());
        });



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
        _mTimer.schedule(mMyTimerTask, 1000, 1000);
    }

    private void performEndGame() {
        yes_btn.setVisibility(View.INVISIBLE);
        no_btn.setVisibility(View.INVISIBLE);
        SaveStatistic();
    }

    private void SaveStatistic() {
        if (SettingsWorker.Get().HighScore < (_countOfAttempt - _rightAnswerNumber))
            SettingsWorker.UpdateHighScore((_countOfAttempt - _rightAnswerNumber));

    }

    private void UpdateAnswerTextView(boolean increase) {
        if (increase)
            _rightAnswerNumber++;

        correctAnswerTextView.setText(String.format("%s %s", getString(R.string.correct_answer_number), _rightAnswerNumber));
    }

    ///Оновляє кольор і його назву та зберігає в змінній поточного кольору
    private void UpdateColorAndColorName(Tuple<Integer, Integer> color) {
        _countOfAttempt++;
        _currentColor = color;
        colorTextAndNameTextView.setText(getResources().getString(_currentColor.colorNameID));
        colorTextAndNameTextView.setTextColor(getResources().getColor(_currentColor.colorID));
    }

    class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            MainActivityExample.Get().runOnUiThread(() -> {
                _timeLeft--;
                if (_timeLeft < 0)
                    return;
                if (_timeLeft == 0) {
                    correctAnswerTextView.setText(String.format("%s %s %s %s", getString(R.string.correct_answer_number), _rightAnswerNumber, " з ", _countOfAttempt));
                    ToastWorker.ShowToast("Час вийшов, правильних відповідей: " + _rightAnswerNumber + " з " + _countOfAttempt);
                    performEndGame();
                }

                try {
                    String s = getResources().getString(R.string.time_left) + _timeLeft;
                    timeLeftTextView.setText(s);
                    progressBar.setProgress(_timeMax - _timeLeft);
                } catch (Exception ignored) {

                }

            });


        }
    }


}