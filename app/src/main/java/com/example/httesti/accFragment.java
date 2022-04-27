package com.example.httesti;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
        MainActivity.getInstance().setTitle("Profile");

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TextView userName = (TextView) view.findViewById(R.id.accNameField);
        DBManager DB = new DBManager(getContext().getApplicationContext());
        if(user != null){
            userName.setText(user.getName());
            System.out.println("Fetched users name is: "+user.getName());
        }


        super.onViewCreated(view, savedInstanceState);
    }


}
