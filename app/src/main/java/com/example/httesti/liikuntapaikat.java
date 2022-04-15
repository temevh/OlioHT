package com.example.httesti;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class liikuntapaikat {
    ArrayList<String> cities = new ArrayList<String>();    //List of cities
    ArrayList<String> placeNames = new ArrayList<String>();    //List of place names
    ArrayList<Integer> placeIdArray = new ArrayList<Integer>(); //List of IDs for places
    ArrayList<String> placeInfo = new ArrayList<String>();
    /*Array for storing the information of a chosen sports place
    Stored based on index
    0 = admin/owner
    1 = email (if exists)
    2 = phone number (if exists)
    3 = address
    4 = additional info (if exists)
    5 = sports place type
     */

    private String json = null;

    public ArrayList getCitiesArray(){  //Used to send the city arraylist to MainClass
        return cities;
    }

    public ArrayList getPlaceNamesArray(){  //Used to send the city arraylist to MainClass
        return placeNames;
    }

    public void runLuokka(String cityChoice){       //wannabe MainClass for this class, used to call the methods/functions
        addCitiesToArray();
        json = getCitySportsPlaceIDs(cityChoice);
        addSportsPlaceIDtoArray(json);
        addPlaceNamesToArray();
        selection();

    }


    public void selection(){
        /*for (int i = 0; i< placeNames.size(); i++){
            System.out.println(placeNames.get(i));
        }*/
        String select = "Elisa stadion";
        int index = 0;
        index = placeNames.indexOf(select);
        System.out.println("PAIKAN "+ select +" INDEKSI ON " + index);
        addPlaceInfoToArray(index);
    }

    public void addPlaceInfoToArray(int index){
        int id = placeIdArray.get(index);
        id = 607426;
        String url = "http://lipas.cc.jyu.fi/api/sports-places/" + id;
        String json = getJSON(url);
        String  admin = "N/A";
        String email = "N/A";
        String phoneNumber = "N/A";
        String address = "N/A";
        String addInfo ="N/A";
        String placeType = "N/A";

        try {
            JSONObject jObject = new JSONObject(json);
            if(jObject.has("admin")){
                admin = jObject.getString("admin");
            }
            if(jObject.has("email")){
                email = jObject.getString("email");
            }
            if(jObject.has("phoneNumber")){
                phoneNumber = jObject.getString("phoneNumber");
            }
            if(jObject.has("location")){
                address = jObject.getJSONObject("location").getString("address");
            }
            if(jObject.has("properties")){
                addInfo = jObject.getJSONObject("properties").getString("infoFi");
            }
            placeType = jObject.getJSONObject("type").getString("name");


        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("ADMIN ON " + admin);
        System.out.println("EMAIL ON " + email);
        System.out.println("PHONENUMBER ON " + phoneNumber);
        System.out.println("ADDRESS ON " + address);
        System.out.println("ADDINFO ON " + addInfo);
        System.out.println("TYPE ON " + placeType);
        System.out.println("DONE");

    }

    public void checkSportsPlaceType(String typeCode){
        String url = "http://lipas.cc.jyu.fi/api/sports-place-types/" + typeCode;
        System.out.println(url);
        String json = getJSON(url);
        String type ="";

        try {
            JSONObject jObject = new JSONObject(json);
            type = jObject.getString("name");
            System.out.println("TYYPPI ON " + type);


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    public void addPlaceNamesToArray(){
        String url = null;
        String response = null;
        String name = null;
        JsonObject jObject = null;

        for (int i = 0; i< placeIdArray.size(); i++){
            url = "http://lipas.cc.jyu.fi/api/sports-places/" + placeIdArray.get(i);
            response = getJSON(url);

            jObject = convertJson(response);
            name = getPlaceName(jObject);
            name = name.substring(1, name.length()-1);
            placeNames.add(name);
        }

    }

    public String getPlaceName(JsonObject jObject){
        String name = null;
        name = jObject.get("name").toString();
        return name;
    }

    public JsonObject convertJson(String json){    //Convert json string to a JsonObject, not sure if this method really is necessary
        JsonObject convertedJson = null;
        Gson g = new Gson();
        convertedJson = g.fromJson(json, JsonObject.class);
        return convertedJson;
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

    public String getCitySportsPlaceIDs(String cityChoice){
        String city = cityChoice;
        city = "Vaasa";
        String url = "http://lipas.cc.jyu.fi/api/sports-places?searchString=";
        String searchUrl = url + city;
        String json = getJSON(searchUrl);
        return json;
    }

    public void addSportsPlaceIDtoArray(String json){ //Add the IDs of sport places to an arraylist
        JSONArray jArray = null;    //Initialize jsonarray
        JSONObject handle = null;       //Used to remove the sportsPlaceId part from the line
        try {
            jArray = new JSONArray(json);
            for (int i=0; i<jArray.length(); i++){
                handle = jArray.getJSONObject(i);
                int id = handle.getInt("sportsPlaceId");  //ID without sportsPlaceId
                placeIdArray.add(id);
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