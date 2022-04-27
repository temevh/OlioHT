package com.example.httesti;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;

import java.util.ArrayList;

public class profileCreationFragment extends Fragment {

    ArrayList cities = new ArrayList<>();
    String home;

    EditText name, age, weight, height;
    Button save;
    Spinner homeCity;
    User user = null;

    DBManager Profiles;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            user = (User) getArguments().getSerializable("user");
            System.out.println("User loaded!");
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_creation_fragment, container,false);
        MainActivity.getInstance().setTitle("Profile Creation");

        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        name = (EditText) view.findViewById(R.id.name);
        age = (EditText) view.findViewById(R.id.age);
        weight = (EditText) view.findViewById(R.id.weight);
        height = (EditText) view.findViewById(R.id.height);
        save = (Button) view.findViewById(R.id.btnsave);
        homeCity = (Spinner) view.findViewById(R.id.spinnerHome);

        Profiles = new DBManager(getContext().getApplicationContext());


        placesClass pC = placesClass.getInstance();
        cities = pC.getCitiesArray();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_spinner_item, cities);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        homeCity.setAdapter(arrayAdapter);

        homeCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                home = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user == null || home.equals("") || age.getText().toString().equals("") || height.getText().toString().equals("") || weight.getText().toString().equals("") || name.getText().toString().equals("")){
                    Toast.makeText(getContext().getApplicationContext(), "Please enter all the fields", Toast.LENGTH_SHORT).show();
                }else{
                    user.setHomeCity(home);
                    user.setName(name.getText().toString());
                    user.setAge(Integer.parseInt(age.getText().toString()));
                    user.setHeight(Double.parseDouble(height.getText().toString()));
                    user.setWeight(Double.parseDouble(weight.getText().toString()));

                    Profiles.insertProfile(user);

                    Toast.makeText(getContext().getApplicationContext(), "Profile creation successful", Toast.LENGTH_SHORT).show();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user", user);
                    FragmentManager fragmentManager = MainActivity.getInstance().getSupportFragmentManager();
                    Fragment accFrag = new accFragment();
                    accFrag.setArguments(bundle);
                    fragmentManager.beginTransaction().replace(R.id.flContent, accFrag).commit();
                }

            }
        });

    }
}
