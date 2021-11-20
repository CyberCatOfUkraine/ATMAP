package com.shaman.labka;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.shaman.labka.OrmModels.Settings;
import com.shaman.labka.Workers.FragmentWorker;
import com.shaman.labka.Workers.SettingsWorker;
import com.shaman.labka.Workers.ToastWorker;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewUserRegistrationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewUserRegistrationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NewUserRegistrationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewUserRegistrationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewUserRegistrationFragment newInstance(String param1, String param2) {
        NewUserRegistrationFragment fragment = new NewUserRegistrationFragment();
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        EditText userName=getView().findViewById(R.id.createUserNameEditText);
        EditText email=getView().findViewById(R.id.createUserEmailEditText);
        Button submit=getView().findViewById(R.id.registration_btn);
        submit.setOnClickListener(v -> {

            if (userName.getText().toString().length()==0&&email.getText().toString().length()==0){
                ToastWorker.ShowToast("Відсутнє і'мя користувача та Email !");
                return;
            }
            else  if (userName.getText().toString().length()==0){
                ToastWorker.ShowToast("Відсутнє і'мя користувача !");
                return;
            }else if (email.getText().toString().length()==0){
                ToastWorker.ShowToast("Відсутній email користувача !");
                return;
            }
            SettingsWorker.Save(new Settings(userName.getText().toString(),email.getText().toString(),0));
            ToastWorker.ShowToast("Збережено!");


            FragmentWorker.setFragment(MainMenuFragment.newInstance("",""));
        });

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_user_registration, container, false);
    }
}