package it.sylwiabrant.weather_app.controller;

import it.sylwiabrant.weather_app.model.WeatherDataCollection;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Sylwia Brant
 */
public class WeatherFetcherService{

    private WeatherDataCollection weatherData;
    private final String apiKey = "";
    public WeatherFetcherService(WeatherDataCollection weatherData) {
        this.weatherData = weatherData;
    }

    public void fetchCurrentWeather(String location) throws FileNotFoundException {
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q="+location+"&appid" +
                    "=c8be7af25cecaa14bc28e7439a4a4130&units=metric&lang=pl");
            System.out.println(url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream inputStream = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";

            line = reader.readLine();
            result.append(line);
            reader.close();
            System.out.println("Pobieranie danych z WeatherFetcherService.");
            System.out.println(result);
            JSONObject jsonObject = new JSONObject(result.toString());
            if (jsonObject != null) {
                System.out.println(jsonObject);
                weatherData.loadCurrentData(jsonObject);
            }
            } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void fetchWeatherForecast(String location) throws IOException {
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL("http://api.openweathermap.org/data/2.5/forecast?q="+location+"&appid" +
                    "=c8be7af25cecaa14bc28e7439a4a4130&units=metric&lang=pl");
            System.out.println(url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream inputStream = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";

            while((line = reader.readLine()) != null){
                result.append(line);
            }
            reader.close();
            System.out.println("Pobieranie danych z WeatherFetcherService.");
            System.out.println(result);
            JSONObject jsonObject = new JSONObject(result.toString());
            JSONArray forecastArray = jsonObject.getJSONArray("list");
            System.out.println(forecastArray);
            if (forecastArray != null) {
                try {
                    weatherData.loadForecast(forecastArray);
                } catch (IOException exc) {
                    // handle exception...
                }
            }
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
