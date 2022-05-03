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

public class MainFragment extends Fragment{

    WeatherData wData = new WeatherData();
    placesClass pC = placesClass.getInstance();

    ArrayList cities = new ArrayList<>();
    ArrayList places = new ArrayList<>();
    ArrayList placeInfo = new ArrayList<>();
    ArrayList placeTypes = new ArrayList();
    ArrayList typesSingles = new ArrayList();
    ArrayList<String> dates = new ArrayList<String>();
    String today = "Tänään";
    String tomorrow = "Huomenna";
    Adapter adapter;
    String typeChoice = "All places";
    String cityChoice = "Helsinki";

    //images and titles for the recyclerView in Home
    RecyclerView dataList;
    List<String> titles;
    String date = "Tänään"; // default value (gives the weather data for the on going hour)
                        // "tomorrow" value gives weather data from 24 hours forward.

    TextView weatherType;
    TextView Temp;
    ImageView wImage;

    User currentUser;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            currentUser = (User) getArguments().getSerializable("user");
            System.out.println("User loaded!");
        }
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        MainActivity.getInstance().setTitle("Koti");

        Temp = (TextView) view.findViewById(R.id.temperature);
        weatherType = (TextView) view.findViewById(R.id.weatherType);
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
        wImage = (ImageView) view.findViewById(R.id.weatherImage);
        //dataList is the recyclerView on Home page
        dataList = (RecyclerView) view.findViewById(R.id.dataList);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL , false);
        dataList.setLayoutManager(gridLayoutManager);


        // run the sports activities fetching class
        pC.runPlacesClass(cityChoice,typeChoice);
        // get the relevant arrays
        cities = pC.getCitiesArray();
        places = pC.getPlaceNamesArray();
        placeTypes = pC.getPlaceTypeArray();
        typesSingles = pC.getSingleTypes();


        // set arrays to their spinners
        Spinner spin = view.findViewById(R.id.spinnerCities);
        Spinner place = view.findViewById(R.id.spinnerPlaces);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_spinner_item, cities);
        ArrayAdapter<String> placeAdapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_spinner_item, typesSingles);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        placeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(arrayAdapter);
        place.setAdapter(placeAdapter);


        // ItemSelected listener for the activity type selection
        place.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                adapter = new Adapter(getActivity().getApplicationContext(), places, placeTypes);
                typeChoice = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        // ItemSelected listener for the city selection
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cityChoice = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(adapterView.getContext(), "Valittu kaupunki: " + cityChoice,Toast.LENGTH_SHORT).show();
                wData.setPlace(cityChoice);

                if(date.equals("Tänään")){
                    wData.setURL(wData.getParams(), wData.getPlace(), 1);
                }else{
                    wData.setURL(wData.getParams(), wData.getPlace(), 24);
                }

                wData.loadData();

                titles = new ArrayList<>();
                adapter = new Adapter(getActivity().getApplicationContext(), places, placeTypes);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        // spinner for showing either todays or tommorrows weather data
        Spinner spinning = view.findViewById(R.id.spinnerDates);
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_spinner_item, dates);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinning.setAdapter(arrayAdapter2);

        // ItemSelected listener for the time selection
        spinning.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                date = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(adapterView.getContext(), "Valittu ajankohta: " + date,Toast.LENGTH_SHORT).show();

                if(date.equals("Tänään")){
                    wData.setURL(wData.getParams(), wData.getPlace(), 1);
                }else{
                    wData.setURL(wData.getParams(), wData.getPlace(), 24);
                }
                wData.loadData();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        // button for showing the fetched data according to the user inputs

        Button refreshButton = (Button) view.findViewById(R.id.bSearch);

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshButtonClicked(view);
            }
        });


    }
    // button action method for when the button is clicked
    public void refreshButtonClicked(View v){

        // show all fetched data and relevant info
        Temp.setText(Integer.toString(wData.getTemperature().intValue())+ "°C");
        weatherType.setText(wData.getWeatherType().toUpperCase(Locale.ROOT));

        if(wData.getWeatherSymbol() < 2){
            wImage.setImageResource(R.drawable.ic_sun);
        }
        if(wData.getWeatherSymbol() >= 2 && wData.getWeatherSymbol() < 4){
            wImage.setImageResource(R.drawable.ic_cloudy);
        }
        if(wData.getWeatherSymbol() >= 20 && wData.getWeatherSymbol() < 34){
            wImage.setImageResource(R.drawable.ic_rain);
        }
        if(wData.getWeatherSymbol() >= 40 && wData.getWeatherSymbol() < 54 || wData.getWeatherSymbol() >= 70 && wData.getWeatherSymbol() < 82){
            wImage.setImageResource(R.drawable.ic_snow);

        }
        if(wData.getWeatherSymbol()>60 && wData.getWeatherSymbol()<= 65){
            wImage.setImageResource(R.drawable.ic_thunder);
        }
        if(wData.getWeatherSymbol()>90){
            wImage.setImageResource(R.drawable.ic_fog);
        }
        pC.runPlacesClass(cityChoice, typeChoice);
        dataList.setAdapter(adapter);

    }




}