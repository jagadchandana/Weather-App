package Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.ResponseCache;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Manager {
    private static String city;
    private static String day;
    private static Integer temperature;
    private static String icon;
    private static String description;
    private static String windSpeed;
    private static String cloudiness;
    private static String pressure;
    private static String humidity;
    private static String code;


    public Manager(String city) {
        this.city = city;
    }

    public Manager() {
    }

    //Build a String from the read Json file
    private String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    //Reads and returns the JsonObject
    public JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    public String[] readJsonFromFile() {

        try {
            String jsonText = readAll(new FileReader("F:/Fidnes-Assignment/src/main/resources/file/cities.json"));

            JSONObject json = new JSONObject(jsonText);
            String[] code =new String[json.getJSONArray("List").length()] ;
            for (int i = 0; i < json.getJSONArray("List").length()-1; i++) {
                code[i] = json.getJSONArray("List").getJSONObject(i).getString("CityCode");
                System.out.println(code[i]);

            }
            return code;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }



    public void writeJsonFile(){
       getWeather("");
    }

    public void getWeather(String city){
        int d = 0;

        JSONObject json;
        JSONObject json_specific; //get specific data in jsonobject variable

        SimpleDateFormat df2 = new SimpleDateFormat("EEEE", Locale.ENGLISH); //Entire word/day as output
        Calendar c = Calendar.getInstance();
        //connects and asks the api to sen the json file
        try {
            if (city.matches("\\d+")){
                json = readJsonFromUrl("https://api.openweathermap.org/data/2.5/weather?id="+city+"&appid=e673bf4ae281f6e2b9e954d4a08c80fe&lang=eng&units=metric");
            }else{
                json = readJsonFromUrl("https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=e673bf4ae281f6e2b9e954d4a08c80fe&lang=eng&units=metric");
            }
            ///
            FileWriter file = new FileWriter("F:/Fidnes-Assignment/src/main/resources/file/weather.json");

            file.write(json.toString());
                file.write("\n");
                file.flush();
                file.close();
            ///
            json_specific = json.getJSONObject("main");
        this.temperature = json_specific.getInt("temp");
        this.pressure = json_specific.get("pressure").toString();
        this.humidity = json_specific.get("humidity").toString();
        json_specific = json.getJSONObject("wind");
        this.windSpeed = json_specific.get("speed").toString();
        json_specific = json.getJSONObject("clouds");
        this.cloudiness = json_specific.get("all").toString();


        c.add(Calendar.DATE, d);
        this.day = df2.format(c.getTime());

        json_specific = json.getJSONArray("weather").getJSONObject(0);
        this.description = json_specific.get("description").toString();
        this.icon = json_specific.get("icon").toString();
        this.city = json.getString("name");
        this.code = String.valueOf(json.get("id"));

       } catch (IOException e) {
            System.out.println("error!..Check Internet Connection...");
        }
    }


    //Setters for all the private fields
    public static String getCode(){ return code;}

    public static String getCity() {
        return city;
    }

    public static String getDay() {
        return day;
    }

    public static Integer getTemperature() {
        return temperature;
    }

    public static String getIcon() {
        return icon;
    }

    public static String getDescription() {
        return description;
    }

    public static String getWindSpeed() {
        return windSpeed;
    }

    public static String getCloudiness() {
        return cloudiness;
    }

    public static String getPressure() {
        return pressure;
    }

    public static String getHumidity() {
        return humidity;
    }
}
