package com.example.httesti;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    List<String> titles;
    List<Integer> images;
    List<String> sports;
    String temperatures;
    String weatherType;
    LayoutInflater inflater;

    public Adapter(Context ctx, List<String> titles, List<Integer> images, String temperatures, String weatherType){
        this.titles = titles;
        this.images = images;
        this.sports =
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
        holder.sport.setText(sport.get(position));
        //holder.gridIcon.setImageResource(images.get(position));
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView sport;
        ImageView gridIcon;

        public ViewHolder(@NonNull View itemView){

            super(itemView);
            title = itemView.findViewById(R.id.placeName);
            sport = itemView.findViewById(R.id.placeType);
            //gridIcon = itemView.findViewById(R.id.weatherIcon);
        }
    }
}
//yo