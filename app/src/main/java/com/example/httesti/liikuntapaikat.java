package com.example.httesti;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class liikuntapaikat {
    ArrayList<String> cities = new ArrayList<String>();
    ArrayList<String> places = new ArrayList<String>();
    ArrayList<Integer> placeIdArray = new ArrayList<Integer>();
    private String json = null;

    public ArrayList getCitiesArray(){
        return cities;
    }


    public void runLuokka(){
        addCitiesToArray();
        json = getCitySportsPlaceIDs();
        addSportsPlacesToArray(json);

    }

    public void useJSON(String result){
        JSONObject jObject = null;
        JSONObject properties = null;
        try {
            jObject = new JSONObject(result);
            properties = new JSONObject(jObject.getString("properties"));
            boolean toilet = properties.getBoolean("kiosk");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    public void addCitiesToArray(){
        cities.add("Helsinki");
        cities.add("Espoo");
        cities.add("Tampere");
        cities.add("Vantaa");
        cities.add("Oulu");
        cities.add("Turku");
        cities.add("Jyväskylä");
        cities.add("Kuopio");
        cities.add("Lahti");
        cities.add("Pori");
        cities.add("Kouvola");
        cities.add("Joensuu");
        cities.add("Lappeenranta");
        cities.add("Hämeenlinna");
        cities.add("Vaasa");
    }

    public String getCitySportsPlaceIDs(){
        String city = "Vaasa";
        String url = "http://lipas.cc.jyu.fi/api/sports-places?searchString=";
        String searchUrl = url + city;
        String json = getJSON(searchUrl);
        System.out.println(json);
        return json;
    }

    public void addSportsPlacesToArray(String json){
        JSONArray jArray = null;    //Initialize jsonarray
        String handle = null;       //Used to remove the sportsPlaceId part from the line
        try {
            jArray = new JSONArray(json);
            for (int i=0; i<jArray.length(); i++){
                //handle = new JSONObject
                //System.out.println("ID ON " + jArray.get(i));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public String getJSON(String searchUrl){
        String response = null;
        try{
            URL url = new URL(searchUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null){
                sb.append(line).append("\n");
            }
            response = sb.toString();
            in.close();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;

    }
}
