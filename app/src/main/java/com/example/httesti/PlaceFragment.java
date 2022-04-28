package com.example.httesti;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;

public class PlaceFragment extends Fragment {

    View view;
    ImageButton backButton;
    User currentUser;

    activityPlace placeInfo = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.place_layout, container,false);
        placeInfo = (activityPlace) getArguments().getSerializable("placeinfo");
        currentUser = (User) getArguments().getSerializable("user");

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TextView placeNameView = view.findViewById(R.id.placeNameView);
        placeNameView.setMovementMethod(new ScrollingMovementMethod());
        TextView placeLocationView = view.findViewById(R.id.placeLocationView);
        TextView placePhoneView = view.findViewById(R.id.placePhoneView);
        TextView placeEmailView = view.findViewById(R.id.placeEmailView);
        TextView placeOwnerView = view.findViewById(R.id.placeOwnerView);
        TextView placeAddinfoView = view.findViewById(R.id.placeAddinfoView);
        placeAddinfoView.setMovementMethod(new ScrollingMovementMethod());
        TextView placeTypeView = view.findViewById(R.id.placeTypeView);
        backButton = (ImageButton) view.findViewById(R.id.buttonBack);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToMainMenu();
            }
        });
        if (placeInfo != null){
            placeNameView.setText(placeInfo.getName());
            placeOwnerView.setText(placeInfo.getAdmin());
            placeEmailView.setText(placeInfo.getEmail());
            placePhoneView.setText(placeInfo.getPhoneNumber());
            placeLocationView.setText(placeInfo.getAddress());
            placeAddinfoView.setText(placeInfo.getAddInfo());
            placeTypeView.setText(placeInfo.getPlaceType());
        }


        super.onViewCreated(view, savedInstanceState);
    }

    public void backToMainMenu(){
        MainActivity.getInstance().loadFragment(MainActivity.getInstance().mainFragment, currentUser);
    }

}
