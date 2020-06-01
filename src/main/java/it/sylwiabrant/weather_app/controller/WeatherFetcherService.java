package it.sylwiabrant.weather_app.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.sylwiabrant.weather_app.model.WeatherDataCollection;
import javafx.fxml.Initializable;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Sylwia Brant
 */
public class WeatherFetcherService{

    private WeatherDataCollection weatherData;
    public WeatherFetcherService(WeatherDataCollection weatherData) {
        this.weatherData = weatherData;
    }

    public void fetchCurrentWeather(String location) throws FileNotFoundException {
        System.out.println("Pobieranie danych z WeatherFetcherService.");
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(new FileReader(location), JsonObject.class);
        if (jsonObject != null) {
            System.out.println(jsonObject);
            weatherData.loadCurrentData(jsonObject);
        }
    }
    
    public void fetchWeatherForecast(String location) throws IOException {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(new FileReader(location), JsonObject.class);
        JsonArray forecastArray = jsonObject.getAsJsonArray("list");
        System.out.println(forecastArray);
        if (forecastArray != null) {
            try {
                weatherData.loadForecast(forecastArray);
            } catch (IOException exc) {
                // handle exception...
            }
        }
    }
}
