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
        TextView placeNameView = (TextView) getView().findViewById(R.id.placeNameView);
        TextView placeLocationView = (TextView) getView().findViewById(R.id.placeLocationView);
        TextView placePhoneView = (TextView) getView().findViewById(R.id.placePhoneView);
        TextView placeEmailView = (TextView) getView().findViewById(R.id.placeEmailView);
        TextView placeOwnerView = (TextView) getView().findViewById(R.id.placeOwnerView);
        TextView placeAddinfoView = (TextView) getView().findViewById(R.id.placeAddinfoView);
        TextView placeTypeView = (TextView) getView().findViewById(R.id.placeTypeView);
        if (getArguments() != null){
            placeInfo = getArguments().getParcelableArrayList("placeinfo");
        }


        placeNameView.setText("NAME: " + placeInfo.get(0).toString());

        return view;
    }
}
