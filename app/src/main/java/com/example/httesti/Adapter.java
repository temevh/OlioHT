package com.example.httesti;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    List<String> titles;
    List<Integer> images;
    List<String> sports;
    LayoutInflater inflater;
    MainActivity MA = MainActivity.getInstance();

    public Adapter(Context ctx, List<String> titles, List<Integer> images, List<String> sports){
        this.titles = titles;
        this.images = images;
        this.sports = sports;
        this.inflater = LayoutInflater.from(ctx);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.grid_layout, parent, false);
        return new ViewHolder(view);
    }

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

        public ViewHolder(@NonNull View cardView){
            super(cardView);
            sport = itemView.findViewById(R.id.placeType);
            title = itemView.findViewById(R.id.placeName);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    title.getText().toString();
                    MA.onItemClicked(v, title.getText().toString());
                    System.out.println(title.getText().toString() + " hello " + sport.getText().toString());
                }
            });
        }
    }

}