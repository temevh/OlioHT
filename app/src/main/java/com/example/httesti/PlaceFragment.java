package com.example.httesti;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class PlaceFragment extends Fragment {

    ArrayList placeInfo = new ArrayList();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.place_layout, container,false);
        placeInfo = getArguments().getParcelableArrayList("placeinfo");


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

        placeNameView.setText("NAME: " + placeInfo.get(0));
        placeOwnerView.setText("OWNER: " + placeInfo.get(1));
        placeEmailView.setText("EMAIL: " + placeInfo.get(2));
        placePhoneView.setText("PHONE: " + placeInfo.get(3));
        placeLocationView.setText("ADDRESS: " + placeInfo.get(4));
        placeAddinfoView.setText("ADDITIONAL INFO: " + placeInfo.get(5));
        placeTypeView.setText(placeInfo.get(6).toString());

        super.onViewCreated(view, savedInstanceState);
    }
}
