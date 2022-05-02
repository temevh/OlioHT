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
import java.util.Locale;

public class weatherAdapter extends RecyclerView.Adapter<weatherAdapter.ViewHolder> {
    // Declaring needed lists
    List<String> wtypes;
    List<String> temps;
    List<String> rains;
    List<String> winds;
    List<String> times;
    LayoutInflater inflater;

    //Creating the adapter for the lists
    public weatherAdapter(Context ctx, List<String> wtypes, List<String> temps, List<String> rain, List<String> winds, List<String> times){
        this.wtypes = wtypes;
        this.temps = temps;
        this.rains = rain;
        this.winds = winds;
        this.times = times;
        this.inflater = LayoutInflater.from(ctx);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.weathergrid_layout, parent, false);
        return new ViewHolder(view);
    }

    //setting the text for the cardView
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // Set weather texts to their respected containers
        holder.wtype.setText(wtypes.get(position).toUpperCase(Locale.ROOT));
        holder.temperature.setText(temps.get(position));
        holder.rain.setText(rains.get(position));
        holder.wind.setText(winds.get(position));
        holder.time.setText(times.get(position));
        Integer timeAsInt = Integer.parseInt(times.get(position).substring(0,1));
        // Set the weather image based on what kind of weather is forecasted
        if(wtypes.get(position).equals("selkeää")){
            if(timeAsInt<22 && timeAsInt>9){
                holder.wIMG.setImageResource(R.drawable.ic_sun);
            }else {
                holder.wIMG.setImageResource(R.drawable.ic_moon);
            }

        }

        if(wtypes.get(position).equals("puolipilvistä") || wtypes.get(position).equals("pilvistä")){
            holder.wIMG.setImageResource(R.drawable.ic_cloudy);
        }
        if(wtypes.get(position).equals("heikkoja sadekuuroja")
                || wtypes.get(position).equals("sadekuuroja")
                || wtypes.get(position).equals("voimakkaita sadekuuroja")
                || wtypes.get(position).equals("heikkoa vesisadetta")
                || wtypes.get(position).equals("vesisadetta")
                || wtypes.get(position).equals("voimakasta vesisadetta")){
            holder.wIMG.setImageResource(R.drawable.ic_rain);

        }
        if(wtypes.get(position).equals("heikkoja lumikuuroja")
                || wtypes.get(position).equals("lumikuuroja")
                ||wtypes.get(position).equals("voimakkaita lumikuuroja")
                || wtypes.get(position).equals("heikkoa lumisadetta")
                || wtypes.get(position).equals("lumisadetta")
                || wtypes.get(position).equals("voimakasta lumisadetta")
                || wtypes.get(position).equals("heikkoja räntäkuuroja")
                || wtypes.get(position).equals("räntäkuuroja")
                || wtypes.get(position).equals("voimakkaita räntäkuuroja")
                || wtypes.get(position).equals("heikkoa räntäsadetta")
                || wtypes.get(position).equals("räntäsadetta")
                || wtypes.get(position).equals("voimakasta räntäsadetta")){
            holder.wIMG.setImageResource(R.drawable.ic_snow);
        }
        if(wtypes.get(position).equals("ukkoskuuroja")
                || wtypes.get(position).equals("voimakkaita ukkoskuuroja")
                || wtypes.get(position).equals("ukkosta")
                || wtypes.get(position).equals("voimakasta ukkosta")){
            holder.wIMG.setImageResource(R.drawable.ic_thunder);
        }
        if(wtypes.get(position).equals("utua") || wtypes.get(position).equals("sumua")){
            holder.wIMG.setImageResource(R.drawable.ic_fog);
        }

    }




    @Override
    public int getItemCount() {
        return wtypes.size();
    }
    // Creating the view holder
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView wtype;
        TextView rain;
        TextView temperature;
        TextView wind;
        TextView time;
        ImageView wIMG;


        public ViewHolder(@NonNull View cardView){
            super(cardView);
            time = itemView.findViewById(R.id.time);
            wtype = itemView.findViewById(R.id.typeofWeather);
            temperature = itemView.findViewById(R.id.temp);
            rain = itemView.findViewById(R.id.rain);
            wind = itemView.findViewById(R.id.wind);
            wIMG = itemView.findViewById(R.id.weatherIMG);
            }
        }
    }



