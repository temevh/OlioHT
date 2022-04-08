package com.example.httesti;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;

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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        readJSON();
        HelloWorld h = new HelloWorld();
        h.Hello();

    }

    public void useJSON(String result){
        JSONObject jObject = null;
        JSONObject properties = null;
        try {
            jObject = new JSONObject(result);
            properties = new JSONObject(jObject.getString("properties"));
            boolean toilet = properties.getBoolean("kiosk");
            System.out.println("############TOILET " + toilet);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void readJSON(){
        String json = getJSON();
        System.out.println(json);
        useJSON(json);

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

