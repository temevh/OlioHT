package com.example.httesti;


import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    List<String> titles;    //the names of the sportPlaces
    List<String> sports;    //type of sport that can be done in the place
    LayoutInflater inflater;
    MainActivity MA = MainActivity.getInstance();

    //Creating the adapter for the lists
    public Adapter(Context ctx, List<String> titles, List<String> sports){
        this.titles = titles;
        this.sports = sports;
        this.inflater = LayoutInflater.from(ctx);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.grid_layout, parent, false);
        return new ViewHolder(view);
    }

    //setting the text for the cardView
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(titles.get(position));
        holder.sport.setText(sports.get(position));
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView sport;
        String placeName;
        String placeType;

        public ViewHolder(@NonNull View cardView){
            super(cardView);
            sport = itemView.findViewById(R.id.placeType);
            title = itemView.findViewById(R.id.placeName);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                // OnClickListener for the cards in recyclerView
                public void onClick(View v) {
                    //onClick gets the tittle on the card and send it to the mainActivity.java and opens a fragment
                    placeName = title.getText().toString();
                    placeType = sport.getText().toString();
                    MA.onItemClicked(v, placeName);
                }
            });
        }
    }

}