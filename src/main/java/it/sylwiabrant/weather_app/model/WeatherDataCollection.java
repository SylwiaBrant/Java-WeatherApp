package it.sylwiabrant.weather_app.model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.sylwiabrant.weather_app.controller.WeatherFetcherService;
import javafx.scene.image.ImageView;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Sylwia Brant
 */
public class WeatherDataCollection {
    private ArrayList<CurrentWeather> currentWeatherList;
    private ArrayList<CurrentWeather> forecastList;
    private WeatherFetcherService fetcher;

    public WeatherDataCollection() {
        this.currentWeatherList = new ArrayList<CurrentWeather>();
        this.forecastList = new ArrayList<CurrentWeather>();
        this.fetcher = new WeatherFetcherService(this);
        System.out.println("Tworzenie WeatherDataCollection.");
    }

    public void addLocation(){

    }

    public void loadCurrentData(JsonObject jsonObject) throws FileNotFoundException {
        System.out.println("ładowanie danych do WeatherDataCollection.");
        System.out.println(jsonObject);
        CurrentWeather cond = new CurrentWeather();
        cond.setCity(jsonObject.get("name").getAsString());
        System.out.println(cond.getCity());
        JsonArray arr = jsonObject.getAsJsonArray("weather");

        for(int i = 0; i < arr.size(); i++){
            JsonObject obj1 = (JsonObject) arr.get(i);
            cond.setDescription(obj1.get("main").getAsString());
        }
        //  cond.setDescription(jsonObject.getAsJsonArray("weather").getAsJsonObject().get("main").getAsString());
        cond.setCountry(jsonObject.getAsJsonObject("sys").get("country").getAsString());
        System.out.println(cond.getCountry());
        cond.setCurrentTemp(jsonObject.getAsJsonObject("main").get("temp").getAsDouble());
        cond.setWindChill(jsonObject.getAsJsonObject("main").get("feels_like").getAsDouble());
        cond.setPressure(jsonObject.getAsJsonObject("main").get("pressure").getAsDouble());
        cond.setHumidity(jsonObject.getAsJsonObject("main").get("humidity").getAsDouble());
        cond.setWindSpeed(jsonObject.getAsJsonObject("wind").get("speed").getAsDouble());
        cond.setWindDirection(jsonObject.getAsJsonObject("wind").get("deg").getAsDouble());
        cond.setVisibility(jsonObject.get("visibility").getAsInt());
        cond.setClouds(jsonObject.getAsJsonObject("clouds").get("all").getAsInt());
        System.out.println(cond);
        currentWeatherList.add(cond);
    }

    public void loadForecast(JsonArray arr) throws IOException {

        for(int i = 1; i < 5; i++){
            CurrentWeather cond = new CurrentWeather();
            JsonObject day = (JsonObject)arr.get(i);
            JsonArray arr1 = day.getAsJsonArray("weather");

            JsonObject obj1 = (JsonObject) arr1.get(0);
            cond.setDescription(obj1.get("main").getAsString());

//            cond.setDescription(jsonObject.getAsJsonArray("weather").getAsJsonObject().get("main").getAsString());
            cond.setCurrentTemp(day.getAsJsonObject("main").get("temp").getAsDouble());
            cond.setPressure(day.getAsJsonObject("main").get("pressure").getAsDouble());
            cond.setHumidity(day.getAsJsonObject("main").get("humidity").getAsDouble());
            cond.setWindSpeed(day.getAsJsonObject("wind").get("speed").getAsDouble());
            cond.setWindDirection(day.getAsJsonObject("wind").get("deg").getAsDouble());
            cond.setClouds(day.getAsJsonObject("clouds").get("all").getAsInt());

            forecastList.add(cond);
        }
    }

    public CurrentWeather getCityWeather() {
        return currentWeatherList.get(0);
    }

    public ArrayList<CurrentWeather> getCityForecast() {
        return forecastList;
    }
}
