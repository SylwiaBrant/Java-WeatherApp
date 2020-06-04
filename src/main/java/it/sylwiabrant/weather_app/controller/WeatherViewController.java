package it.sylwiabrant.weather_app.controller;

import it.sylwiabrant.weather_app.model.CurrentWeather;
import it.sylwiabrant.weather_app.model.ForecastWeather;
import it.sylwiabrant.weather_app.model.WeatherDataCollection;
import it.sylwiabrant.weather_app.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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

    private void setUpWeatherData() throws IOException {
        WeatherFetcherService weatherFetcherService = new WeatherFetcherService(weatherData);
        weatherFetcherService.fetchCurrentWeather("Londyn");
        weatherFetcherService.fetchCurrentWeather("Paryż");
        weatherFetcherService.fetchWeatherForecast("Londyn");
        weatherFetcherService.fetchWeatherForecast("Paryż");
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
        Label windChillLabel = new Label("(" + String.valueOf(conditions.getWindChill()) + " °)");
        windChillLabel.getStyleClass().add("windChillLabel");

        gridPane.add(new ImageView(new Image(getConditionsIcon(conditions.getDescription()),70, 70, false,
                        false))
                ,0,0,1,2);
        gridPane.add(tempLabel,1,0,2,1);
        gridPane.add(windChillLabel,1,1,2,1);
        gridPane.add(new Label(conditions.getPressure()),1,4);
        gridPane.add(new Label(conditions.getWindSpeed()),1,5);
        gridPane.add(new Label(conditions.getHumidity()),1,6);
        gridPane.add(new Label(String.valueOf(conditions.getClouds())),1,7);
        gridPane.add(new Label(String.valueOf(conditions.getVisibility())),1,8);
    }

    public void setForecastPerCity(ArrayList<ForecastWeather> forecast, GridPane gridPane) throws IOException {
        int i = 0;
        for(ForecastWeather cond : forecast){
            gridPane.add(new Label(String.valueOf(cond.getDate())),i,0);
            gridPane.add(new ImageView(new Image(getConditionsIcon(cond.getDescription()),40,
                    40,
                    false,
                    false)),i,1);
            Label tempLabel = new Label(cond.getMaxTemp() + " °C / " + cond.getMinTemp() + " °C");
            tempLabel.getStyleClass().add("forecastTempLabel");
            gridPane.add(tempLabel,i,2);
            gridPane.add(new Label(cond.getPressure() + " hPa"),i,3);
            gridPane.add(new Label(String.valueOf(windDirToLetters(cond.getWindDirection()) + " " + cond.getWindSpeed()) + " " +
                    "m/s"),i,4);
            i++;
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

    private String windDirToLetters(double deg) {
        if (deg >= 0.0 && deg < 22.5) return "N";
        if (deg >= 337.5 && deg < 360) return "N";
        if (deg >= 22.5 && deg < 67.5) return "NE";
        if (deg >= 67.5 && deg < 112.5) return "E";
        if (deg >= 112.5 && deg < 157.5) return "SE";
        if (deg >= 157.5 && deg < 202.5) return "S";
        if (deg >= 202.5 && deg < 247.5) return "SW";
        if (deg >= 247.5 && deg < 292.5) return "W";
        if (deg >= 292.5 && deg < 337.5) return "NW";
        return "?";
    }

    public String getFxmlName() {
        return this.fxmlName;
    }
}
