package com.example.httesti;

import android.os.Bundle;
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

    ArrayList placeInfo = new ArrayList();
    String placeName = "N/A";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.place_layout, container,false);
        placeInfo = getArguments().getParcelableArrayList("placeinfo");
        placeName = getArguments().getString("placename");

        String testiteksti = getArguments().getString("testiString");
        System.out.println("TESTI TEKSTI ON " +testiteksti);
        for(int i = 0; i<placeInfo.size();i++){
            System.out.println(placeInfo.get(i));
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TextView placeNameView = (TextView) getView().findViewById(R.id.placeNameView);
        TextView placeLocationView = (TextView) getView().findViewById(R.id.placeLocationView);
        TextView placePhoneView = (TextView) getView().findViewById(R.id.placePhoneView);
        TextView placeEmailView = (TextView) getView().findViewById(R.id.placeEmailView);
        TextView placeOwnerView = (TextView) getView().findViewById(R.id.placeOwnerView);
        TextView placeAddinfoView = (TextView) getView().findViewById(R.id.placeAddinfoView);
        TextView placeTypeView = (TextView) getView().findViewById(R.id.placeTypeView);

        backButton = (ImageButton) view.findViewById(R.id.buttonBack);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToMainMenu();
            }
        });

        //placeNameView.setText(placeInfo.get(0).toString());
        placeNameView.setText(placeName);
        placeOwnerView.setText(placeInfo.get(0).toString());
        placeEmailView.setText(placeInfo.get(1).toString());
        placePhoneView.setText(placeInfo.get(2).toString());
        placeLocationView.setText(placeInfo.get(3).toString());
        placeAddinfoView.setText(placeInfo.get(4).toString());
        placeTypeView.setText(placeInfo.get(5).toString());

        super.onViewCreated(view, savedInstanceState);
    }

    public void backToMainMenu(){
        System.out.println("TOIMII");

        //Bundle bundle = new Bundle();
        //bundle.putSerializable("user", user);
        FragmentManager fragmentManager = MainActivity.getInstance().getSupportFragmentManager();
        Fragment mainFrag = new MainFragment();
        //accFrag.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.flContent, mainFrag).commit();



    }

}
