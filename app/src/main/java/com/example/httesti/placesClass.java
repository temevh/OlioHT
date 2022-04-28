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

public class placesClass {
    ArrayList<String> cities = new ArrayList<String>();    //List of cities
    ArrayList<String> placeNames = new ArrayList<String>();    //List of place names
    ArrayList<Integer> placeIdArray = new ArrayList<Integer>(); //List of IDs for places
    ArrayList<String> placeInfo = new ArrayList<String>();    //Information for the place
    /*Array for storing the information of a chosen sports place
   Stored based on index
   0 = admin/owner
   1 = email (if exists)
   2 = phone number (if exists)
   3 = address
   4 = additional info (if exists)
   5 = sports place type
    */
    ArrayList<String> placeTypeArray = new ArrayList<>(); //All placetypes
    ArrayList<String> singlePlaceTypes = new ArrayList<>(); //Placetypes without duplicates
    ArrayList<String> placesToShow = new ArrayList<>();

    private String json = null;

    private static placesClass new_instance = null;

    public static placesClass getInstance(){
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

    public ArrayList getPlaceInfoArray(){return  placeInfo;}

    public ArrayList getPlaceTypeArray(){return  placeTypeArray;}

    public ArrayList getSingleTypes(){return singlePlaceTypes;}

    public ArrayList getPlacesToShow(){return placesToShow;}

    public void runPlacesClass(String cityChoice, String typeChoice){       //wannabe MainClass for this class, used to call the methods/functions
        json = getCitySportsPlaceIDs(cityChoice);
        addSportsPlaceIDtoArray(json);
        addPlaceNamesToArray(typeChoice);


    }

    public ArrayList selection(String selection){       //Used to add the information on a selected place to the info array
        String select = selection;
        int index = 0;  //Initialization of index variable
        index = placeNames.indexOf(select);
        addPlaceInfoToArray(index);
        return placeInfo;
    }

    public void addPlaceInfoToArray(int index){       //Adds the information of a selected place to an array using a JSON
        int id = placeIdArray.get(index);
        String url = "http://lipas.cc.jyu.fi/api/sports-places/" + id;
        System.out.println("URLI ON " +url);
        String json = getJSON(url);
        String admin = "N/A";
        String email = "N/A";
        String phoneNumber = "N/A";
        String address = "N/A";
        String addInfo ="N/A";
        String placeType = "N/A";
        //String name = "N/A";

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
            /*if(jObject.has("properties")){
                addInfo = jObject.getJSONObject("properties").getString("infoFi");
            }*/
            placeType = jObject.getJSONObject("type").getString("name");
            //name = jObject.getString("name");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        placeInfo.add(0, admin);
        placeInfo.add(1, email);
        placeInfo.add(2, phoneNumber);
        placeInfo.add(3, address);
        placeInfo.add(4, addInfo);
        placeInfo.add(5, placeType);

    }

    public void addPlaceNamesToArray(String typeChoice){   //Adds the sports places of a selected city to an array
        String url = null;
        String response = null;
        String name = null;
        String type = null;
        JsonObject jObject = null;
        singlePlaceTypes.clear();
        placeNames.clear();
        placeTypeArray.clear();
        singlePlaceTypes.add("All places");

        for (int i = 0; i< placeIdArray.size(); i++){
            url = "http://lipas.cc.jyu.fi/api/sports-places/" + placeIdArray.get(i);
            //System.out.println("LISÄTTY ID" + placeIdArray.get(i));
            response = getJSON(url);

            jObject = convertJson(response);
            name = getPlaceName(jObject);
            type = getPlaceType(jObject);
            name = name.substring(1, name.length()-1);   //Removes the " " marks from the place name
            type = type.substring(1, type.length()-1);
            if(typeChoice.equals("All places")){
                placeTypeArray.add(type);
                placeNames.add(name);
                placesToShow.add(name);
            }else if(!typeChoice.equals("All places") && typeChoice.equals(type)){
                placeTypeArray.add(type);
                placeNames.add(name);
                placesToShow.add(name);
            }
            if (!singlePlaceTypes.contains(type)) {
                singlePlaceTypes.add(type);
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
        cities.clear();
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
        //placeIdArray.clear();
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