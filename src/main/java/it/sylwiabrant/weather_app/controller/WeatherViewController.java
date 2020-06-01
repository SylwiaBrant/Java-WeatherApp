package it.sylwiabrant.weather_app.controller;

import it.sylwiabrant.weather_app.model.CurrentWeather;
import it.sylwiabrant.weather_app.model.ForecastWeather;
import it.sylwiabrant.weather_app.model.WeatherConditions;
import it.sylwiabrant.weather_app.model.WeatherDataCollection;
import it.sylwiabrant.weather_app.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.*;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Sylwia Brant
 */
public class WeatherViewController implements Initializable {

    @FXML
    private Label locationLabel1;

    @FXML
    private GridPane forecastGrid1;

    @FXML
    private GridPane forecastGrid2;

    @FXML
    private Label locationLabel2;

    @FXML
    private GridPane currentConds1;

    @FXML
    private Button change1LocBtn;

    @FXML
    private Button change2LocBtn;

    @FXML
    private GridPane currentConds2;

    @FXML
    void change1LocAction() {

    }

    @FXML
    void change2LocAction() {

    }

    private WeatherDataCollection weatherData;
    private ViewFactory viewFactory;
    private String fxmlName;

    public WeatherViewController(WeatherDataCollection weatherData, ViewFactory viewFactory, String fxmlName) {
        this.weatherData = weatherData;
        this.viewFactory = viewFactory;
        this.fxmlName = fxmlName;
    }

    private void setUpWeatherData() throws IOException {
        WeatherFetcherService weatherFetcherService = new WeatherFetcherService(weatherData);
        weatherFetcherService.fetchCurrentWeather("LondonCurrent.json");
        weatherFetcherService.fetchCurrentWeather("FlorenceCurrent.json");
        weatherFetcherService.fetchWeatherForecast("LondonForecast.json");
        weatherFetcherService.fetchWeatherForecast("FlorenceForecast.json");
    }

    public void setCurrentWeatherView() throws IOException {
        ArrayList<CurrentWeather> currentWeathers = weatherData.getCurrentWeathers();
        setWeatherPerCity(currentWeathers.get(0), currentConds1, locationLabel1);
        setWeatherPerCity(currentWeathers.get(1), currentConds2, locationLabel2);
    }

    public void setForecastView() throws IOException {
        ArrayList<ArrayList<ForecastWeather>> forecasts = weatherData.getForecasts();
        setForecastPerCity(forecasts.get(0), forecastGrid1);
        setForecastPerCity(forecasts.get(1), forecastGrid2);
    }

    private void setWeatherPerCity(CurrentWeather conditions, GridPane gridPane, Label label){
        label.setText(conditions.getCity() + ", " + conditions.getCountry());
        Label tempLabel = new Label(String.valueOf(conditions.getTemp()) + " °C");
        tempLabel.getStyleClass().add("currentTempLabel");
        Label windChillLabel = new Label(String.valueOf(conditions.getWindChill()) + " °C");
        windChillLabel.getStyleClass().add("windChillLabel");

        gridPane.add(new ImageView(new Image(getConditionsIcon(conditions.getDescription()),100, 100, false,
                        false))
                ,0,1,1,2);
        gridPane.add(tempLabel,1,0,1,2);
        gridPane.add(windChillLabel,1,2,1,2);
        gridPane.add(new Label(String.valueOf(conditions.getPressure())),2,2);
        gridPane.add(new Label(String.valueOf(conditions.getWindSpeed())),3,2);
        gridPane.add(new Label(String.valueOf(conditions.getHumidity())),4,2);
        gridPane.add(new Label(String.valueOf(conditions.getClouds())),5,2);
        gridPane.add(new Label(String.valueOf(conditions.getVisibility())),6,2);
    }

    public void setForecastPerCity(ArrayList<ForecastWeather> forecast, GridPane gridPane) throws IOException {
        int i = 1;
        for(WeatherConditions cond : forecast){
            //   forecastGrid1.add(new Label(String.valueOf(cond.getDescription())),i,1);
            gridPane.add(new ImageView(new Image(getConditionsIcon(cond.getDescription()),40,
                    40,
                    false,
                    false)),i,0);
            gridPane.add(new Label(String.valueOf(cond.getTemp()) + " °C"),i,1);
            gridPane.add(new Label(String.valueOf(cond.getPressure()) + " hPa"),i,2);
            gridPane.add(new Label(String.valueOf(cond.getWindSpeed()) + " m/s"),i,3);
            i+=2;
        }
    }

    private String getConditionsIcon(String conditions){
        String imgPath;
        switch (conditions){
            case "Clear":
                imgPath = "/it/sylwiabrant/weather_app/Icons/sun.png";
                break;
            case "Clouds":
                imgPath = "/it/sylwiabrant/weather_app/Icons/clouds.png";
                break;
            case "Rain":
            case "Drizzle":
                imgPath = "/it/sylwiabrant/weather_app/Icons/rain.png";
                break;
            case "Thunderstorm":
                imgPath = "/it/sylwiabrant/weather_app/Icons/storm.png";
                break;
            case "Snow":
                imgPath = "/it/sylwiabrant/weather_app/Icons/snow.png";
                break;
            case "Mist":
            case "Fog":
            case "Haze":
                imgPath = "/it/sylwiabrant/weather_app/Icons/mist.png";
                break;
            default:
               imgPath = "/it/sylwiabrant/weather_app/Icons/clouds.png";
        }
        return String.valueOf(this.getClass().getResource(imgPath));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            System.out.println("Inicjalizacja WeatherViewController.");
            setUpWeatherData();
            setCurrentWeatherView();
            setForecastView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getFxmlName() {
        return this.fxmlName;
    }
}
