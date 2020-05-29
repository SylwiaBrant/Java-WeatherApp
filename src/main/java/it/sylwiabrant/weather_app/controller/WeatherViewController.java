package it.sylwiabrant.weather_app.controller;

import it.sylwiabrant.weather_app.model.CurrentWeather;
import it.sylwiabrant.weather_app.model.WeatherDataCollection;
import it.sylwiabrant.weather_app.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

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

    @FXML
    private GridPane forecastGrid;

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
     //   System.out.println(cond.getCurrentTemp());
     //   System.out.println(cond.getHumidity());
        locationLabel.setText(cond.getCity() + ", " + cond.getCountry());
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
        ArrayList<CurrentWeather> forecast = weatherData.getCityForecast();
        int i = 0;
        for(CurrentWeather cond : forecast){
         //   System.out.println(cond.getCurrentTemp());
            forecastGrid.add(new Label(String.valueOf(cond.getCurrentTemp()) + " °C"),i,0);
            forecastGrid.add(new Label(String.valueOf(cond.getPressure()) + " hPa"),i,1);
            forecastGrid.add(new Label(String.valueOf(cond.getWindSpeed()) + " m/s"),i,2);
            forecastGrid.add(new Label(String.valueOf(cond.getHumidity()) + " %"),i,3);
            forecastGrid.add(new Label(String.valueOf(cond.getClouds()) + " %"),i,4);
            i++;
        }
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
            weatherFetcherService.fetchWeatherForecast();
            setCurrentWeatherView();
            setWeatherForecast();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getFxmlName() {
        return this.fxmlName;
    }
}
