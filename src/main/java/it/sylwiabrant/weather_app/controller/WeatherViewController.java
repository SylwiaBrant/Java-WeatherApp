package it.sylwiabrant.weather_app.controller;

import it.sylwiabrant.weather_app.model.CurrentWeather;
import it.sylwiabrant.weather_app.model.WeatherDataCollection;
import it.sylwiabrant.weather_app.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
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
                 /*   URL path = this.getClass().getResource("/it/sylwiabrant/weather_app/Icons/water.png");
            Image img = new Image(String.valueOf(path),20, 20, false, false);
            ImageView imgView = new ImageView(img);
            forecastGrid.add(imgView,i,0);*/
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
        int i = 1;
        for(CurrentWeather cond : forecast){
         //   System.out.println(cond.getCurrentTemp();

            forecastGrid.add(new Label(String.valueOf(cond.getCurrentTemp()) + " °C"),i,0);
            forecastGrid.add(new Label(String.valueOf(cond.getPressure()) + " hPa"),i,1);
            forecastGrid.add(new Label(String.valueOf(cond.getWindSpeed()) + " m/s"),i,2);
            forecastGrid.add(new Label(String.valueOf(cond.getHumidity()) + " %"),i,3);
            forecastGrid.add(new Label(String.valueOf(cond.getClouds()) + " %"),i,4);
            i+=2;
        }
        addIconsToForecast();
    }

    private void addIconsToForecast() {
        for(int day=0; day < 4; day++){
            URL path1 = this.getClass().getResource("/it/sylwiabrant/weather_app/Icons/temp.png");
            Image img1 = new Image(String.valueOf(path1),25, 25, false, false);
            ImageView imgView1 = new ImageView(img1);
            forecastGrid.add(imgView1,2*day,0);

            URL path2 = this.getClass().getResource("/it/sylwiabrant/weather_app/Icons/gauge.png");
            Image img2 = new Image(String.valueOf(path2),25, 20, false, false);
            ImageView imgView2 = new ImageView(img2);
            forecastGrid.add(imgView2,2*day,1);

            URL path3 = this.getClass().getResource("/it/sylwiabrant/weather_app/Icons/wind.png");
            Image img3 = new Image(String.valueOf(path3),25, 25, false, false);
            ImageView imgView3 = new ImageView(img3);
            forecastGrid.add(imgView3,2*day,2);

            URL path4 = this.getClass().getResource("/it/sylwiabrant/weather_app/Icons/humidity.png");
            Image img4 = new Image(String.valueOf(path4),25, 25, false, false);
            ImageView imgView4 = new ImageView(img4);
            forecastGrid.add(imgView4,2*day,3);

            URL path5 = this.getClass().getResource("/it/sylwiabrant/weather_app/Icons/water.png");
            Image img5 = new Image(String.valueOf(path5),20, 20, false, false);
            ImageView imgView5 = new ImageView(img5);
            forecastGrid.add(imgView5,2*day,4);

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
