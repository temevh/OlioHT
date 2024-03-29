package com.example.httesti;

import com.google.gson.Gson;
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

public class placesClass {
    ArrayList<String> cities = new ArrayList<String>();    //List of cities
    ArrayList<String> placeNames = new ArrayList<String>();    //List of place names
    ArrayList<Integer> placeIdArray = new ArrayList<Integer>(); //List of IDs for places

    ArrayList<String> placeTypeArray = new ArrayList<>(); //All placetypes
    ArrayList<String> singlePlaceTypes = new ArrayList<>(); //Placetypes without duplicates
    ArrayList<activityPlace> places = new ArrayList<>();


    private String json = null;

    private static placesClass new_instance = null;

    public static placesClass getInstance(){        //Singleton for the class
        if (new_instance == null){
            new_instance = new placesClass();
        }
        return new_instance;
    }



    public ArrayList getCitiesArray(){  //Used to send the city arraylist to MainClass
        return cities;
    }

    public ArrayList getPlaceNamesArray(){  //Used to send the city arraylist to MainClass
        return placeNames;
    }

    public ArrayList<activityPlace> getPlaces() {
        return places;
    }

    public ArrayList getPlaceTypeArray(){return  placeTypeArray;}

    public ArrayList getSingleTypes(){return singlePlaceTypes;}


    public void runPlacesClass(String cityChoice, String typeChoice){       //method used to run the "core" functions/methods of the class
        json = getCitySportsPlaceIDs(cityChoice);
        addSportsPlaceIDtoArray(json);
        cities.clear();
        addCitiesToArray();
        addPlacesToArray(typeChoice);
    }





    public activityPlace selection(String selection){//Used to add the information on a selected place to the info array
        activityPlace selected = null;
        for (activityPlace a : places){
            if(a.getName().equals(selection)){
                selected = a;
            }
        }

        return selected;
    }

    public activityPlace addPlaceInfoToAP(int index){       //Adds the information of a selected place to an array using a JSON
        activityPlace place = new activityPlace();
        int id = placeIdArray.get(index);


        String url = "http://lipas.cc.jyu.fi/api/sports-places/" + id;
        String json = getJSON(url);
        String admin = "N/A";               //Set N/A as the default value for each field
        String email = "N/A";
        String phoneNumber = "N/A";
        String address = "N/A";
        String addInfo ="N/A";
        String placeType = "N/A";
        String name = "N/A";

        try {
            JSONObject jObject = new JSONObject(json);
            JSONObject prop = new JSONObject();

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
                prop = jObject.getJSONObject("properties");        //Get the infoFi value from this JSONobject
            }
            if(prop.has("infoFi")){
                addInfo = prop.getString("infoFi");
            }
            placeType = jObject.getJSONObject("type").getString("name");
            name = jObject.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        place.setName(name);
        place.setAddress(address);
        place.setAdmin(admin);
        place.setPlaceType(placeType);
        place.setAddInfo(addInfo);
        place.setPhoneNumber(phoneNumber);
        place.setEmail(email);
        place.setID(id);
        place.setUrl(url);

        return place;

    }

    public void addPlacesToArray(String typeChoice){   //Adds the sports places of a selected city to an array
        String url = null;
        String response = null;
        String name = null;
        String type = null;
        JsonObject jObject = null;
        places.clear();
        singlePlaceTypes.clear();
        placeNames.clear();
        placeTypeArray.clear();
        singlePlaceTypes.add("Kaikki paikat");

        for (int i = 0; i< placeIdArray.size(); i++){

            url = "http://lipas.cc.jyu.fi/api/sports-places/" + placeIdArray.get(i);
            response = getJSON(url);
            activityPlace ap = addPlaceInfoToAP(i);
            jObject = convertJson(response);
            name = getPlaceName(jObject);
            type = getPlaceType(jObject);
            name = name.substring(1, name.length()-1);   //Removes the " " marks from the place name
            type = type.substring(1, type.length()-1);   //Removes the " " marks from the type name
            if(typeChoice.equals("Kaikki paikat")){         //If selection is "All places" add all places to list
                placeTypeArray.add(type);
                placeNames.add(name);
                places.add(ap);
            }else if(!typeChoice.equals("Kaikki paikat") && typeChoice.equals(type)){   //If some specific place type selected, add only those types to list
                placeTypeArray.add(type);
                placeNames.add(name);
                places.add(ap);
            }
            if (!singlePlaceTypes.contains(type)) {    //Get a list of place types without duplicates, used for the placetype selection
                singlePlaceTypes.add(type);
                places.add(ap);
            }
        }
    }


    public String getPlaceName(JsonObject jObject){   //Method to get the name of the place from the jsonobject
        String name = null;
        name = jObject.get("name").toString();
        return name;
    }

    public String getPlaceType(JsonObject jObject){   //Method to get the type of the place from the jsonobject
        String type = null;
        JsonObject temp = new JsonObject();
        temp = jObject.getAsJsonObject("type");
        type = temp.get("name").toString();
        return type;
    }

    public JsonObject convertJson(String json){    //Convert json string to a JsonObject
        JsonObject convertedJson = null;
        Gson g = new Gson();
        convertedJson = g.fromJson(json, JsonObject.class);
        return convertedJson;
    }

    public void addCitiesToArray(){  //Manually add the 20 biggest cities in Finland to array
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
        cities.add("Seinäjoki");
        cities.add("Rovaniemi");
        cities.add("Mikkeli");
        cities.add("Salo");
        cities.add("Kotka");
        cities.add("Porvoo");
    }

    public String getCitySportsPlaceIDs(String cityChoice){   //Lists the IDs of a citys sport places
        String city = cityChoice;
        //city = "Vaasa";
        String url = "http://lipas.cc.jyu.fi/api/sports-places?searchString=";
        String searchUrl = url + city;
        String json = getJSON(searchUrl);
        return json;
    }

    public void addSportsPlaceIDtoArray(String json){ //Add the IDs of sport places to an arraylist
        JSONArray jArray = null;    //Initialize jsonarray
        JSONObject handle = null;       //Used to remove the sportsPlaceId part from the line
        placeIdArray.clear();
        try {
            jArray = new JSONArray(json);
            for (int i=0; i<jArray.length(); i++){
                handle = jArray.getJSONObject(i);
                int id = handle.getInt("sportsPlaceId");  //ID without sportsPlaceId-part
                placeIdArray.add(id);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getJSON(String searchUrl){    //Method to get the JSON as string
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