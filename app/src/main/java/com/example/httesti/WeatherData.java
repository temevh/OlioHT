package com.example.httesti;




import android.os.AsyncTask;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class WeatherData {

    // URL parameters for fetching the data
    private String place;
    private String params = "Temperature,WindSpeedMs,WeatherSymbol3,PrecipitationAmount";
    private String URL;

    // Variables for saving different weather data to
    private Double windSpeed;
    private Double temperature;
    private String weatherType;
    private Double precipitationAmnt;
    private String time;

    //For weathertypes indicated by the WeatherSymbol3 returned from the datasource
    private HashMap<Double,String> weather_types = new HashMap<>();





    public WeatherData(){
        // List of weather types linked with WeatherSymbol3 as the key for each of them
        weather_types.put(1.0, "selkeää");
        weather_types.put(2.0, "puolipilvistä");
        weather_types.put(21.0, "heikkoja sadekuuroja");
        weather_types.put(22.0, "sadekuuroja");
        weather_types.put(23.0, "voimakkaita sadekuuroja");
        weather_types.put(3.0, "pilvistä");
        weather_types.put(31.0, "heikkoa vesisadetta");
        weather_types.put(32.0, "vesisadetta");
        weather_types.put(33.0, "voimakasta vesisadetta");
        weather_types.put(41.0, "heikkoja lumikuuroja");
        weather_types.put(42.0, "lumikuuroja");
        weather_types.put(43.0, "voimakkaita lumikuuroja");
        weather_types.put(51.0, "heikkoa lumisadetta");
        weather_types.put(52.0, "lumisadetta");
        weather_types.put(53.0, "voimakasta lumisadetta");
        weather_types.put(61.0, "ukkoskuuroja");
        weather_types.put(62.0, "voimakkaita ukkoskuuroja");
        weather_types.put(63.0, "ukkosta");
        weather_types.put(64.0, "voimakasta ukkosta");
        weather_types.put(71.0, "heikkoja räntäkuuroja");
        weather_types.put(72.0, "räntäkuuroja");
        weather_types.put(73.0, "voimakkaita räntäkuuroja");
        weather_types.put(81.0, "heikkoa räntäsadetta");
        weather_types.put(82.0, "räntäsadetta");
        weather_types.put(83.0, "voimakasta räntäsadetta");
        weather_types.put(91.0, "utua");
        weather_types.put(92.0, "sumua");


    }

    public void loadData(){
        new LoadWeatherData().execute(URL);
    }

    public void getWeatherData(String url) throws ParserConfigurationException, IOException, SAXException {

            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(url);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getDocumentElement().getElementsByTagName("wfs:member");
            for(int i = 0; i<nodeList.getLength();i++){
                Node measure = nodeList.item(i);
                if(measure.getNodeType() == measure.ELEMENT_NODE) {
                    Element m = (Element) measure;
                    String time = m.getElementsByTagName("BsWfs:Time").item(0).getTextContent();

                    System.out.println("Time: " + time);
                    if (m.getElementsByTagName("BsWfs:ParameterName").item(0).getTextContent().equals("Temperature")) {
                        Double temp = Double.valueOf(m.getElementsByTagName("BsWfs:ParameterValue").item(0).getTextContent());
                        System.out.println("Temp: " + temp);
                        setTemperature(temp);
                    }
                    if (m.getElementsByTagName("BsWfs:ParameterName").item(0).getTextContent().equals("WindSpeedMs")) {
                        Double ws = Double.valueOf(m.getElementsByTagName("BsWfs:ParameterValue").item(0).getTextContent());
                        System.out.println("Wind speed: " + ws);
                        setWindSpeed(ws);
                    }
                    if (m.getElementsByTagName("BsWfs:ParameterName").item(0).getTextContent().equals("WeatherSymbol3")) {
                        Double WeatherSymbol = Double.valueOf(m.getElementsByTagName("BsWfs:ParameterValue").item(0).getTextContent());
                        System.out.println("Weather type: " + weather_types.get(WeatherSymbol));
                        setWeatherType(weather_types.get(WeatherSymbol));

                    }
                    if (m.getElementsByTagName("BsWfs:ParameterName").item(0).getTextContent().equals("PrecipitationAmount")) {
                        Double rain = Double.valueOf(m.getElementsByTagName("BsWfs:ParameterValue").item(0).getTextContent());
                        System.out.println("Amount of precipitation: " + rain);
                        setPrecipitationAmnt(rain);
                    }

                }
            }

    }

    private class LoadWeatherData extends AsyncTask<String,Void,String>{


        @Override
        protected String doInBackground(String... urls) {
            try {
                getWeatherData(urls[0]);
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } finally {
                System.out.println("DONE!");
            }
            return null;
        }


    }



    public String getParams() {
        return this.params;
    }

    public String getPlace() {
        return this.place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public void setPrecipitationAmnt(Double p) {
        this.precipitationAmnt = p;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public void setWeatherType(String weatherType) {
        this.weatherType = weatherType;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Double getPrecipitationAmnt() {
        return precipitationAmnt;
    }

    public Double getTemperature() {
        return temperature;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public String getWeatherType() {
        return weatherType;
    }

    public void setTime(String t) {
        this.time = t;
    }

    public String getTime() {
        return time;
    }

    public void setURL(String params, String place) {
        // Getting time now
        TimeZone tz = TimeZone.getTimeZone("EET");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        df.setTimeZone(tz);
        String nowAsISO = df.format(new Date());

        // Setting the timegap from which data is fetched
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY, 2);
        String endtimeAsISO = df.format(calendar.getTime());

        System.out.println(nowAsISO);
        // Setting up the url with the params
        this.URL = "https://opendata.fmi.fi/wfs?service=WFS&version=2.0.0&request=getFeature&storedquery_id=fmi::forecast::hirlam::surface::point::simple&place="+place+"&parameters="+params+
                "&starttime="+nowAsISO+"&endtime="+endtimeAsISO;
    }

    public String getURL() {
        return URL;
    }
}