package com.shaman.labka;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.shaman.labka.Collections.Tuple;
import com.shaman.labka.Crutches.MainActivityExample;
import com.shaman.labka.Workers.ColorWorker;
import com.shaman.labka.Workers.FragmentWorker;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FastGameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FastGameFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FastGameFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FastGameFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FastGameFragment newInstance(String param1, String param2) {
        FastGameFragment fragment = new FastGameFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public Button yes_btn;
    public Button no_btn;
    public Button finish_btn;
    public TextView colorTextAndNameTextView;
    public TextView correctAnswerTextView;

    private static Tuple<Integer, Integer> _currentColor;
    private static ColorWorker _colorWorker;

    private static int _rightAnswerNumber = 0;

    private static boolean _gameStarted =false;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        yes_btn = getView().findViewById(R.id.fast_game_yes_btn);
        no_btn = getView().findViewById(R.id.fast_game_no_btn);
        finish_btn = getView().findViewById(R.id.fast_game_finish_btn);
        colorTextAndNameTextView = getView().findViewById(R.id.fast_game_color_text_textView);
        correctAnswerTextView = getView().findViewById(R.id.fast_game_correct_answers_textView);


        if (!_gameStarted){

            _rightAnswerNumber = 0;

            _colorWorker = new ColorWorker();

            _currentColor = _colorWorker.ReturnRandomizeColor();
            UpdateColorAndColorName(_currentColor);


            correctAnswerTextView.setText(String.format("%s %s", MainActivityExample.Get().getString(R.string.correct_answer_number), 0));

            _gameStarted=true;
        }else {

            colorTextAndNameTextView.setText(MainActivityExample.Get().getResources().getString(_currentColor.colorNameID));
            colorTextAndNameTextView.setTextColor(MainActivityExample.Get().getResources().getColor(_currentColor.colorID));
            correctAnswerTextView.setText(String.format("%s %s", MainActivityExample.Get().getString(R.string.correct_answer_number), _rightAnswerNumber));

        }



        finish_btn.setOnClickListener(v -> {
            _gameStarted=false;
            _rightAnswerNumber = 0;

            FragmentWorker.setFragment(MainMenuFragment.newInstance("", ""));
        });

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
    }

    private void UpdateColorAndColorName(Tuple<Integer, Integer> color) {
        _currentColor = color;
        colorTextAndNameTextView.setText(MainActivityExample.Get().getResources().getString(_currentColor.colorNameID));
        colorTextAndNameTextView.setTextColor(MainActivityExample.Get().getResources().getColor(_currentColor.colorID));
    }
    private void UpdateAnswerTextView(boolean increase) {
        if (increase)
            _rightAnswerNumber++;

        correctAnswerTextView.setText(String.format("%s %s",MainActivityExample.Get().getString(R.string.correct_answer_number), _rightAnswerNumber));
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fast_game, container, false);
    }
}