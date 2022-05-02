package com.example.httesti;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Locale;

public class accFragment extends Fragment {

    User user = null;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            user = (User) getArguments().getSerializable("user");
            System.out.println("User loaded!");
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.acc_layout, container,false);
        MainActivity.getInstance().setTitle("Profiili");

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TextView userName = (TextView) view.findViewById(R.id.accNameField);
        TextView homeTown = (TextView) view.findViewById(R.id.homeTown);
        TextView userHeight = (TextView) view.findViewById(R.id.userHeight);
        TextView userWeight = (TextView) view.findViewById(R.id.userWeight);
        TextView userAge = (TextView) view.findViewById(R.id.userAge);
        DBManager DB = new DBManager(getContext().getApplicationContext());
        if(user != null){
            userName.setText(user.getName().toUpperCase(Locale.ROOT));
            homeTown.setText(user.getHomeCity().toUpperCase(Locale.ROOT));
            userAge.setText(user.getAge().toString()+"v");
            userHeight.setText(user.getHeight().toString()+"cm");
            userWeight.setText(user.getWeight().toString()+"kg");

        }


        super.onViewCreated(view, savedInstanceState);
    }


}
