package com.example.httesti;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FavouritesFragment extends Fragment{


    //images and titles for the recyclerView in Home
    RecyclerView dataList;

    Adapter adapter;
    User user;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Load current user
        if(getArguments() != null){
            user = (User) getArguments().getSerializable("user");
            System.out.println("User loaded!");
        }
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favourite_layout, container, false);
        MainActivity.getInstance().setTitle("Favourites");

        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Add the relevant info to arrays for the cards
        ArrayList<String> places = new ArrayList<>();
        ArrayList<String> placeTypes = new ArrayList<>();
        for (activityPlace ap: user.getFavourites()) {
            places.add(ap.getName());
            placeTypes.add(ap.getPlaceType());
        }


        adapter = new Adapter(getActivity().getApplicationContext(), places, placeTypes);
        dataList = (RecyclerView) view.findViewById(R.id.dataList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL , false);

        dataList.setLayoutManager(gridLayoutManager);
        dataList.setAdapter(adapter);
    }





}

