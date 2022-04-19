package com.example.httesti;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment{

    ArrayList cities = new ArrayList<>();
    ArrayList<String> places = new ArrayList<>();
    ArrayList placeInfo = new ArrayList<>();

    //images and titles for the recyclerView in Home
    RecyclerView dataList;
    List<String> titles;
    List<Integer> images;
    String temperatures;
    String weatherType;
    Adapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        liikuntapaikat teemuTrial = new liikuntapaikat();
        //teemuTrial.runLuokka("Helsinki");
        teemuTrial.addCitiesToArray();

        cities = teemuTrial.getCitiesArray();

        WeatherData w = new WeatherData();

        Spinner spin = view.findViewById(R.id.spinnerCities);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_spinner_item, cities);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spin.setAdapter(arrayAdapter);

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String cityChoice = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(adapterView.getContext(), "Selected: " + cityChoice,Toast.LENGTH_LONG).show();
                teemuTrial.runLuokka(cityChoice);
                places = teemuTrial.getPlaceNamesArray();



                placeInfo = teemuTrial.getPlaceInfoArray();
                w.setPlace(cityChoice);
                w.setURL(w.getParams(),w.getPlace());
                w.loadData();

                //dataList is the recyclerView on Home page
                dataList = getView().findViewById(R.id.dataList);
                titles = new ArrayList<>();
                images = new ArrayList<>();

                for (int k = 0; k < places.size(); k++){
                    images.add(R.drawable.ic_baseline_cloud_24);
                }
                //titles = places;

                adapter = new Adapter(getActivity(), places, images, temperatures, weatherType);

                GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL , false);
                dataList.setLayoutManager(gridLayoutManager);
                dataList.setAdapter(adapter);
                //images = null;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

}
