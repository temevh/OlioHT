package com.example.httesti;

import android.os.Bundle;
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

public class WeatherFragment extends Fragment {


    RecyclerView weatherList;
    Spinner location;
    TextView locText;
    Button searchButton;
    ImageView weatherImage;

    WeatherData wData = new WeatherData();
    placesClass PC = placesClass.getInstance();
    weatherAdapter wAdapter;
    User user;

    ArrayList<String> locations;
    String cityChoice = "Helsinki"; // default value




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if(getArguments() != null){
            user = (User) getArguments().getSerializable("user");
            System.out.println("User loaded!");
        }*/
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.weather_fragment, container, false);
        MainActivity.getInstance().setTitle("Weather");

        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        weatherList = (RecyclerView) view.findViewById(R.id.weatherView);
        location = (Spinner) view.findViewById(R.id.placesSpinner);
        locText = (TextView) view.findViewById(R.id.locationText);
        searchButton = (Button) view.findViewById(R.id.searchButton);

        locations = PC.getCitiesArray();



        ArrayAdapter locationAA = new ArrayAdapter(getActivity().getBaseContext(), android.R.layout.simple_spinner_item, locations);
        locationAA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        location.setAdapter(locationAA);


        location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cityChoice = adapterView.getItemAtPosition(i).toString();
                locText.setText("Selected: "+cityChoice);

                wData.setPlace(cityChoice);
                wData.setURL(wData.getParams(), wData.getPlace(), 24);
                wData.loadData();



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                wAdapter = new weatherAdapter(getContext().getApplicationContext(), wData.getWtypes(), wData.getTemps(), wData.getPrecips(), wData.getWinds(), wData.getTimes());
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL , false);
                weatherList.setLayoutManager(gridLayoutManager);
                weatherList.setAdapter(wAdapter);
            }
        });


    }
}