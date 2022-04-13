package com.example.httesti;



import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class WeatherData {
    private String place = "Espoo";
    private String params = "Temperature,WindSpeedMs,WeatherSymbol3,PrecipitationAmount";

    private Double windSpeed;
    private Double temperature;
    private String weatherType;
    private Double precipitationAmnt;
    private String time;
    //For weathertypes indicated by the WeatherSymbol3 returned from the datasource
    private HashMap<Double,String> weather_types = new HashMap<>();


    public WeatherData(){

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

    public void getWeatherData(){
        try {

            TimeZone tz = TimeZone.getTimeZone("EET");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
            df.setTimeZone(tz);
            String nowAsISO = df.format(new Date());
            String endtimeAsISO = df.format(addHoursToJavaUtilDate(new Date(), 2));
            System.out.println(nowAsISO);
            String url = "https://opendata.fmi.fi/wfs?service=WFS&version=2.0.0&request=getFeature&storedquery_id=fmi::forecast::hirlam::surface::point::simple&place="+place+"&parameters="+params+"&starttime="+nowAsISO+"&endtime="+endtimeAsISO;
            System.out.println(url);
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(url);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getDocumentElement().getElementsByTagName("wfs:member");
            for(int i = 0; i<nodeList.getLength();i++){
                Node measure = nodeList.item(i);
                if(measure.getNodeType() == measure.ELEMENT_NODE){
                    Element m = (Element) measure;
                    String time = m.getElementsByTagName("BsWfs:Time").item(0).getTextContent();

                    System.out.println("Time: "+time);
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
                        setPrecipitationAmnt(rain); }



                }
            }


        } catch (ParserConfigurationException parserConfigurationException) {
            parserConfigurationException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (SAXException saxException) {
            saxException.printStackTrace();
        }
        finally{
            System.out.println("DONE!");
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

    public void setPrecipitationAmnt(Double precipitationAmnt) {
        this.precipitationAmnt = precipitationAmnt;
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

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public Date addHoursToJavaUtilDate(Date date, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }
}
