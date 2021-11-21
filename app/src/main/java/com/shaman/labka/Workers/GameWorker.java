package com.shaman.labka.Workers;

import android.view.View;

import com.shaman.labka.Collections.Tuple;
import com.shaman.labka.Crutches.MainActivityExample;
import com.shaman.labka.GameFragment;
import com.shaman.labka.R;

import java.util.Timer;
import java.util.TimerTask;


public class GameWorker {

    private static GameWorker _gameWorker;
    private int startedCount =0;
    public static GameWorker GetInstance(){
        if (_gameWorker==null){
            _gameWorker=new GameWorker();
        }
        return _gameWorker;
    }


    private Timer _mTimer;                          ///Таймер
    private MyTimerTask mMyTimerTask;
    private int _rightAnswerNumber = 0;
    private int _countOfAttempt = 0;
    private int _timeLeft = 10;
    private int _timeMax = 10;

    GameFragment _gameFragment;

    public static void FullStop() {
        GameWorker.GetInstance()._rightAnswerNumber = 0;
        GameWorker.GetInstance()._countOfAttempt = 0;
        GameWorker.GetInstance()._timeLeft = GameWorker.GetInstance()._timeMax+1;
        GameWorker.GetInstance()._timeLeft--;
       GameWorker.GetInstance()._mTimer.cancel();
       GameWorker.GetInstance()._mTimer=null;
       GameWorker.GetInstance().startedCount=0;
    }

    public static void SetGameFragment(GameFragment gameFragment){
        GameWorker gameWorker= GetInstance();
       _gameWorker._gameFragment=gameFragment;
    }

    private Tuple<Integer, Integer> _currentColor;
    private ColorWorker _colorWorker;

    public  void  Start(){
        if (GameWorker.GetInstance().startedCount==0){

            GameWorker.GetInstance()._rightAnswerNumber = 0;
            GameWorker.GetInstance()._countOfAttempt = -1;
            GameWorker.GetInstance().startedCount=0;

            GameWorker.GetInstance()._colorWorker = new ColorWorker();

            GameWorker.GetInstance()._currentColor = _colorWorker.ReturnRandomizeColor();
            UpdateColorAndColorName(_currentColor);

            _gameFragment.progressBar.setMax(_timeMax);

            GameWorker.GetInstance().startedCount++;
            _gameFragment.correctAnswerTextView.setText(String.format("%s %s",MainActivityExample.Get().getString(R.string.correct_answer_number), 0));

        }else {

            _gameFragment.colorTextAndNameTextView.setText(MainActivityExample.Get().getResources().getString(_currentColor.colorNameID));
            _gameFragment.colorTextAndNameTextView.setTextColor(MainActivityExample.Get().getResources().getColor(_currentColor.colorID));
        }


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


        _gameFragment.timeLeftTextView.setText(String.format("%s%d", MainActivityExample.Get().getResources().getString(R.string.time_left), _timeLeft));
        _gameFragment.progressBar.setProgress(_timeMax - _timeLeft);
    }

    ///Оновляє кольор і його назву та зберігає в змінній поточного кольору
    private void UpdateColorAndColorName(Tuple<Integer, Integer> color) {
        GameWorker.GetInstance()._countOfAttempt++;
        GameWorker.GetInstance()._currentColor = color;
        if (_gameFragment!=null&&_gameFragment.colorTextAndNameTextView!=null){
            _gameFragment.colorTextAndNameTextView.setText(MainActivityExample.Get().getResources().getString(_currentColor.colorNameID));
            _gameFragment.colorTextAndNameTextView.setTextColor(MainActivityExample.Get().getResources().getColor(_currentColor.colorID));
        }

    }
    private void SaveStatistic() {
        if (SettingsWorker.Get().HighScore < (_countOfAttempt - _rightAnswerNumber))
            SettingsWorker.UpdateHighScore(_rightAnswerNumber-(_countOfAttempt - _rightAnswerNumber));

    }
    private void disableGameButtons() {
        _gameFragment.yes_btn.setVisibility(View.INVISIBLE);
        _gameFragment.no_btn.setVisibility(View.INVISIBLE);
    }

    public void NoBtnOnClick() {
        Integer val =GameWorker.GetInstance()._colorWorker.getValueByKey(GameWorker.GetInstance()._currentColor.colorID);
        UpdateAnswerTextView(!val.equals(GameWorker.GetInstance()._currentColor.colorNameID));


        UpdateColorAndColorName(GameWorker.GetInstance()._colorWorker.ReturnRandomizeColor());
    }

    public void YesBtnOnClick() {
        Integer val = GameWorker.GetInstance()._colorWorker.getValueByKey(GameWorker.GetInstance()._currentColor.colorID);

        UpdateAnswerTextView(val.equals(GameWorker.GetInstance()._currentColor.colorNameID));

        UpdateColorAndColorName(GameWorker.GetInstance()._colorWorker.ReturnRandomizeColor());
    }

    private void UpdateAnswerTextView(boolean increase) {
        if (increase)
            GameWorker.GetInstance()._rightAnswerNumber++;

       _gameFragment.correctAnswerTextView.setText(String.format("%s %s",MainActivityExample.Get().getString(R.string.correct_answer_number), _rightAnswerNumber));
    }
    class MyTimerTask extends TimerTask {


        @Override
        public void run() {
            MainActivityExample.Get().runOnUiThread(() -> {

                _timeLeft--;
                if (_timeLeft == 0) {
                    _gameFragment.correctAnswerTextView.setText(String.format("%s %s %s %s", MainActivityExample.Get().getString(R.string.correct_answer_number), _rightAnswerNumber, " з ", _countOfAttempt));
                    ToastWorker.ShowToast("Час вийшов, правильних відповідей: " + _rightAnswerNumber + "/" + _countOfAttempt);
                    disableGameButtons();
                    SaveStatistic();
                    _gameFragment.timeLeftTextView.setText(String.format("%s%d", MainActivityExample.Get().getResources().getString(R.string.time_left), _timeLeft));
                    _gameFragment.progressBar.setProgress(_timeMax);
                    GameWorker.GetInstance()._mTimer.cancel();
                    return;
                }
                _gameFragment.timeLeftTextView.setText(String.format("%s%d", MainActivityExample.Get().getResources().getString(R.string.time_left), _timeLeft));
                _gameFragment.progressBar.setProgress(_timeMax - _timeLeft);

            });


        }
    }
}
