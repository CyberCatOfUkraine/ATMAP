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
import com.shaman.labka.Workers.GameWorker;
import com.shaman.labka.Workers.SettingsWorker;
import com.shaman.labka.Workers.ToastWorker;

import java.text.BreakIterator;
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

    public Button yes_btn;
    public Button no_btn;
    public Button finish_btn;
    public TextView colorTextAndNameTextView;
    public ProgressBar progressBar;
    public TextView correctAnswerTextView;
    public TextView timeLeftTextView;

    public boolean itsLoaded=false;
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

        GameWorker.SetGameFragment(this);
        GameWorker.GetInstance().Start();
        finish_btn.setOnClickListener(v -> {
            this.onDestroy();
            GameWorker.FullStop();
            FragmentWorker.setFragment(MainMenuFragment.newInstance("", ""));
        });

        yes_btn.setOnClickListener(v -> {
            GameWorker.GetInstance().YesBtnOnClick();

        });
        no_btn.setOnClickListener(v -> {
            GameWorker.GetInstance().NoBtnOnClick();

        });
        itsLoaded=true;
    }




}