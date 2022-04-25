package com.teknikindustries.yahooweather;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

class display {
    public static String city;
    public static String country;
    public static String region;
    public static String pres;
    public static String dist;
    public static String spd;
    public static String temp;
    public static String chill;
    public static String dir;
    public static String spdw;
    public static String hum;
    public static String vis;
    public static String rise;
    public static String set;
    public void getdata(Document doc) {
        try {
            doc.getDocumentElement().normalize();
            NodeList l = doc.getElementsByTagName("rss");
            for (int i = 0; i < l.getLength(); i++) {
                Node n = l.item(i);
                if (n.getNodeType() == 1) {
                    Element e = (Element) n;
                    NodeList l1 = e.getElementsByTagName("yweather:location");
                    for (int j = 0; j < l1.getLength(); j++) {
                        Node n1 = l1.item(j);
                        Element e1 = (Element) n1;
                        city = e1.getAttribute("city");

                        //Element e1 = (Element) n1;
                        country = e1.getAttribute("country");
                        // Element e1 = (Element) n1;
                        region = e1.getAttribute("region");
                    }
                    NodeList l2 = e.getElementsByTagName("yweather:units");
                    for (int j = 0; j < l2.getLength(); j++) {
                        Node n2 = l2.item(j);
                        Element e2 = (Element) n2;
                        temp = e2.getAttribute("temperature");
                        dist = e2.getAttribute("distance");
                        pres = e2.getAttribute("pressure");
                        spd = e2.getAttribute("speed");

                    }
                    NodeList l3 = e.getElementsByTagName("yweather:wind");
                    for (int j = 0; j < l3.getLength(); j++) {
                        Node n3 = l3.item(j);
                        Element e3 = (Element) n3;
                        chill = e3.getAttribute("chill");
                        dir = e3.getAttribute("direction");
                        spdw = e3.getAttribute("speed");
                    }
                    NodeList l4 = e.getElementsByTagName("yweather:atmosphere");
                    for (int j = 0; j < l4.getLength(); j++) {
                        Node n4 = l4.item(j);
                        Element e4 = (Element) n4;
                        hum = e4.getAttribute("humidity");
                        vis = e4.getAttribute("visibility");

                    }
                    NodeList l5 = e.getElementsByTagName("yweather:astronomy");
                    for (int j = 0; j < l5.getLength(); j++) {
                        Node n5 = l5.item(j);
                        Element e5=(Element)n5;
                        rise = e5.getAttribute("sunrise");
                        set = e5.getAttribute("sunset");
                    }
                }


            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public String getCity()
    {
        return city;
    }
    public String getCountry()
    {
        return country;
    }
    public String getRegion()
    {
        return region;
    }
    public String getDist()
    {
        return dist;
    }
    public String getPres()
    {
        return pres;
    }
    public String getSpd()
    {
        return spd;
    }
    public String getTemp()
    {
        return temp;
    }
    public String getChill()
    {
        return chill;
    }
    public String getSpdw()
    {
        return spdw;
    }
    public String getDir()
    {
        return dir;
    }
    public String getHum()
    {
        return hum;
    }
    public String getVis()
    {
        return vis;
    }
    public String getRise()
    {
        return rise;
    }
    public String getSet()
    {
        return set;
    }
}
 class document {

    public document(String WOEID, String IM) {
        display disp = new display();
        try {
            Document doc = generateXML(WOEID, IM);
            disp.getdata(doc);
        } catch (IOException ex) {
            Logger.getLogger(document.class.getName()).log(Level.SEVERE, (String)null, ex);
        }
    }

    public static Document generateXML(String code, String IM) throws IOException {
        String url = null;
        String XmlData = null;
        url = "http://xml.weather.yahoo.com/forecastrss?w=" + code + "&u=" + IM;
        URL xmlUrl = new URL(url);
        InputStream in = xmlUrl.openStream();
        Document doc = parse(in);
        return doc;
    }

    public static Document parse(InputStream is) {
        Document doc = null;
        try {
            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
            domFactory.setValidating(false);
            domFactory.setNamespaceAware(false);
            DocumentBuilder builder = domFactory.newDocumentBuilder();
            doc = builder.parse(is);
        } catch (Exception ex) {
            System.err.println("unable to load XML: " + ex);
        }
        return doc;
    }
}
        public class weather_app extends javax.swing.JFrame
        {
            public weather_app()
            {
                initComponents();
                getWeather();
            }
            private void getWeather()
            {
                document doc = new document("1580913 ","c");
                display disp = new display();
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                System.out.println(dtf.format(now));
                System.out.println("Todays temp:");
                System.out.println(disp.getTemp());
                System.out.println("Todays Humidity level:");
                System.out.println(disp.getHum());

            }

            @SuppressWarnings("unchecked")
            private void initComponents()
            {
                setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0,322,Short.MAX_VALUE)
                );
                layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0,254,Short.MAX_VALUE)
                );
                pack();


            }
            public static void main(String args[])
            {
                java.awt.EventQueue.invokeLater(new Runnable ()
                {
                    public void run()
                    {
                        //HomeGUI obj=new HomeGUI();
                        new weather_app().setVisible(true);
                    }
                });
            }
        }



    

