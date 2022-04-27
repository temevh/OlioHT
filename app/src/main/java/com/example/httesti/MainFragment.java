package com.example.httesti;


import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class MainFragment extends Fragment{

    WeatherData w = new WeatherData();

    ArrayList cities = new ArrayList<>();
    ArrayList places = new ArrayList<>();
    ArrayList placeInfo = new ArrayList<>();
    ArrayList placeTypes = new ArrayList();
    ArrayList typesSingles = new ArrayList();
    ArrayList<String> dates = new ArrayList<String>();
    String today = "Today";
    String tomorrow = "Tomorrow";
    Adapter adapter;

    //images and titles for the recyclerView in Home
    RecyclerView dataList;
    List<String> titles;
    String date = "Today"; // default value (gives the weather data for the on going hour)
                        // "tomorrow" value gives weather data from 24 hours forward.

    TextView weatherType;
    TextView Temp;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        MainActivity.getInstance().setTitle("Home");
        dates.clear();
        dates.add(today);
        dates.add(tomorrow);
        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        Temp = (TextView) view.findViewById(R.id.temperature);
        weatherType = (TextView) view.findViewById(R.id.weatherType);

        placesClass pC = placesClass.getInstance();
        pC.addCitiesToArray();

        cities = pC.getCitiesArray();
        places = pC.getPlaceNamesArray();
        placeTypes = pC.getPlaceTypeArray();
        typesSingles = pC.getSingleTypes();

        Spinner spin = view.findViewById(R.id.spinnerCities);
        Spinner place = view.findViewById(R.id.spinnerPlaces);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_spinner_item, cities);
        ArrayAdapter<String> placeAdapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_spinner_item, typesSingles);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        placeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spin.setAdapter(arrayAdapter);
        place.setAdapter(placeAdapter);

        place.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                adapter = new Adapter(getActivity().getApplicationContext(), places, placeTypes);

                GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL , false);
                dataList.setLayoutManager(gridLayoutManager);
                dataList.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String cityChoice = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(adapterView.getContext(), "Selected: " + cityChoice,Toast.LENGTH_LONG).show();
                pC.runPlacesClass(cityChoice);
                placeInfo = pC.getPlaceInfoArray();
                w.setPlace(cityChoice);

                if(date.equals("Today")){
                    w.setURL(w.getParams(), w.getPlace(), 1);
                }else{
                    w.setURL(w.getParams(), w.getPlace(), 24);
                }

                w.loadData();

                //dataList is the recyclerView on Home page
                dataList = getView().findViewById(R.id.dataList);
                titles = new ArrayList<>();

                adapter = new Adapter(getActivity().getApplicationContext(), places, placeTypes);

                GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL , false);
                dataList.setLayoutManager(gridLayoutManager);
                dataList.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //joo
        Spinner spinning = view.findViewById(R.id.spinnerDates);
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_spinner_item, dates);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinning.setAdapter(arrayAdapter2);
        spinning.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                date = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(adapterView.getContext(), "Selected: " + date,Toast.LENGTH_LONG).show();

                if(date.equals("Today")){
                    w.setURL(w.getParams(), w.getPlace(), 1);
                }else{
                    w.setURL(w.getParams(), w.getPlace(), 24);
                }
                w.loadData();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button refreshButton = (Button) view.findViewById(R.id.bSearch);

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshButtonClicked(view);
            }
        });


    }

    public void refreshButtonClicked(View v){
        System.out.println("REFRESHED");

    }




}