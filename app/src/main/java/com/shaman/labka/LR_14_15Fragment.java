package com.shaman.labka;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shaman.labka.Crutches.MainActivityExample;
import com.shaman.labka.LR_14_15.Crime;
import com.shaman.labka.LR_14_15.CrimeAdapter;
import com.shaman.labka.Workers.FragmentWorker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LR_14_15Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LR_14_15Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LR_14_15Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LR_14_15Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LR_14_15Fragment newInstance(String param1, String param2) {
        LR_14_15Fragment fragment = new LR_14_15Fragment();
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

    ArrayList<Crime> crimes = new ArrayList<>();
    LayoutInflater _inflater;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // начальная инициализация списка
        setInitialData();
        RecyclerView recyclerView = getView().findViewById(R.id.lr_14_recyclerView);
        // создаем адаптер
        CrimeAdapter adapter = new CrimeAdapter(_inflater, crimes);
        // устанавливаем для списка адаптер
        recyclerView.setAdapter(adapter);

        getView().findViewById(R.id.lr_14_btn).setOnClickListener(v -> {
            FragmentWorker.setFragment(MainMenuFragment.newInstance("",""));
        });
    }

    private void setInitialData() {
        Random random=new Random();
        for (int i=0;i<10;i++){
            Crime crime=new Crime();
            crime.setTitle("Кража тапочків "+i);
            crime.setSolved(random.nextBoolean());
            crime.setDate(getDate(2010+i,1,i+i));
            crimes.add(crime);

        }
    }
    public static Date getDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        _inflater=inflater;
        return inflater.inflate(R.layout.fragment_l_r_14_15, container, false);
    }
}