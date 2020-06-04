package it.sylwiabrant.weather_app.model;

import it.sylwiabrant.weather_app.controller.WeatherFetcherService;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Sylwia Brant
 */
public class WeatherDataCollection {
    private ArrayList<CurrentWeather> currentWeatherList;
    private ArrayList<ArrayList<ForecastWeather>> forecastList;
    private WeatherFetcherService fetcher;

    public WeatherDataCollection() {
        this.currentWeatherList = new ArrayList<CurrentWeather>();
        this.forecastList = new ArrayList<ArrayList<ForecastWeather>>();
        this.fetcher = new WeatherFetcherService(this);
        System.out.println("Tworzenie WeatherDataCollection.");
    }

    public void loadCurrentData(JSONObject jsonObject) {
      //  System.out.println("ładowanie danych do WeatherDataCollection.");
        CurrentWeather cond = new CurrentWeather();
        cond.setCity(jsonObject.get("name").toString());
        cond.setDescription(jsonObject.getJSONArray("weather").getJSONObject(0).get("main").toString());
        cond.setDescription(jsonObject.getJSONArray("weather").getJSONObject(0).get("description").toString());
        cond.setCountry(jsonObject.getJSONObject("sys").get("country").toString());
        cond.setTemp(((Number)jsonObject.getJSONObject("main").get("temp")).doubleValue());
        cond.setWindChill(jsonObject.getJSONObject("main").get("feels_like").toString());
        cond.setPressure(jsonObject.getJSONObject("main").get("pressure").toString());
        cond.setHumidity(jsonObject.getJSONObject("main").get("humidity").toString());
        cond.setWindSpeed(jsonObject.getJSONObject("wind").get("speed").toString());
        cond.setWindDirection(((Number) jsonObject.getJSONObject("wind").get("deg")).intValue());
     //   cond.setVisibility(jsonObject.get("visibility").toString());
     //   cond.setClouds(jsonObject.getAsJsonObject("clouds").get("all").getAsInt());
        System.out.println(cond);
        currentWeatherList.add(cond);
    }

    public void loadForecast(JSONArray jsonArray) throws IOException {
        ArrayList<ForecastWeather> forecasts = new ArrayList<ForecastWeather>();
        double tempTemp, minTemp, maxTemp, rain, snow;
        int windDirection;
        String description = "", pressure ="", windSpeed="";

        for(int i = 0; i < 40; i++){
            minTemp=100; maxTemp=-100; windDirection = 0; rain = 0; snow = 0;

            JSONObject day = (JSONObject)jsonArray.get(i);
            JSONArray arr1 = day.getJSONArray("weather");
            JSONObject obj1 = (JSONObject) arr1.get(0);

            tempTemp = ((Number) day.getJSONObject("main").get("temp")).doubleValue();
            if(tempTemp < minTemp)
                minTemp = tempTemp;
            if(tempTemp > maxTemp)
                maxTemp = tempTemp;

            if(i % 6 == 0){
                pressure = day.getJSONObject("main").get("pressure").toString();
                windSpeed = day.getJSONObject("wind").get("speed").toString();
                windDirection = ((Number) day.getJSONObject("wind").get("deg")).intValue();
            }

            if(i % 8 == 0){
                ForecastWeather cond = new ForecastWeather();
                cond.setMaxTemp(maxTemp);
                cond.setMinTemp(minTemp);
                cond.setDate(extractDate(((Number) day.get("dt")).longValue()));
                cond.setDescription(description);
                cond.setPressure(pressure);
                cond.setWindSpeed(windSpeed);
                cond.setWindDirection(windDirection);

                forecasts.add(cond);
            }
        }
        forecastList.add(forecasts);
    }

    public ArrayList<CurrentWeather> getCurrentWeathers() {
        return currentWeatherList;
    }

    public ArrayList<ArrayList<ForecastWeather>> getForecasts() {
        return forecastList;
    }

    private String extractDate(long timestamp){
        Date date = new java.util.Date(timestamp*1000L);
        // the format of your date
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy");
        // give a timezone reference for formatting (see comment at the bottom)
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT-0"));
        return sdf.format(date);
    }
}
