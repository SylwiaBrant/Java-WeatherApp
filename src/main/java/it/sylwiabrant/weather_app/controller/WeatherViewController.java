package it.sylwiabrant.weather_app.controller;

import it.sylwiabrant.weather_app.model.CurrentWeather;
import it.sylwiabrant.weather_app.model.ForecastWeather;
import it.sylwiabrant.weather_app.model.WeatherDataCollection;
import it.sylwiabrant.weather_app.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

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
    private GridPane currentConds2;

    /**
     * On button click opens single city choice window, invokes fetching info about current weather
     * and forecast for newly chosen city and invokes setting info in the 1st city view
     * @return void
     */
    @FXML
    void change1stCityAction() {
        viewFactory.show1stCityChoiceWindow();
    }
    /**
     * On button click opens single city choice window, invokes fetching info about current weather
     * and forecast for newly chosen city and invokes setting info in the 2nd city view
     * @return void
     */
    @FXML
    void change2ndCityAction() {
        viewFactory.show2ndCityChoiceWindow();
    }

    public WeatherViewController(WeatherDataCollection weatherData, ViewFactory viewFactory, String fxmlName) {
        super(weatherData, viewFactory, fxmlName);
    }

    /**
     * On creation of this instance sets current weather and forecast for both cities in the view
     * @return void
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            System.out.println("Inicjalizacja WeatherViewController.");
            weatherData.registerObserver(this);
            setCurrentWeatherView();
            setForecastView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Fetches current weather for both cities from WeatherDataCollection model and
     * invokes functions to set those conditions in the view.
     * @return void
     */
    public void setCurrentWeatherView() {
        ArrayList<CurrentWeather> currentWeathers = weatherData.getCurrentWeathers();
        setWeatherPerCity(currentWeathers.get(0), currentConds1, locationLabel1);
        setWeatherPerCity(currentWeathers.get(1), currentConds2, locationLabel2);
    }

    /**
     * Fetches weather forecast for both cities from WeatherDataCollection model and
     * invokes functions to set those forecasts in the view.
     * @return void
     */
    public void setForecastView() {
        ArrayList<ArrayList<ForecastWeather>> forecasts = weatherData.getForecasts();
        setForecastPerCity(forecasts.get(0), forecastGrid1);
        setForecastPerCity(forecasts.get(1), forecastGrid2);
    }

    /**
     * Sets current weather in view for single city. Which one depends on sent gridPane and label
     * @param conditions - current weather object with conditions set
     * @param gridPane - gridPane in which the conditions will be set
     * @param label - label on which the location info will be set
     * @return void
     */
    private void setWeatherPerCity(CurrentWeather conditions, GridPane gridPane, Label label){
        System.out.println("Settowanie danych w WeatherDataCollection.");
        label.setText(conditions.getCity() + ", " + conditions.getCountry());
        Label tempLabel = new Label(conditions.getTemp() + " °C");
        tempLabel.getStyleClass().add("currentTempLabel");
        Label windChillLabel = new Label("("+ conditions.getWindChill()+ " °)");
        windChillLabel.getStyleClass().add("windChillLabel");

        gridPane.add(new ImageView(new Image(conditions.getIcon(), 70, 70, false,
                        false))
                , 0, 0, 3, 2);
        gridPane.add(tempLabel,3,0,3,1);
        gridPane.add(windChillLabel,2,1,3,1);
        gridPane.add(new Label(conditions.getDescription()),0,2,6,1);

        String downfall = "0";
        if(!conditions.getRain().equals("0"))
            downfall = conditions.getRain();
        else if (!conditions.getSnow().equals("0"))
            downfall = conditions.getSnow();

        gridPane.add(new Label(downfall),1,3);
        gridPane.add(new Label(conditions.getPressure()),4,3);
        gridPane.add(new Label(conditions.getWindSpeed()),1,4);
        gridPane.add(new Label(conditions.getHumidity()),4,4);
        gridPane.add(new Label(conditions.getClouds()),1,5);
        gridPane.add(new Label(conditions.getVisibility()),4,5);

        setDefaultGridElems(gridPane);
    }

    private void setDefaultGridElems(GridPane gridPane) {
        gridPane.add(new ImageView(getDefaultGridIcon("downfall.png")), 0, 3);
        gridPane.add(new Label("mm"),2,3);

        gridPane.add(new ImageView(getDefaultGridIcon("pressure.png")), 3,3);
        gridPane.add(new Label("hPa"),5,3);

        gridPane.add(new ImageView(getDefaultGridIcon("wind.png")),0,4);
        gridPane.add(new Label("m/s"),2,4);

        gridPane.add(new ImageView(getDefaultGridIcon("humidity.png")),3,4);
        gridPane.add(new Label("%"),5,4);

        gridPane.add(new ImageView(getDefaultGridIcon("cloudiness.png")),0,5);
        gridPane.add(new Label("%"),2,5);

        gridPane.add(new ImageView(getDefaultGridIcon("vi.png")),3,5);
        gridPane.add(new Label("m"),5,5);
    }

    private Image getDefaultGridIcon(String imgName){
        return new Image (String.valueOf(this.getClass().getResource("/it/sylwiabrant/weather_app/Icons/"+imgName)), 25, 25, false,
                false);
    }

    /**
     * Sets weather forecast in view for single city. Which one depends on sent gridPane
     * @param forecast - 4 day weather forecast objects array
     * @param gridPane - gridPane in which the forecast conditions will be set
     * @return void
     */
    public void setForecastPerCity(ArrayList<ForecastWeather> forecast, GridPane gridPane) {
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
            if(!cond.getRain().equals("0"))
                downfall = String.valueOf(cond.getRain());
            else if (!cond.getSnow().equals("0"))
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

    public void updateCityView(int index){
        Label cityLabel; GridPane currentWeatherPane; GridPane forecastPane;
        CurrentWeather currentWeather = weatherData.getSingleCityWeather(index);
        ArrayList<ForecastWeather> forecastWeather = weatherData.getSingleCityForecasts(index);

        if (index == 0){
            cityLabel = locationLabel1;
            currentWeatherPane = currentConds1;
            forecastPane = forecastGrid1;
        } else {
            cityLabel = locationLabel2;
            currentWeatherPane = currentConds2;
            forecastPane = forecastGrid2;
        }
        currentWeatherPane.getChildren().clear();
        forecastPane.getChildren().clear();
        setWeatherPerCity(currentWeather, currentWeatherPane, cityLabel);
        setForecastPerCity(forecastWeather, forecastPane);
    }
}
