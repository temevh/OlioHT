package com.example.httesti;


import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

public class MainFragment extends Fragment{

    ArrayList cities = new ArrayList<>();
    ArrayList places = new ArrayList<>();
    ArrayList placeInfo = new ArrayList<>();
    ArrayList sports = new ArrayList<>();
    ArrayList placeTypes = new ArrayList();
    ArrayList<String> dates = new ArrayList<String>();
    String today = "Today";
    String tomorrow = "Tomorrow";
    Adapter adapter;

    //images and titles for the recyclerView in Home
    RecyclerView dataList;
    List<String> titles;
    List<Integer> images;
    Double temperatures;
    String wType;
    String date = today; // default value (gives the weather data of NOW)

    TextView weatherType;
    TextView Temp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        dates.add(today);
        dates.add(tomorrow);
        for (int i = 0; i<100; i++ ){
            sports.add("KuntosaliJoojoo");
        }
        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        Temp = (TextView) view.findViewById(R.id.temperature);
        weatherType = (TextView) view.findViewById(R.id.weatherType);

        liikuntapaikat teemuTrial = liikuntapaikat.getInstance();
        //teemuTrial.runLuokka("Helsinki");
        teemuTrial.addCitiesToArray();

        cities = teemuTrial.getCitiesArray();
        places = teemuTrial.getPlaceNamesArray();
        placeTypes = teemuTrial.getPlaceTypeArray();

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
                placeInfo = teemuTrial.getPlaceInfoArray();
                w.setPlace(cityChoice);

                if(date.equals("Today")){
                    w.setURL(w.getParams(), w.getPlace(), 1);
                }else{
                    w.setURL(w.getParams(), w.getPlace(), 24);
                }

                w.loadData();

                temperatures = w.getTemperature();
                wType = w.getWeatherType();

                if(temperatures != null){
                    Temp.setText(temperatures.intValue() + " °C");
                }
                if(temperatures != null){
                   weatherType.setText(wType.toUpperCase(Locale.ROOT));
                }

                System.out.println(temperatures);
                System.out.println(wType);
                //dataList is the recyclerView on Home page
                dataList = getView().findViewById(R.id.dataList);
                titles = new ArrayList<>();
                images = new ArrayList<>();

                for (int k = 0; k < places.size(); k++){
                    images.add(R.drawable.ic_baseline_cloud_24);
                }

                adapter = new Adapter(getActivity().getApplicationContext(), places, images, sports);

                GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL , false);
                dataList.setLayoutManager(gridLayoutManager);
                dataList.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Spinner spinning = view.findViewById(R.id.spinnerDates);
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_spinner_item, dates);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinning.setAdapter(arrayAdapter2);
        spinning.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                date = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(adapterView.getContext(), "Selected: " + date,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }




}