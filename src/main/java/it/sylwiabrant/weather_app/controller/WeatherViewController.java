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
public class WeatherViewController extends BaseController implements Initializable {

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

    public WeatherViewController(WeatherDataCollection weatherData, ViewFactory viewFactory, String fxmlName) {
        super(weatherData, viewFactory, fxmlName);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            System.out.println("Inicjalizacja WeatherViewController.");
    //        setUpWeatherData();
            setCurrentWeatherView();
            setForecastView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setUpWeatherData() throws IOException {
      /*  WeatherFetcherService weatherFetcherService = new WeatherFetcherService(weatherData);
        weatherFetcherService.fetchCurrentWeather("Londyn");
        weatherFetcherService.fetchCurrentWeather("Paryż");
        weatherFetcherService.fetchWeatherForecast("Londyn");
        weatherFetcherService.fetchWeatherForecast("Paryż");*/
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
        Label tempLabel = new Label(conditions.getTemp() + " °C");
        tempLabel.getStyleClass().add("currentTempLabel");
        Label windChillLabel = new Label("("+ conditions.getWindChill()+ " °)");
        windChillLabel.getStyleClass().add("windChillLabel");

        gridPane.add(new ImageView(new Image(conditions.getIcon(),70, 70, false,
                        false))
                ,0,0,2,2);
        gridPane.add(tempLabel,2,0,2,1);
        gridPane.add(windChillLabel,2,1,2,1);
        gridPane.add(new Label(conditions.getDescription()),0,2,4,1);

        String downfall = "0";
        if(conditions.getRain() != "0")
            downfall = conditions.getRain();
        else if (conditions.getSnow() != "0")
            downfall = conditions.getSnow();

        gridPane.add(new Label(downfall),1,3);
        gridPane.add(new Label(conditions.getPressure()+" hPa"),3,3);
        gridPane.add(new Label(conditions.getWindSpeed()+" m/s"),1,4);
        gridPane.add(new Label(conditions.getHumidity()+" %"),3,4);
        gridPane.add(new Label(conditions.getClouds()+" %"),1,5);
        gridPane.add(new Label(conditions.getVisibility()+" m"),3,5);
    }

    public void setForecastPerCity(ArrayList<ForecastWeather> forecast, GridPane gridPane) throws IOException {
        int i = 0;
        for(ForecastWeather cond : forecast){
            gridPane.add(new Label(cond.getDate()),i,0);
            gridPane.add(new ImageView(new Image(cond.getIcon(),40,
                    40,
                    false,
                    false)),i,1);

            gridPane.add(new Label(cond.getDescription()),i,2);
            Label tempLabel = new Label(cond.getMaxTemp() + " °C / " + cond.getMinTemp() + " °C");
            tempLabel.getStyleClass().add("forecastTempLabel");

            String downfall = "0";
            if(cond.getRain() != "0")
                downfall = String.valueOf(cond.getRain());
            else if (cond.getSnow() != "0")
                downfall = String.valueOf(cond.getSnow());

            gridPane.add(tempLabel,i,3);
            gridPane.add(new Label(downfall + " mm"),i,4);
            gridPane.add(new Label(cond.getPressure() + " hPa"),i,5);
            gridPane.add(new Label(cond.getWindDirection() + " " + cond.getWindSpeed() + " " +
                    "m/s"),i,6);
            gridPane.add(new Label(cond.getClouds() + " %"),i,7);
            i++;
        }
    }
}
