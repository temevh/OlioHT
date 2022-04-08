package com.example.httesti;

import android.os.StrictMode;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.time.LocalTime;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class WeatherData {
    private String place;
    private String params;

    public WeatherData(){
    }

    public void getWeatherData(){
        try {
            String url = "https://opendata.fmi.fi/wfs/fin?service=WFS&version=2.0.0&request=GetFeature&storedquery_id=fmi::observations::weather::timevaluepair&place="+place+"&parameters="+params+"&";

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            System.out.println(url);
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(url);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getDocumentElement().getElementsByTagName("wml2:point");
            for(int i = 0; i<nodeList.getLength();i++){
                Node measure = nodeList.item(i);
                if(measure.getNodeType() == measure.ELEMENT_NODE){
                    Element m = (Element) measure;
                    String time = m.getElementsByTagName("wml2:time").item(0).getTextContent();
                    Double temp = Double.valueOf(m.getElementsByTagName("wml2:value").item(0).getTextContent());
                    System.out.println("Time: "+time);
                    System.out.println("Temp: "+temp);
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
}
