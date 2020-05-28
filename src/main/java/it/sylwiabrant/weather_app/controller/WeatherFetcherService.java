package it.sylwiabrant.weather_app.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.sylwiabrant.weather_app.model.CurrentWeather;
import it.sylwiabrant.weather_app.model.WeatherDataCollection;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.Initializable;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.security.Provider;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Sylwia Brant
 */
public class WeatherFetcherService implements Initializable {

    private WeatherDataCollection weatherData;
    public WeatherFetcherService(WeatherDataCollection weatherData) {
        this.weatherData = weatherData;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            fetchCurrentWeather();
            System.out.println("Inicjalizacja WeatherFetcherService.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void fetchCurrentWeather() throws IOException {
        System.out.println("Pobieranie danych z WeatherFetcherService.");
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(new FileReader("today.json"), JsonObject.class);
        if (jsonObject != null) {
            try {
                System.out.println(jsonObject);
                weatherData.loadWeatherData(jsonObject);
            } catch (IOException exc) {
                // handle exception...
            }
        }
    }
    
    private void fetchWeatherForecast() throws IOException {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(new FileReader("forecast.json"), JsonObject.class);
        JsonArray forecastArray = jsonObject.getAsJsonArray("list");
        if (forecastArray != null) {
            try {
                weatherData.loadForecast(forecastArray);
            } catch (IOException exc) {
                // handle exception...
            }
        }
    }
}
