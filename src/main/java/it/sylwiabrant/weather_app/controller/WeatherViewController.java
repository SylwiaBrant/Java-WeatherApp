package it.sylwiabrant.weather_app.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.sylwiabrant.weather_app.model.CurrentWeather;
import it.sylwiabrant.weather_app.model.WeatherDataCollection;
import it.sylwiabrant.weather_app.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Sylwia Brant
 */
public class WeatherViewController implements Initializable {

    @FXML
    private ImageView currentIcon;

    @FXML
    private ImageView day1Icon;

    @FXML
    private Label tempLabel1;

    @FXML
    private Label downfallLabel1;

    @FXML
    private Label pressureLabel1;

    @FXML
    private Label humidityLabel1;

    @FXML
    private Label windLabel1;

    @FXML
    private Label cloudsLabel1;

    @FXML
    private ImageView day2Icon;

    @FXML
    private Label tempLabel2;

    @FXML
    private Label downfallLabel2;

    @FXML
    private Label pressureLabel2;

    @FXML
    private Label humidityLabel2;

    @FXML
    private Label windLabel2;

    @FXML
    private Label cloudsLabel2;

    @FXML
    private ImageView day3Icon;

    @FXML
    private Label tempLabel3;

    @FXML
    private Label downfallLabel3;

    @FXML
    private Label pressureLabel3;

    @FXML
    private Label humidityLabel3;

    @FXML
    private Label windLabel3;

    @FXML
    private Label cloudsLabel3;

    @FXML
    private ImageView day4Icon;

    @FXML
    private Label tempLabel4;

    @FXML
    private Label downfallLabel4;

    @FXML
    private Label pressureLabel4;

    @FXML
    private Label humidityLabel4;

    @FXML
    private Label windLabel4;

    @FXML
    private Label cloudsLabel4;

    @FXML
    private HBox locationsMenu;

    @FXML
    private Label tempLabel;

    @FXML
    private Label locationLabel;

    @FXML
    private Label windChillLabel;

    @FXML
    private Label downfallLabel;

    @FXML
    private Label pressureLabel;

    @FXML
    private Label humidityLabel;

    @FXML
    private Label windLabel;

    @FXML
    private Label cloudsLabel;

    @FXML
    private Label visibilityLabel;

    private WeatherDataCollection weatherData;
    private ViewFactory viewFactory;
    private String fxmlName;

    public WeatherViewController(WeatherDataCollection weatherData, ViewFactory viewFactory, String fxmlName) {
        this.weatherData = weatherData;
        this.viewFactory = viewFactory;
        this.fxmlName = fxmlName;
    }
    
    public void setCurrentWeatherView() throws IOException {
        CurrentWeather cond = weatherData.getCityWeather();
        System.out.println(cond.getCurrentTemp());
        System.out.println(cond.getHumidity());
   //     locationLabel.setText(cond.getCity() + ", " + cond.getCountry());
        tempLabel.setText(String.valueOf(cond.getCurrentTemp()) + " °C");
        windChillLabel.setText(String.valueOf(cond.getWindChill()) + " °C");
        pressureLabel.setText(String.valueOf(cond.getPressure()) + " hPa");
        windLabel.setText(String.valueOf(cond.getWindSpeed()) + " m/s");
        humidityLabel.setText(String.valueOf(cond.getHumidity()) + " %");
        visibilityLabel.setText(String.valueOf(cond.getVisibility()) + " m");
        cloudsLabel.setText(String.valueOf(cond.getClouds()) + " %");
        currentIcon.getImage();
    }

    public void setWeatherForecast() throws IOException {
        CurrentWeather cond = weatherData.getCityForecast();
    //    for(CurrentWeather cond : forecast){
            tempLabel1.setText(String.valueOf(cond.getCurrentTemp()) + " °C");
            pressureLabel1.setText(String.valueOf(cond.getPressure()) + " hPa");
            windLabel1.setText(String.valueOf(cond.getWindSpeed()) + " m/s");
            humidityLabel1.setText(String.valueOf(cond.getHumidity()) + " %");
            cloudsLabel1.setText(String.valueOf(cond.getClouds()) + " %");
  //      }
    }
 /*   private void setWeatherIcon(String conditions, int day){
        switch (conditions){
            case "clear sky":
                currentIcon.getImage();
            case "few clouds":
            case "scattered clouds":
            case "broken clouds":
            case "shower rain":
            case "thunderstorm":
            case "snow":
            case "mist":
            default:
        }
    }*/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            System.out.println("Inicjalizacja WeatherViewController.");
            WeatherFetcherService weatherFetcherService = new WeatherFetcherService(weatherData);
            weatherFetcherService.fetchCurrentWeather();
            setCurrentWeatherView();
          //  setWeatherForecast();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getFxmlName() {
        return this.fxmlName;
    }
}
