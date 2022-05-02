package com.example.httesti;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;

public class PlaceFragment extends Fragment {

    View view;
    ImageButton backButton;
    ToggleButton star;
    User currentUser;
    DBManager DB;

    activityPlace placeInfo = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.place_layout, container,false);
        if(getArguments() != null){
            placeInfo = (activityPlace) getArguments().getSerializable("placeinfo");  //one place object which contains all relevant info
            currentUser = (User) getArguments().getSerializable("user");//Get user(if logged in)
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TextView placeNameView = view.findViewById(R.id.placeNameView);
        placeNameView.setMovementMethod(new ScrollingMovementMethod());
        TextView placeLocationView = view.findViewById(R.id.placeLocationView);
        TextView placePhoneView = view.findViewById(R.id.placePhoneView);
        TextView placeEmailView = view.findViewById(R.id.placeEmailView);
        TextView placeOwnerView = view.findViewById(R.id.placeOwnerView);
        TextView placeAddinfoView = view.findViewById(R.id.placeAddinfoView);
        placeAddinfoView.setMovementMethod(new ScrollingMovementMethod());
        TextView placeTypeView = view.findViewById(R.id.placeTypeView);

        DB = new DBManager(getContext().getApplicationContext());


        //Set up the back button
        backButton = (ImageButton) view.findViewById(R.id.buttonBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToPrevious();
            }
        });

        star  = (ToggleButton) view.findViewById(R.id.favoriteButton);
        // If we are at Favourites window, don't show the star (it's unnecessary)
        if (currentUser != null && MainActivity.getInstance().getTitle().equals("Suosikit")){
            star.setVisibility(View.INVISIBLE);
        }

        // If no user has been logged in, don't show the favourite star
        if(currentUser == null){
            star.setVisibility(View.INVISIBLE);
        }
        if(currentUser != null && MainActivity.getInstance().getTitle().equals("Koti")){

            Boolean exists = false;
            // Check if the place exists already in users favourites
            for (activityPlace ap : currentUser.getFavourites()){
                if(placeInfo != null){
                    if (ap.getName().equals(placeInfo.getName())){
                        exists = true;
                    }
                }

            }
            // Show the star filled if it exists and set the onClick method accordingly
            if(exists){
                star.setBackgroundResource(R.drawable.ic_star_full);
                star.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // If place exists in users favourites, reclicking the button removes it from favourites
                        removeFromFavourites(view);
                    }
                });
            }else{
                star.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // If place doesn't exist in users favourites, clicking the button adds it to favourites
                        favoriteClicked(view);
                    }
                });
            }
        }

        // If info is available, show it on the screen
        if (placeInfo != null){
            placeNameView.setText(placeInfo.getName());
            placeOwnerView.setText(placeInfo.getAdmin());
            placeEmailView.setText(placeInfo.getEmail());
            placePhoneView.setText(placeInfo.getPhoneNumber());
            placeLocationView.setText(placeInfo.getAddress());
            placeAddinfoView.setText(placeInfo.getAddInfo());
            placeTypeView.setText(placeInfo.getPlaceType());
        }
        super.onViewCreated(view, savedInstanceState);
    }

    public void backToPrevious(){     //Method for going back to home/main menu when the back button in the app is clicked
        if(MainActivity.getInstance().getTitle().equals("Koti")){
            MainActivity.getInstance().loadFragment(MainActivity.getInstance().mainFragment, currentUser);
        }
        if(MainActivity.getInstance().getTitle().equals("Suosikit")){
            MainActivity.getInstance().loadFragment(new FavouritesFragment(), currentUser);
        }
    }

    public void favoriteClicked(View view){            //Adding the current place to favorites

        Toast.makeText(getActivity(), "Lisätty suosikeihin", Toast.LENGTH_SHORT).show();
        star.setBackgroundResource(R.drawable.ic_star_full);
        currentUser.addToFavourites(placeInfo);
        DB.updateUser(currentUser);
    }

    public void removeFromFavourites(View view){        //Removing the current place from favorites
        Toast.makeText(getActivity(), "Poistettu suosikeista", Toast.LENGTH_SHORT).show();
        star.setBackgroundResource(R.drawable.ic_star_empty);
        currentUser.removeFromFavourites(placeInfo);
        DB.updateUser(currentUser);
    }



}

