package com.example.httesti;

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


    ArrayList<String> kaupungit = new ArrayList<String>();

    public void lisaaKaupungitListaan(){
        kaupungit.add("Helsinki");
        kaupungit.add("Espoo");
        kaupungit.add("Tampere");
        kaupungit.add("Vantaa");
        kaupungit.add("Oulu");
        kaupungit.add("Turku");
        kaupungit.add("Jyväskylä");
        kaupungit.add("Kuopio");
        kaupungit.add("Lahti");
        kaupungit.add("Pori");
        kaupungit.add("Kouvola");
        kaupungit.add("Joensuu");
        kaupungit.add("Lappeenranta");
        kaupungit.add("Hämeenlinna");
        kaupungit.add("Vaasa");

    }



    public void readJSON(){
        String json = getJSON();
        System.out.println(json);
        useJSON(json);
        lisaaKaupungitListaan();
    }

    public String getJSON(){
        String response = null;
        String urlid = "601699";
        try{
            URL url = new URL("http://lipas.cc.jyu.fi/api/sports-places/"+urlid);
            //URL url = new URL("http://lipas.cc.jyu.fi/api/sports-places?searchString=Lappeenranta\n");
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
